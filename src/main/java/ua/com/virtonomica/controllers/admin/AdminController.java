package ua.com.virtonomica.controllers.admin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.service.industrial.unit_type.UnitTypeService;
import ua.com.virtonomica.utils.reports.CompanyReportFinance;
import ua.com.virtonomica.utils.reports.UnitsReportFinance;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.api.analysis.industry_analysis.write_json.WriteDataIntoJson;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.FactoryInfo;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitClassEnum;
import ua.com.virtonomica.web.myUnitsControl.shops.ShopSettings;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final VirtonomicaAPI virtonomicaAPI;
    private final ShopSettings shopSettings;
    private final UnitTypeService unitTypeService;

    public AdminController(VirtonomicaAPI virtonomicaAPI, ShopSettings shopSettings, UnitTypeService unitTypeService) {
        this.virtonomicaAPI = virtonomicaAPI;
        this.shopSettings = shopSettings;
        this.unitTypeService = unitTypeService;
    }

    @GetMapping("/company/report/finance")
    private String reportFinance(Model model){
        List<CompanyReportFinance> companyReportFinance = virtonomicaAPI.getCompanyReportFinance();
        model.addAttribute("finance", companyReportFinance);
        return "companyFinanceReport";
    }

    @GetMapping("/units/report/finance/{value}")
    private String unitsReport(Model model,
                             @PathVariable String value){
        List<UnitsReportFinance> unitsReportFinance = virtonomicaAPI.getUnitsReportFinance(value);
        Collections.sort(unitsReportFinance);
        model.addAttribute("finances",unitsReportFinance);
        return "unitsFinanceReport";
    }

    @GetMapping("/write/factory/info")
    private void writeFactoryJson(){
        List<UnitType> unitTypes = unitTypeService.getUnitTypeByUnitClassName(UnitClassEnum.Завод);
        FactoryInfo factoryInfo = new FactoryInfo();
        List<FactoryInfo> factoryInfos = factoryInfo.industryCalculation(unitTypes);
        ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setVisibility(PropertyAccessor.FIELD,JsonAutoDetect.Visibility.ANY);//якщо якісь поля в класі приватні і не мають сетерів і гетерів
        try {
            mapper.writeValue(new File("factory_info.json"), factoryInfos);
            System.out.println("Success write file to 'factory_info.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/write/calculateByKvala")
    private void writeCalculationByKvala(){
        WriteDataIntoJson writeDataIntoJson = new WriteDataIntoJson();
        writeDataIntoJson.write("http://virtacalc.pluton-host.ru/top3.php",new File("data_qualification.json"));

    }
}
