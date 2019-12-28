package ua.com.virtonomica.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.entity.company.Company;
import ua.com.virtonomica.utils.reports.CompanyReportFinance;
import ua.com.virtonomica.service.company.CompanyService;
import ua.com.virtonomica.utils.reports.MarketingReport;
import ua.com.virtonomica.utils.reports.UnitsReportFinance;
import ua.com.virtonomica.utils.reports.UnitsSupplyContracts;
import ua.com.virtonomica.web.connection.UserWebClient;
import ua.com.virtonomica.web.api.analysis.industry_analysis.product_search.TradeOffers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Component
public class VirtonomicaAPI {

    private final CompanyService companyService;
    private final ObjectMapper mapper = new ObjectMapper();
    private HtmlPageData htmlPageData = new HtmlPageData();

    @Autowired
    public VirtonomicaAPI(CompanyService companyService) {
        this.companyService = companyService;
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
        WebClient webClient = getWebClient();
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

    public void getTradeOffersByProductId(long productId){
        String content = htmlPageData.getContent("https://virtonomica.ru/api/lien/main/marketing/report/trade/offers?product_id=" + productId);

        JSONObject jsonObject = new JSONObject(content);
        System.out.println(jsonObject);
        try {
            JSONObject dataValue = jsonObject.getJSONObject("data");
            Set<String> keys = dataValue.keySet();
            for (String key : keys) {
                TradeOffers tradeOffers = null;
                try {
                    tradeOffers = mapper.readValue(dataValue.get(key).toString(), TradeOffers.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println();
                System.out.println("trade offers:");
                System.out.println(tradeOffers);
//                return;
            }

        }catch (JSONException e){
            System.out.println(productId +" " +"пропозицій на ринку на цей товар немає!");
        }
        System.out.println("****************************************");
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
                System.out.println(marketingReport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put(retailProductId,marketingReports);
        return map;
    }

    private WebClient getWebClient(){
        return UserWebClient.webClient;
    }
}
