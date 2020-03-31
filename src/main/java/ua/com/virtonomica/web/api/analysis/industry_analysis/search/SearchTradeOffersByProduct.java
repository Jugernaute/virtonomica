package ua.com.virtonomica.web.api.analysis.industry_analysis.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.service.RequiredMinValuesForProductToCreateFactoryService;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.service.product.main_product.MainProductService;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.FactoryInfo_For_Offer;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;

import java.util.*;

@Component
public class SearchTradeOffersByProduct {
    private final MainProductService mainProductService;
    private final MyCompanyUnitsService myCompanyUnitsService;
    private final VirtonomicaAPI virtonomicaAPI;
    private Set<TradeOffers> set = new HashSet<>();
    private List<TradeOffers> offers = new ArrayList<>();

    private HashMap<String,List<TradeOffers>>tradeOffersMap = new HashMap<>();

    @Autowired
    public SearchTradeOffersByProduct(MainProductService mainProductService, RequiredMinValuesForProductToCreateFactoryService requiredMinValuesForProductService, MyCompanyUnitsService myCompanyUnitsService, VirtonomicaAPI virtonomicaAPI) {
        this.mainProductService = mainProductService;
        this.myCompanyUnitsService = myCompanyUnitsService;
        this.virtonomicaAPI = virtonomicaAPI;
    }

    public List<TradeOffers> search (List<IngredientsOfSpecialization>materials/*, long endProductId, String specialization*/){
        //        LinkedHashMap<Long, List<MarketingReport>> reportLargestStores = virtonomicaAPI.getReportLargestStores(endProductId);
//        System.out.println("largest stores by product:"+ specialization);
//        reportLargestStores.forEach((k,v)-> System.out.println(k+": "+v));
        return getTradeOffers(materials);

    }
    public List<TradeOffers> getTradeOffers(IngredientsOfSpecialization ingredient) {
            String product = ingredient.getMaterial();
            findOffersAndAddToList(product);
        return offers;
    }

    private List<TradeOffers> getTradeOffers(List<IngredientsOfSpecialization> materials) {
        for (IngredientsOfSpecialization material : materials) {
            String product = material.getMaterial();
            findOffersAndAddToList(product);
        }
        return offers;
    }

    public List<TradeOffers> getTradeOffers(HashSet<String> products) {
        for (String product : products) {
            findOffersAndAddToList(product);
        }
        return offers;
    }

    public List<TradeOffers> getTradeOffers(String product, int minCount) {
        set = new HashSet<>();
        findOffersAndAddToList(product,minCount);
        return new ArrayList<>(set);
    }




    private void findOffersAndAddToList(String product) {
        long productId = mainProductService.findByName(product).getId();
        TradeOffers tradeOffers = virtonomicaAPI.getTradeOffersByProductId(product, productId);
        System.out.println("==============================================");
        List<CompanyUnits> byProduct = myCompanyUnitsService.findByProduct(product);
        if (byProduct!=null){
            for (CompanyUnits companyUnits : byProduct) {
                long companyId = companyUnits.getId();
                FactoryInfo_For_Offer factoryInfoForOffer = virtonomicaAPI.getMyFactoryInfoForOffer(companyId,productId);

            }
        }
        if (tradeOffers!=null){
            System.out.println("-------------------------");
            System.out.println(tradeOffers);
            System.out.println(tradeOffers.getProduct_name()+" " + tradeOffers.getQuality()+" "+tradeOffers.getPrice());
            offers.add(tradeOffers);
        }
    }








    private void findOffersAndAddToList(String product, int minCount) {
        long productId = mainProductService.findByName(product).getId();
        System.out.println(product);
        List<TradeOffers> listTradeOffers = virtonomicaAPI.getListTradeOffers(productId);

        TradeOffers tradeOffers = virtonomicaAPI.getTradeOffersByProductId(product, listTradeOffers, minCount);
        System.out.println("==============================================");
        List<CompanyUnits> byProduct = myCompanyUnitsService.findByProduct(product);
        if (byProduct!=null){
            for (CompanyUnits companyUnits : byProduct) {
                long companyId = companyUnits.getId();
                FactoryInfo_For_Offer factoryInfoForOffer = virtonomicaAPI.getMyFactoryInfoForOffer(companyId,productId);

            }
        }
        if (tradeOffers!=null){
            System.out.println("-------------------------");
            System.out.println(tradeOffers);
            System.out.println(tradeOffers.getProduct_name()+" " + tradeOffers.getQuality()+" "+tradeOffers.getPrice());
            set.add(tradeOffers);
        }
    }

}
