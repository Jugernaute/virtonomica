package ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.dto.calculate_factory.ProfitCalculateFactoryDto;
import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.service.industrial.unit_type.UnitTypeService;
import ua.com.virtonomica.service.product.retail_product.RetailProductService;
import ua.com.virtonomica.utils.reports.MarketingReport;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.CollectionInformationForFactoryCalculate;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitSpecialization;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SelectionBestCalculation {
    private List<ResultDataAfterCalculateFactory> resultDataAfterCalculateFactoryList;
//    private List<String> optionsProfitDependingIngredients = new ArrayList<>(); // for console debug!!!

    private final UnitTypeService unitTypeService;
    private final RetailProductService retailProductService;
    private final VirtonomicaAPI virtonomicaAPI;


    @Autowired
    public SelectionBestCalculation(UnitTypeService unitTypeService, RetailProductService retailProductService, VirtonomicaAPI virtonomicaAPI) {
        this.unitTypeService = unitTypeService;
        this.retailProductService = retailProductService;
        this.virtonomicaAPI = virtonomicaAPI;
    }

    public HashMap<String, ProfitCalculateFactoryDto> start(List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates) {
        resultDataAfterCalculateFactoryList = new ArrayList<>(); //щоб при рефреші сторінки, список очищався
        resultDataAfterCalculateFactoryList = allPropositionForCalculate(factoryInfoForCalculates);
        resultDataAfterCalculateFactoryList.sort(Comparator.comparingInt(r-> (int) r.getProductId())); // sort important!
        return bestCalculation(resultDataAfterCalculateFactoryList);
    }

    private List<ResultDataAfterCalculateFactory> allPropositionForCalculate(List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates) {
        ExcludedUnitsFromCalculate excludedUnitsFromCalculate = new ExcludedUnitsFromCalculate();
        for (CollectionInformationForFactoryCalculate factoryInfoForCalculate : factoryInfoForCalculates) {
            List<UnitSpecialization> unitSpecializations = factoryInfoForCalculate.getUnitSpecializations();
            String unitName = factoryInfoForCalculate.getUnitName();
//                if (!unitName.equalsIgnoreCase("Автосборочный завод")) continue;
            long id = unitTypeService.getIdByUnitType_Name(unitName);

            boolean b = excludedUnitsFromCalculate.checkExclude(id);
            boolean b1 = excludedUnitsFromCalculate.checkExclude(unitName);
            if (b || b1) continue;
            for (UnitSpecialization specialization : unitSpecializations) {
//                      if (!specialization.getSpecialization().equalsIgnoreCase("Кондитерские изделия") ) continue;

                long productId = Long.parseLong(specialization.getProductId());
                boolean b2 = excludedUnitsFromCalculate.checkExclude(productId);
                if (b2) continue;

                CalculateFactoryByIngredients calculate = new CalculateFactoryByIngredients();
                ResultDataAfterCalculateFactory result = calculate.result(specialization);
                if (result!=null) resultDataAfterCalculateFactoryList.add(result);
            }
        }
        return resultDataAfterCalculateFactoryList;
    }

    private HashMap<String,ProfitCalculateFactoryDto> bestCalculation(List<ResultDataAfterCalculateFactory> resultDataAfterCalculateFactoryList) {
        HashMap<String,ProfitCalculateFactoryDto> profitCalculateFactoryDtoHashMap = new HashMap<>();
        List<MarketingReport> marketingReports = new ArrayList<>();
        LinkedHashMap<ResultDataAfterCalculateFactory,List<IngredientsOfSpecialization>> data = new LinkedHashMap<>();

        long id = 0;
        double minQuality = 0;  // in list largest stores
        double maxPrice = 0;    // in list largest stores

//        int i=0;

        for (ResultDataAfterCalculateFactory resultDataAfterCalculateFactory : resultDataAfterCalculateFactoryList) {
//            i++;
//            if (i<40) continue;
            long productId = resultDataAfterCalculateFactory.getProductId();
            double productQuality = resultDataAfterCalculateFactory.getProductQuality();
            double productCostPrice = resultDataAfterCalculateFactory.getProductCostPrice();
            int productCount = resultDataAfterCalculateFactory.getProductCount();
            List<IngredientsOfSpecialization> ingredients = resultDataAfterCalculateFactory.getIngredientsOfSpecializationList();
            List<RetailProduct> retailProducts = retailProductService.findByIdKey(productId); //list, because retail prod has composite key
            int size = retailProducts.size();

            if (size>1) {   // на всяк випадок!!  спосок повинен завжди мати розмір = 1
                System.out.println(">>ERROR: ");
                retailProducts.forEach(System.out::println);
                throw new IndexOutOfBoundsException();
            } else if (size==0){
                continue;
            }

            // цей код в такому вигляді лише для консольного виведення
            // потім можна скоротити все
//                if (id!=productId){
            RetailProduct retailProduct = retailProducts.get(0);
            LinkedHashMap<Long, List<MarketingReport>> reportLargestStores = virtonomicaAPI.getReportLargestStores(retailProduct.getId());
                    for (Long idProduct : reportLargestStores.keySet()) {
                        marketingReports = reportLargestStores.get(idProduct);
                        maxPrice = marketingReports.stream()
                                .mapToDouble(MarketingReport::getPrice).max().getAsDouble();
                        minQuality = marketingReports.stream()
                                .mapToDouble(MarketingReport::getQuality).min().getAsDouble();

                    }
                id=productId;
            if (productQuality>=minQuality && productCostPrice<=maxPrice){
                    String name = retailProduct.getName();

                    //for debug
//                    System.out.println(name + " qty: "+ productQuality +", price: "+ productCostPrice + ", count: " + productCount);

                    List<MarketingReport> reports = getLowersThanRequiredQuality(marketingReports, productQuality);
                    if (reports.size()==0)continue;

                    // виводимо маркетинговий звіт(найбыльших магазинів) з якістю яка є меншою від нашої -> всі магазини => for debug
//                    printMarketingReports(marketingReports);

                    // вивід маркетингового звіту (найбільших магазинів), з якістю яка є меншою від нашої - найвища пропозиція ціни
//                    System.out.println("вивід маркетингового звіту (найбільших магазинів), з якістю яка є меншою від нашої - найвища пропозиція ціни:");
                    MarketingReport marketingReport = getReportBetterStoreWithLowersQuality(reports);
                    double marketPrice = marketingReport.getPrice();
                   long profit = getProfit(productCount,marketPrice,productCostPrice);
                    if (profit<=40000000) continue; // якщо прибуток менший 40кк - пропускаємо
                    // декілька варіантів профіту в залежності від інградієнтів
//                    addToOptionsProfit(marketingReport, productId, productQuality, productCostPrice, profit);

//                    print(marketingReport);

                    ProfitCalculateFactoryDto dto = new ProfitCalculateFactoryDto(productId,name, resultDataAfterCalculateFactory,marketingReport,profit);
                    boolean b = profitCalculateFactoryDtoHashMap.containsKey(name);
                    if (!b){
                        profitCalculateFactoryDtoHashMap.put(name,dto);
                    } else {
                        if (productCostPrice>marketPrice){
                            profitCalculateFactoryDtoHashMap.put(name, dto);
                        }
                    }
                }
        }
//        System.out.println("list profit:");
//        optionsProfitDependingIngredients.forEach(System.out::println);
        printProfitCalculateFactoryDto(profitCalculateFactoryDtoHashMap);
        return profitCalculateFactoryDtoHashMap;
    }


    // декілька варіантів профіту в залежності від інградієнтів
    // for debug
//    private void addToOptionsProfit(MarketingReport marketingReport, long productId, double productQuality, double productCostPrice, long profit) {
//        String text = productId+" "+ productQuality+" "+productCostPrice+" "+" ,profit = "+ profit;
//        optionsProfitDependingIngredients.add(text);
//    }

    private long getProfit(int productCount, double marketPrice, double productCostPrice) {
        double profPrice = productCount * (marketPrice - productCostPrice);
        return Double.valueOf(profPrice).longValue();
    }
    private void printIngredients(List<IngredientsOfSpecialization> ingredients) {
        for (IngredientsOfSpecialization i : ingredients) {
            System.out.println("    name: " + i.getMaterial());
            System.out.println("    quality: " + i.getQuality());
            System.out.println("    price: " + i.getPrice());
            System.out.println();
        }
    }

    private List<MarketingReport> getLowersThanRequiredQuality(List<MarketingReport> marketingReports, double productQuality) {
        return marketingReports.stream()
                .filter(m -> m.getQuality() <= productQuality)
                .collect(Collectors.toList());

    }

    private void printMarketingReports(List<MarketingReport> marketingReports) {
        System.out.println("виводимо маркетинговий звіт(найбыльших магазинів) з якістю яка є меншою від нашої -> всі магазини:");
        for (MarketingReport report : marketingReports) {
            printMarketingReport(report);
        }
        System.out.println(">>");
    }

    private void printMarketingReport(MarketingReport marketingReport){
        System.out.println(
                "count: "+marketingReport.getQty()+
                        " quality: "+marketingReport.getQuality()+
                        " price: "+marketingReport.getPrice()+
                        " brand: "+marketingReport.getBrand()+
                        " company: " + marketingReport.getCompany_name()+
                        " country: "+marketingReport.getGeographyWrapper().getCountry().getName());

    }

    //for debug
    private void printProfitCalculateFactoryDto(HashMap<String, ProfitCalculateFactoryDto> profitCalculateFactoryDtoHashMap) {
        profitCalculateFactoryDtoHashMap.forEach((k,v)-> System.out.println(k+" : \r\n       "
                +v.getResultDataAfterCalculateFactory().getIngredientsOfSpecializationList()+ " \r\n         "
                +v.getMarketingReport()+" \r\n      "
                +"cost price: "+ v.getResultDataAfterCalculateFactory().getProductCostPrice()+ " \r\n         "
                +"count: "+ v.getResultDataAfterCalculateFactory().getProductCount()+ " \r\n         "
                +"quality: "+ v.getResultDataAfterCalculateFactory().getProductQuality()+ " \r\n         "
                +"profit: "+ v.getProfit()));
        System.out.println("---------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------");

    }

    private MarketingReport getReportBetterStoreWithLowersQuality(List<MarketingReport> reports) {
        return reports.stream()
                .reduce((o1, o2) -> o2.getPrice() < o1.getPrice() ? o1 : o2)
                .get();

    }

}
