package ua.com.virtonomica.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.entity.company.Company;
import ua.com.virtonomica.utils.reports.CompanyReportFinance;
import ua.com.virtonomica.service.company.CompanyService;
import ua.com.virtonomica.utils.reports.UnitsReportFinance;
import ua.com.virtonomica.utils.reports.UnitsSupplyContracts;
import ua.com.virtonomica.web.connection.UserWebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class VirtonomicaAPI {

    private final CompanyService companyService;

    @Autowired
    public VirtonomicaAPI(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void getCompanyInfo(){
        WebClient webClient = UserWebClient.webClient;
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/my/company");
            String content= page.getWebResponse().getContentAsString();
            if (content.equals("false")){
                System.out.println("Content must not have 'false'");
                return;
            }
            Company company = new ObjectMapper().readValue(content, Company.class);
            companyService.update(company);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FailingHttpStatusCodeException e){
            System.out.println("Идет пересчет игровой ситуации");
        }
    }

    public String getCompanyUnits(){
        String content = null;
        WebClient webClient = UserWebClient.webClient;
        if (webClient!=null){
        long id = companyService.getCompany().getId();
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/company/units?id="+id+"&pagesize=30000");
            content= page.getWebResponse().getContentAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else System.out.println("Спочатку потрібно зайти на головну сторінку ігри, щоб отримати куки");

        return content;
    }

    public List<CompanyReportFinance> getCompanyReportFinance(){
        WebClient webClient = UserWebClient.webClient;
        int companyId = CompanyReportFinance.getCompanyId();
        List<CompanyReportFinance> finance = null;
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/company/report/finance/byitem?id=" + companyId);
            String content = page.getWebResponse().getContentAsString();
            finance = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
            ObjectMapper mapper = new ObjectMapper();
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
        WebClient webClient = UserWebClient.webClient;
        List<UnitsReportFinance>finances = new ArrayList<>();
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/company/report/units/" + param + "?id=3247145&pagesize=1000");
            String content = page.getWebResponse().getContentAsString();
            JSONObject jsonObject = new JSONObject(content);
            Set<String> keySet = jsonObject.keySet();
            for (String s : keySet) {
                String o = jsonObject.get(s).toString();
                ObjectMapper mapper = new ObjectMapper();
                UnitsReportFinance unitsReportFinance = mapper.readValue(o, UnitsReportFinance.class);
                finances.add(unitsReportFinance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finances;
    }

    public List<UnitsSupplyContracts> getSupplyContractsByUnitId(long unitId){
        WebClient webClient = UserWebClient.webClient;
        String content;
        List<UnitsSupplyContracts> contracts = new ArrayList<>();
        try {
            Page page = webClient.getPage("https://virtonomica.ru/api/lien/main/unit/supply/contracts?id=" + unitId);
            content = page.getWebResponse().getContentAsString();
            JSONObject jsonObject = new JSONObject(content);
            Set<String> keySet = jsonObject.keySet();
            for (String s : keySet) {
                String o = jsonObject.get(s).toString();
                ObjectMapper mapper = new ObjectMapper();
                UnitsSupplyContracts unitsSupplyContracts = mapper.readValue(o, UnitsSupplyContracts.class);
                contracts.add(unitsSupplyContracts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contracts;
    }

}
