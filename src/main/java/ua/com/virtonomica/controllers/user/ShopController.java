package ua.com.virtonomica.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.service.company.company_products.CompanyProductsService;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.service.images.ProductImagesService;
import ua.com.virtonomica.service.industrial.unit_class.UnitClassService;
import ua.com.virtonomica.service.industrial.unit_type.UnitTypeService;
import ua.com.virtonomica.utils.reports.UnitsSupplyContracts;
import ua.com.virtonomica.web.myUnitsControl.shops.ShopSettings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
public class ShopController {
    private final MyCompanyUnitsService myCompanyUnitsService;
    private final CompanyProductsService companyProductsService;
    private final UnitClassService unitClassService;
    private final ProductImagesService productImagesService;
    private final ShopSettings shopSettings;

    @Autowired
    public ShopController(MyCompanyUnitsService myCompanyUnitsService, CompanyProductsService companyProductsService, UnitClassService unitClassService, ProductImagesService productImagesService, ShopSettings shopSettings) {
        this.myCompanyUnitsService = myCompanyUnitsService;
        this.companyProductsService = companyProductsService;
        this.unitClassService = unitClassService;
        this.productImagesService = productImagesService;
        this.shopSettings = shopSettings;
    }

    @GetMapping("/company/units/shops")
    private String shops(Model model){
        System.out.println("start");
        try {
            long idShop = unitClassService.getIdByUnitClass_Name("Магазин");

        List<CompanyProducts> productsOfUnitType = companyProductsService.findProductsRealizedByUnitClass(idShop);
        HashMap<String,String>hashMap = new HashMap<>();
        for (CompanyProducts companyProducts : productsOfUnitType) {
            String symbol = companyProducts.getSymbol();
            ProductImage image = productImagesService.findByProductSymbol(symbol);
            hashMap.put(symbol,image.getSymbol());
        }
        HashSet<Object> objects = new HashSet<>();
//        productsOfUnitType.forEach(System.out::println);
        model.addAttribute("units",hashMap);
        }catch (NullPointerException e){
            System.out.println("Не існує такого unit_class_name в базі даних");
        }

        return "shops";
    }

    @GetMapping("/company/units/shop/{id}")
    private String shop(Model model,
                        @PathVariable long id){
        CompanyUnits one = myCompanyUnitsService.findById(id);
        System.out.println(one);
        return "shops";
    }

    @GetMapping("/company/units/shop_unprofitable/")
    private void shop_unprofitable(Model model){
//        shopSettings.SupplyTab(4343908L);
        List<UnitsSupplyContracts> contracts = shopSettings.SupplyTab(5306807L);
//        contracts.forEach(System.out::println);


        long id = unitClassService.getIdByUnitClass_Name("Завод");
        List<CompanyProducts> products = companyProductsService.findProductsRealizedByUnitClass(id);


    }

    @GetMapping("/company/units/shop/")
    private String shop(@RequestParam long unitId,
                        @RequestParam long unitTypeId,
                      Model model){
        // якщо unitTypeId == магазин, тоді все норм. Так
        // цей метод тільки для магазинів
        List<UnitsSupplyContracts> contracts = shopSettings.SupplyTab(unitId);

        System.out.println(unitId);
        System.out.println(unitTypeId);
        model.addAttribute("contracts", contracts);
        return "";
    }
}
