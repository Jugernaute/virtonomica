package ua.com.virtonomica.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.entity.RequiredMinValuesForProductToCalculateFactory;
import ua.com.virtonomica.entity.company.Company;
import ua.com.virtonomica.service.RequiredMinValuesForProductToCreateFactoryService;
import ua.com.virtonomica.utils.reports.*;
import ua.com.virtonomica.service.company.CompanyService;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.FactoryInfo_For_Offer;
import ua.com.virtonomica.web.connection.UserWebClient;
import ua.com.virtonomica.web.api.analysis.industry_analysis.search.TradeOffers;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class VirtonomicaAPI {

    private final CompanyService companyService;
    private final RequiredMinValuesForProductToCreateFactoryService requiredMinValuesForProductService;
    private final ObjectMapper mapper = new ObjectMapper();
    private HtmlPageData htmlPageData = new HtmlPageData();

    @Autowired
    public VirtonomicaAPI(CompanyService companyService, RequiredMinValuesForProductToCreateFactoryService requiredMinValuesForProductService) {
        this.companyService = companyService;
        this.requiredMinValuesForProductService = requiredMinValuesForProductService;
    }

    public void getCompanyInfo(){
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/my/company");
        try {
            Company company = new ObjectMapper().readValue(content, Company.class);
            companyService.update(company);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCompanyUnits(){
        long id = companyService.getCompany().getId();
        return htmlPageData.getContent("https://virtonomica.ru/api/lien/main/company/units?id=" + id + "&pagesize=30000");
    }

    public List<CompanyReportFinance> getCompanyReportFinance(){
        WebClient webClient = getWebClient();
        int companyId = CompanyReportFinance.getCompanyId();
        List<CompanyReportFinance> finance = new ArrayList<>();
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/company/report/finance/byitem?id=" + companyId);
            String content = page.getWebResponse().getContentAsString();
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CompanyReportFinance companyReportFinance = mapper.readValue(jsonObject.toString(), CompanyReportFinance.class);
                finance.add(companyReportFinance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Спочатку потрібно зайти на головну сторінку ігри, щоб отримати куки");
        }
        return finance;
    }

    @Cacheable("unitsReportFinance")
    public List<UnitsReportFinance> getUnitsReportFinance(String param){
//        WebClient webClient = getWebClient();
        List<UnitsReportFinance>finances = new ArrayList<>();
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/company/report/units/" + param + "?id=3247145&pagesize=1000");
        try {
            JSONObject jsonObject = new JSONObject(content);
            Set<String> keySet = jsonObject.keySet();
            for (String s : keySet) {
                String o = jsonObject.get(s).toString();
                UnitsReportFinance unitsReportFinance = mapper.readValue(o, UnitsReportFinance.class);
                finances.add(unitsReportFinance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finances;
    }

    public List<UnitsSupplyContracts> getSupplyContractsByUnitId(long unitId){
        List<UnitsSupplyContracts> contracts = new ArrayList<>();
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/unit/supply/contracts?id=" + unitId);
        try {
            JSONObject jsonObject = new JSONObject(content);
            Set<String> keySet = jsonObject.keySet();
            for (String s : keySet) {
                String o = jsonObject.get(s).toString();
                UnitsSupplyContracts unitsSupplyContracts = mapper.readValue(o, UnitsSupplyContracts.class);
                contracts.add(unitsSupplyContracts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contracts;
    }

//    @Cacheable(value = "tradeOffersById", key = "#productId") // установить время жизни кеша = 10мин
    public TradeOffers getTradeOffersByProductId(String product, long productId/*, int minCountMaterial*/) {
        LinkedHashMap<Double, TradeOffers> map = new LinkedHashMap<>();
//        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/marketing/report/trade/offers?product_id=" + productId + "&pagesize=1000");
        List<TradeOffers> list;

        String productName = product.toLowerCase();
       System.out.println(productName);

        boolean b = requiredMinValuesForProductService.existsByName(productName);
        if (b) {
            RequiredMinValuesForProductToCalculateFactory byName = requiredMinValuesForProductService.findByName(productName);
            double minQuality = byName.getMinQuality();
            double maxPrice = byName.getMaxPrice();

            List<TradeOffers> listTradeOffers = getListTradeOffers(productId);
            if (listTradeOffers.size()==0) {
                return null;
            }
//            System.out.println("trade offers (not sort): ");
//            print(listTradeOffers);

            list = listTradeOffers
                    .stream()
                    .filter(t->t.getQuality()>=minQuality && t.getPrice()<=maxPrice)
                    .filter(t -> t.getFree_for_buy() >100  & t.getMax_qty() == 0)
                    .collect(Collectors.toList());
            //from min to max
            list.sort(Comparator.comparingDouble(TradeOffers::getQuality));
        } else {
            List<TradeOffers> listTradeOffers = getListTradeOffers(productId);

            if (listTradeOffers.size()==0) {
                return null;
            }
//            System.out.println("trade offers (not sort): ");
//            print(listTradeOffers);

            list = listTradeOffers
                    .stream()
//                    .filter(t ->t.getMax_qty() >=0 & t.getMax_qty() == 0)
                    .filter(t -> t.getFree_for_buy() >100  & t.getMax_qty() == 0)
                    .collect(Collectors.toList());


            list.sort(Comparator.comparingDouble(TradeOffers::getQuality));//from min to max
//            System.out.println("trade offers (after sort): ");
//            print(list);
        }

        if (list.size()==0){
            return null;
        }

        try {
            // діапазон якості товарів з якими будемо працювати
            int[] qArr = qualityArray();
            for (int i = 0; i < qArr.length - 1; i++) {
                int q_min = qArr[i];
                int q_max = qArr[i + 1];

                //найбільша якість продукції, яка пропонується
                //залежно від неї буде і довжина масиву якостей
                // вибираємо якість від q[і] -> q[i+1]
                List<TradeOffers> collect = getQualityRange(list, q_min, q_max);
                //працюємо з якісю в межах q[і] -> q[i+1]

                boolean notEmptyCollect = isNotEmptyCollect(collect);
                if (notEmptyCollect) {
                    LinkedHashMap<Double, TradeOffers> bestPrice_eachQualityRange = getBestPrice_EachQualityRange(collect);
                    map.putAll(bestPrice_eachQualityRange);
                }
                double max_quality = list.get(list.size() - 1).getQuality();
                if (q_min <= max_quality && q_max > max_quality) {
                    double koef = Double.MAX_VALUE;
                    TradeOffers tradeOffers = new TradeOffers();
                    Set<Map.Entry<Double, TradeOffers>> entries = map.entrySet();
                    for (Map.Entry<Double, TradeOffers> next : entries) {
                        Double quality = next.getKey();
                        TradeOffers value = next.getValue();
                        double price = value.getPrice();
                        double v = price / quality;
                        if (v < koef) {
                            koef = v;
                            tradeOffers = value;
                        }
                    }
//                    System.out.println("trade offers (final)");
//                    print(tradeOffers);
//                    System.out.println("============================");
//                    System.out.println("============================");
                    return tradeOffers;
                }
            }
        } catch (JSONException e) {
            System.out.println(productId + " " + "пропозицій на ринку на цей товар немає!");
        }
        return null;
    }

    public TradeOffers getTradeOffersByProductId(String product, List<TradeOffers> listTradeOffers, int minCountMaterial) {
        LinkedHashMap<Double, TradeOffers> map = new LinkedHashMap<>();
//        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/marketing/report/trade/offers?product_id=" + productId + "&pagesize=1000");
        List<TradeOffers> list;

        String productName = product.toLowerCase();
        System.out.println(productName);

        boolean b = requiredMinValuesForProductService.existsByName(productName);
        if (b) {
            RequiredMinValuesForProductToCalculateFactory byName = requiredMinValuesForProductService.findByName(productName);
            double minQuality = byName.getMinQuality();
            double maxPrice = byName.getMaxPrice();

//            List<TradeOffers> listTradeOffers = getListTradeOffers(productId);
            if (listTradeOffers.size()==0) {
                return null;
            }
//            System.out.println("trade offers (not sort): ");
//            print(listTradeOffers);

            list = listTradeOffers
                    .stream()
                    .filter(t->t.getQuality()>=minQuality && t.getPrice()<=maxPrice)
                    .filter(t -> t.getFree_for_buy() >=minCountMaterial  & t.getMax_qty() == 0)
                    .collect(Collectors.toList());
            //from min to max
            list.sort(Comparator.comparingDouble(TradeOffers::getQuality));
        } else {
//            List<TradeOffers> listTradeOffers = getListTradeOffers(productId);

            if (listTradeOffers.size()==0) {
                return null;
            }
            list = listTradeOffers
                    .stream()
//                    .filter(t ->t.getMax_qty() >=0)
                    .filter(t ->
                            (t.getMax_qty() >=minCountMaterial  & t.getFree_for_buy() >= minCountMaterial)
                                    ||
                                    (t.getMax_qty()==0 & t.getFree_for_buy()>=minCountMaterial)
                    )
                    .collect(Collectors.toList());


//            list.stream()
//                    .filter(t->t.getMax_qty()!=0)
            list.sort(Comparator.comparingDouble(TradeOffers::getQuality));//from min to max
//            System.out.println("trade offers (after sort): ");
//            print(list);
        }

        if (list.size()==0){
            return null;
        }

        try {
            // діапазон якості товарів з якими будемо працювати
            int[] qArr = qualityArray();
            for (int i = 0; i < qArr.length - 1; i++) {
                int q_min = qArr[i];
                int q_max = qArr[i + 1];

                //найбільша якість продукції, яка пропонується
                //залежно від неї буде і довжина масиву якостей
                // вибираємо якість від q[і] -> q[i+1]
                List<TradeOffers> collect = getQualityRange(list, q_min, q_max);
                //працюємо з якісю в межах q[і] -> q[i+1]

                boolean notEmptyCollect = isNotEmptyCollect(collect);
                if (notEmptyCollect) {
                    LinkedHashMap<Double, TradeOffers> bestPrice_eachQualityRange = getBestPrice_EachQualityRange(collect);
                    map.putAll(bestPrice_eachQualityRange);
                }
                double max_quality = list.get(list.size() - 1).getQuality();
                if (q_min <= max_quality && q_max > max_quality) {
                    double koef = Double.MAX_VALUE;
                    TradeOffers tradeOffers = new TradeOffers();
                    Set<Map.Entry<Double, TradeOffers>> entries = map.entrySet();
                    for (Map.Entry<Double, TradeOffers> next : entries) {
                        Double quality = next.getKey();
                        TradeOffers value = next.getValue();
                        double price = value.getPrice();
                        double v = price / quality;
                        if (v < koef) {
                            koef = v;
                            tradeOffers = value;
                        }
                    }
//                    System.out.println("trade offers (final)");
//                    print(tradeOffers);
//                    System.out.println("============================");
//                    System.out.println("============================");
                    return tradeOffers;
                }
            }
        } catch (JSONException e) {
//            System.out.println(productId + " " + "пропозицій на ринку на цей товар немає!");
        }
        return null;
    }

    private void print(List<TradeOffers> listTradeOffers) {
        listTradeOffers.forEach(t-> System.out.println("quality: "+ t.getQuality()+" ,freeForBuy: "+t.getFree_for_buy()+ " ,price: "+t.getPrice()+" ,count: "+t.getQuantity()));
    }

    private void print(TradeOffers t){
        System.out.println("quality: "+ t.getQuality()+" ,freeForBuy: "+t.getFree_for_buy()+ " ,price: "+t.getPrice()+" ,count: "+t.getQuantity());
    }

    private LinkedHashMap<Double, TradeOffers> getBestPrice_EachQualityRange(List<TradeOffers> collect) {
            LinkedHashMap<Double,TradeOffers> map = new LinkedHashMap<>();
            TradeOffers tradeOffers = new TradeOffers();
            double quality=Double.MAX_VALUE;
            double minPrice=Double.MAX_VALUE;

            for (TradeOffers next : collect) {
                double iterQuality = next.getQuality();
                double iterPrice = next.getPrice();
                if (iterPrice==minPrice){
                     if (iterQuality==quality){
                         tradeOffers=(tradeOffers.getFree_for_buy()>next.getFree_for_buy() ? tradeOffers : next);
                         quality = iterQuality;
                         minPrice=iterPrice;
                     } else {
                         tradeOffers = (next.getQuality() > tradeOffers.getQuality() ? next : tradeOffers);
                         quality = iterQuality;
                         minPrice = iterPrice;
                     }
                } else if (iterPrice<minPrice) {
                    tradeOffers = next;
                    quality = iterQuality;
                    minPrice=iterPrice;
                }
            }
            map.put(quality,tradeOffers);
            return map;
    }

    @Cacheable("tradeOffersById") // установить время жизни кеша = 10мин
    public List<TradeOffers> getListTradeOffers(long productId) {
        System.out.println("first search ");
        String content = getContent(productId);
        List<TradeOffers> list = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(content);
            JSONObject dataValue = jsonObject.getJSONObject("data");
            Set<String> keys = dataValue.keySet();
            for (String key : keys) {
                TradeOffers tradeOffers;
                try {
                    tradeOffers = mapper.readValue(dataValue.get(key).toString(), TradeOffers.class);
//                    System.out.println(tradeOffers.getProduct_name());
//                    System.out.println("end name!");
                    list.add(tradeOffers);
                } catch (IOException e) {
                    System.out.println(content);
                    e.printStackTrace();
                }
            }
        }catch (JSONException e){
            System.out.println(content);
            return list;
        }
        return list;
    }


    private String getContent(long productId) {
        return htmlPageData.getContent("https://virtonomica.ru/api/lien/main/marketing/report/trade/offers?product_id=" + productId + "&pagesize=1000");
    }

    private boolean isNotEmptyCollect(List<TradeOffers> collect) {
        return collect.size()!=0;
    }

    private int[] qualityArray() {
        return new int[]{1,2,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,105,110,115,120,125,130};//19 size;
    }

    private List<TradeOffers> getQualityRange(List<TradeOffers> list, double q_min, double q_max) {
        return list.stream()
                .filter(t -> t.getQuality() >= q_min && t.getQuality() < q_max)
                .collect(Collectors.toList());
    }
    //top-10 the best shops by product

    public LinkedHashMap<Long, List<MarketingReport>> getReportLargestStores(long retailProductId){
        LinkedHashMap<Long,List<MarketingReport>>map = new LinkedHashMap<>();
        List<MarketingReport>marketingReports = new ArrayList<>();
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/marketing/report/retail/units?product_id=" + retailProductId);
        JSONArray jsonArray = new JSONArray(content);
        for (int i = 0; i < jsonArray.length(); i++) {
            String string = jsonArray.getJSONObject(i).toString();
            try {
                MarketingReport marketingReport = mapper.readValue(string, MarketingReport.class);
                marketingReports.add(marketingReport);
//                System.out.println(marketingReport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put(retailProductId,marketingReports);
        return map;
    }

    public FactoryInfo_For_Offer getMyFactoryInfoForOffer(long idCompanyUnit, long productId){
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/unit/offer/browse?id=" + idCompanyUnit);
        JSONObject jsonObject = new JSONObject(content);
        Set<String> strings = jsonObject.keySet();
        for (String string : strings) {
            String s = string.replaceAll("[^0-9]", "");
            long prodId = Long.parseLong(s);
            if (prodId!=productId) continue;
            JSONObject jsonOValue = jsonObject.getJSONObject(string);
            System.out.println(idCompanyUnit);
            System.out.println(string);
            System.out.println(jsonOValue);

            try {
                FactoryInfo_For_Offer factoryInfo_for_offer = mapper.readValue(jsonOValue.toString(), FactoryInfo_For_Offer.class);
                System.out.println("my offers:");
                System.out.println(factoryInfo_for_offer.getProduct_name()+" "+factoryInfo_for_offer.getQuality()+" "+factoryInfo_for_offer.getPrime_cost());
                return factoryInfo_for_offer;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private WebClient getWebClient(){
        return UserWebClient.webClient;
    }

}
