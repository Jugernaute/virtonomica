package ua.com.virtonomica.controllers.admin;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;
import ua.com.virtonomica.utils.create.geography.GeographyUnitBuilder;
import ua.com.virtonomica.utils.create.industrial.IndustrialWrapper;
import ua.com.virtonomica.utils.create.industrial.IndustrialUnitBuilder;
import ua.com.virtonomica.utils.create.my_company_units.MyCompanyUnitsBuilder;
import ua.com.virtonomica.utils.create.my_company_units.MyCompanyUnitsWrapper;
import ua.com.virtonomica.utils.create.product.main_product.MainProductUnitBuilder;
import ua.com.virtonomica.utils.create.product.main_product.MainProductWrapper;
import ua.com.virtonomica.utils.create.product.retail_product.RetailProductUnitBuilder;
import ua.com.virtonomica.utils.create.product.retail_product.RetailProductWrapper;
import ua.com.virtonomica.web.DownloadImageFromUrl;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.connection.UserWebClient;
import ua.com.virtonomica.web.connection.Virtonomica;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    @Autowired
    MyCompanyUnitsService myCompanyUnitsService;

    private final Virtonomica virtonomica;
    private final DownloadImageFromUrl downloadImageFromUrl;
    private final VirtonomicaAPI virtonomicaAPI;
    private final GeographyUnitBuilder geographyBuilder;
    private final IndustrialUnitBuilder industrialBuilder;
    private final MainProductUnitBuilder mainProductUnitBuilder;
    private final RetailProductUnitBuilder retailProductUnitBuilder;
    private final MyCompanyUnitsBuilder myCompanyUnitsBuilder;

    @Autowired
    public AdminRestController(Virtonomica virtonomica, DownloadImageFromUrl downloadImageFromUrl, VirtonomicaAPI virtonomicaAPI,
                           GeographyUnitBuilder geographyBuilder,
                           IndustrialUnitBuilder industrialBuilder,
                           MainProductUnitBuilder mainProductUnitBuilder,
                           RetailProductUnitBuilder retailProductUnitBuilder,
                           MyCompanyUnitsBuilder myCompanyUnitsBuilder) {
        this.virtonomica = virtonomica;
        this.downloadImageFromUrl = downloadImageFromUrl;
        this.virtonomicaAPI = virtonomicaAPI;
        this.retailProductUnitBuilder = retailProductUnitBuilder;
        this.geographyBuilder = geographyBuilder;
        this.industrialBuilder = industrialBuilder;
        this.mainProductUnitBuilder = mainProductUnitBuilder;
        this.myCompanyUnitsBuilder = myCompanyUnitsBuilder;
    }

    @CrossOrigin
    @PostMapping("/testTampermonkey")
    private void allApi(@RequestBody String json) {
//        System.out.println(json);
        JSONObject object = new JSONObject(json);
//        object.keySet()
    }

    @CrossOrigin
    @PostMapping("/getCookie")
    private void getCookie(@RequestBody String cookie){
        UserWebClient.webClient = virtonomica.getWebClient(cookie);
    }

    @GetMapping("/company")
    private void companyInfo() {
        virtonomicaAPI.getCompanyInfo();
    }

    @GetMapping("/company/units")
    private void companyUnits() {
            String json = virtonomicaAPI.getCompanyUnits();
            myCompanyUnitsBuilder.build(json,new MyCompanyUnitsWrapper());
//        List<CompanyUnits> prods = myCompanyUnitsService.findByProductNameAndUnitTypeId("marshmallows", 1886);
//        System.out.println(">>>>>");
//        prods.forEach(System.out::println);
    }

    @PostMapping("/regionsAndCountries")
    private void regions(@RequestBody String regions) {
        geographyBuilder.build(regions, new GeographyWrapper());
    }

    @PostMapping("/main_products")
    private void allProduct(@RequestBody String json) {
        mainProductUnitBuilder.build(json,new MainProductWrapper());
    }

    @PostMapping("/retail_products")
    private void retailProduct(@RequestBody String json) {
        retailProductUnitBuilder.build(json, new RetailProductWrapper());
    }

    @PostMapping("/industry")
    private void industry(@RequestBody String json) {
        industrialBuilder.build(json, new IndustrialWrapper());
    }

    @GetMapping("/image/download/")
    private void downloadImage(){
        downloadImageFromUrl.writeImages();
    }


}
