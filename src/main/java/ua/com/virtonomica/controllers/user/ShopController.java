package ua.com.virtonomica.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.virtonomica.dto.shop_products.ShopInfo;
import ua.com.virtonomica.dto.shop_products.ShopsProducts;
import ua.com.virtonomica.dto.shop_products.ShopsProductsDto;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.service.company.company_products.CompanyProductsService;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.service.images.ProductImagesService;
import ua.com.virtonomica.service.industrial.unit_class.UnitClassService;
import ua.com.virtonomica.utils.reports.UnitsSupplyContracts;
import ua.com.virtonomica.web.myUnitsControl.shops.ShopSettings;

import java.util.*;

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
        ShopsProductsDto shopsProductsDto = new ShopsProductsDto();
        try {
            long idShop = unitClassService.getIdByUnitClass_Name("Магазин");

            HashSet<ShopsProducts> productsOfUnitType = companyProductsService.findProductsRealizedByUnitClass(idShop);
            for (ShopsProducts shopsProducts : productsOfUnitType) {
                System.out.println(shopsProducts.getCompany_product_name()+" "+ shopsProducts.getCity_name());
            }
            HashMap<ShopInfo, HashSet<CompanyProducts>> map= shopsProductsDto.shopsAndProducts(productsOfUnitType);

            System.out.println(map.size());
            for (ShopInfo companyUnits : map.keySet()) {
//                System.out.println(companyUnits+" "+ map.get(companyUnits));
            }
        model.addAttribute("units",map);
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
//        List<CompanyProducts> products = companyProductsService.findProductsRealizedByUnitClass(id);


    }

    @GetMapping("/company/units/shop/")
    private String shop(@RequestParam long unitId,
                        @RequestParam long unitTypeId,
                      Model model){
        // якщо unitTypeId == магазин, тоді все норм
        // цей метод тільки для магазинів
        List<UnitsSupplyContracts> contracts = shopSettings.SupplyTab(unitId);

        System.out.println(unitId);
        System.out.println(unitTypeId);
        model.addAttribute("contracts", contracts);
        return "";
    }
}
