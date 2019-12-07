package ua.com.virtonomica.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.virtonomica.utils.reports.CompanyReportFinance;
import ua.com.virtonomica.utils.reports.UnitsReportFinance;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.myUnitsControl.shops.ShopSettings;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final VirtonomicaAPI virtonomicaAPI;
    private final ShopSettings shopSettings;

    public AdminController(VirtonomicaAPI virtonomicaAPI, ShopSettings shopSettings) {
        this.virtonomicaAPI = virtonomicaAPI;
        this.shopSettings = shopSettings;
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
}
