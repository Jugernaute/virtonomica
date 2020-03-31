package ua.com.virtonomica.controllers.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.virtonomica.dto.calculate_factory.ProfitCalculateFactoryDto;
import ua.com.virtonomica.entity.RequiredMinValuesForProductToCalculateFactory;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.service.RequiredMinValuesForProductToCreateFactoryService;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.service.industrial.unit_type.UnitTypeService;
import ua.com.virtonomica.service.product.main_product.MainProductService;
import ua.com.virtonomica.service.product.retail_product.RetailProductService;
import ua.com.virtonomica.utils.reports.CompanyReportFinance;
import ua.com.virtonomica.utils.reports.UnitsReportFinance;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients.RequiredDataForCalculateProduction;
import ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients.SelectionBestCalculation;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.CollectionInformationForFactoryCalculate;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.read_file.ReadFile;
import ua.com.virtonomica.web.api.analysis.industry_analysis.search.SearchTradeOffersByProduct;
import ua.com.virtonomica.web.api.analysis.industry_analysis.search.TradeOffers;
import ua.com.virtonomica.web.api.analysis.industry_analysis.write_json.WriteDataIntoJson;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitClassEnum;
import ua.com.virtonomica.web.myUnitsControl.shops.ShopSettings;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final VirtonomicaAPI virtonomicaAPI;
    private final ShopSettings shopSettings;
    private final UnitTypeService unitTypeService;
    private final SearchTradeOffersByProduct searchTradeOffersByProduct;
    private final MyCompanyUnitsService myCompanyUnitsService;
    private final ObjectMapper objectMapper;
    private final RetailProductService retailProductService;
    private final MainProductService mainProductService;
    private final RequiredMinValuesForProductToCreateFactoryService requiredMinValuesForProductToCreateFactoryService;
    private final SelectionBestCalculation selectionBestCalculation;

    public AdminController(VirtonomicaAPI virtonomicaAPI, ShopSettings shopSettings, UnitTypeService unitTypeService, SearchTradeOffersByProduct searchTradeOffersByProduct, MyCompanyUnitsService myCompanyUnitsService, ObjectMapper objectMapper, RetailProductService retailProductService, MainProductService mainProductService, RequiredMinValuesForProductToCreateFactoryService requiredMinValuesForProductToCreateFactoryService, SelectionBestCalculation selectionBestCalculation) {
        this.virtonomicaAPI = virtonomicaAPI;
        this.shopSettings = shopSettings;
        this.unitTypeService = unitTypeService;
        this.searchTradeOffersByProduct = searchTradeOffersByProduct;
        this.myCompanyUnitsService = myCompanyUnitsService;
        this.objectMapper = objectMapper;
        this.retailProductService = retailProductService;
        this.mainProductService = mainProductService;
        this.requiredMinValuesForProductToCreateFactoryService = requiredMinValuesForProductToCreateFactoryService;
        this.selectionBestCalculation = selectionBestCalculation;
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
    private String writeFactoryJson(){
        List<UnitType> unitTypes = unitTypeService.getUnitTypeByUnitClassName(UnitClassEnum.Завод);
        CollectionInformationForFactoryCalculate factoryInfoForCalculate = new CollectionInformationForFactoryCalculate();
        List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates = factoryInfoForCalculate.start(unitTypes);
        factoryInfoForCalculates.forEach(f-> System.out.println(f.toString()));
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.setVisibility(PropertyAccessor.FIELD,JsonAutoDetect.Visibility.ANY);//якщо якісь поля в класі приватні і не мають сетерів і гетерів
        try {
            objectMapper.writeValue(new File("factory_info.json"), factoryInfoForCalculates);
            System.out.println("Success write file to 'factory_info.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "admin";
    }

    @GetMapping("/write/productTradeOffers")
    private String writeProductTradeOffers(){
        RequiredDataForCalculateProduction requiredDataForCalculateProduction = new RequiredDataForCalculateProduction();
        HashSet<String> products=new HashSet<>();
        HashMap<String,Set<Integer>>productCount = new HashMap<>();
        Set<List<TradeOffers>> offers = new HashSet<>();
        int countOfIngredients;

        List<TradeOffers> tradeOffers = null;
        try {
            TypeReference<List<CollectionInformationForFactoryCalculate>> ref = new TypeReference<List<CollectionInformationForFactoryCalculate>>() {};
            List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates = objectMapper.readValue(new File("factory_info.json"), ref);

           System.out.println("read file success");

            for (CollectionInformationForFactoryCalculate factoryInfoForCalculate : factoryInfoForCalculates) {
//                if (!factoryInfoForCalculate.getUnitName().equalsIgnoreCase("Завод климатической техники")) {
//                    continue;
//                }
                List<UnitSpecialization> unitSpecialization = factoryInfoForCalculate.getUnitSpecializations();
                for (UnitSpecialization specialization : unitSpecialization) {
                    List<IngredientsOfSpecialization> ingredients = specialization.getIngredients();
                    double productionsByPerson = specialization.getProductionsByPerson();
                    for (IngredientsOfSpecialization ingredient : ingredients) {
                        String product = ingredient.getMaterial();
                        countOfIngredients = requiredDataForCalculateProduction.getIngrTotalCount(ingredient,productionsByPerson);
                        tradeOffers = searchTradeOffersByProduct.getTradeOffers(product,countOfIngredients);
                        if(tradeOffers!=null){
                            offers.add(tradeOffers);
                        }
                        System.out.println("need count: " + countOfIngredients);
                        for (TradeOffers t : tradeOffers) {
                            System.out.println("quality: "+ t.getQuality()+" ,freeForBuy: "+t.getFree_for_buy()+ " ,price: "+t.getPrice()+" ,count: "+t.getQuantity());
                        }
//                        tradeOffers = null;
                        System.out.println("=============================================================================================");

                    }

                }
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            if(tradeOffers!=null){
                offers.add(tradeOffers);
                tradeOffers.forEach(t-> System.out.println(t.getProduct_name()+", "+" ,free: " + t.getFree_for_buy()+" ,quality: "+t.getQuality()+" ,price: " + t.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        WriteDataIntoJson writeDataIntoJson = new WriteDataIntoJson();
        writeDataIntoJson.write(offers,new File("product_trade_offers.json"));

        return "admin";
    }

    @GetMapping("/write/calculateByKvala")
    private String writeCalculationByKvala(){
        WriteDataIntoJson writeDataIntoJson = new WriteDataIntoJson();
        writeDataIntoJson.write("http://virtacalc.pluton-host.ru/top3.php",new File("data_qualification.json"));
    return "admin";
    }

    @GetMapping("/analyse/calculate/factory")
    private String calculateFactory(){
        ReadFile<CollectionInformationForFactoryCalculate> readFile = new ReadFile<>();
        WriteDataIntoJson writeDataIntoJson = new WriteDataIntoJson();

        List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates = readFile.read(CollectionInformationForFactoryCalculate.class, objectMapper, "factory_info.json");

        HashMap<String, ProfitCalculateFactoryDto> start = selectionBestCalculation.start(factoryInfoForCalculates);
        LinkedHashMap<String, ProfitCalculateFactoryDto> collect = start.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//        writeDataIntoJson.write(collect,new File("calculate_profit.json"));
        return "admin";
    }

    @PostMapping(value = "/writeMinQualityAndPriceForProduct",
            consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    private String writeMinProduct(@RequestParam String name,
                                   @RequestParam Double minQuality,
                                   @RequestParam Double maxPrice){

        boolean existByName = mainProductService.isExistByName(name);
        if (existByName && minQuality!=null && maxPrice!=null){
            System.out.println("write....");
            RequiredMinValuesForProductToCalculateFactory x = new RequiredMinValuesForProductToCalculateFactory(name.toLowerCase(), minQuality, maxPrice);
            requiredMinValuesForProductToCreateFactoryService.save(x);
            System.out.println("Success write: "+ x);
        }
        else {
            System.out.println("wrong product writing: "+name);
        }
        return "admin";
    }

//    private void print(MarketingReport marketingReport){
//        System.out.println(
//                "count: "+marketingReport.getQty()+
//                        " quality: "+marketingReport.getQuality()+
//                        " price: "+marketingReport.getPrice()+
//                        " brand: "+marketingReport.getBrand()+
//                        " company: " + marketingReport.getCompany_name()+
//                        " country: "+marketingReport.getGeographyWrapper().getCountry().getName());
//
//    }

}
