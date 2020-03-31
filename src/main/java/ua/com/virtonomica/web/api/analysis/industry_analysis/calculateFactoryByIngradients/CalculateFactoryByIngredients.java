package ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.decimal4j.util.DoubleRounder;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.search.TradeOffers;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients.RequiredDataForCalculateProduction.*;

public class CalculateFactoryByIngredients {
    private double ingrTotalCost = 0;
    private long productId;
    //    private double ingrTotalQuality;
    //    private double productionsByPerson;
    //    private String qualityModifier;
    private RequiredDataForCalculateProduction requiredDataForCalculateProduction = new RequiredDataForCalculateProduction();
    private List<ResultDataAfterCalculateFactory> resultDataAfterCalculateFactoryList = new ArrayList<>();
    private List<Integer> ingrCountList = new ArrayList<>();
    private List<Double> ingrQualityList = new ArrayList<>();
    private List<Double> ingrPriceList = new ArrayList<>();
    private List<String> ingrNameList = new ArrayList<>();
    private List<IngredientsOfSpecialization> ingredientsOfSpecializationList = new ArrayList<>();



    public CalculateFactoryByIngredients() {
    }

    public ResultDataAfterCalculateFactory result(UnitSpecialization unitSpecialization) {
        List<IngredientsOfSpecialization> ingredients = unitSpecialization.getIngredients();

        productId = Long.parseLong(unitSpecialization.getProductId());
        RequiredDataForCalculateProduction requiredDataForCalculateProduction = getRequiredData(unitSpecialization);
//        qualityModifier = requiredDataForCalculateProduction.getQlt_modif();
        ingrCountList = requiredDataForCalculateProduction.getTotalCountList();
//        System.out.println(unitSpecialization.getSpecialization());
//        System.out.println(ingrCountList);
//        System.out.println("=============");
        ingrNameList = requiredDataForCalculateProduction.getIngrNameList();
//        System.out.println("qm "+qualityModifier);
//        System.out.println("pbp "+productionsByPerson);
//        System.out.println("ingr count list: "+ingrCountList);
//        System.out.println("ingr name list: "+ingrNameList);

        List<TradeOffers> tradeOffers = getTradeOffers(ingredients);
        if (tradeOffers == null) return null;

          /*   init ingrQualityList and ingrPriceList  */
        getIngredientsValues(tradeOffers);

        if (ingrCountList.size()==ingrQualityList.size()){
//            ingrTotalQuality = getIngrTotalQuality();
            return getResultDataAfterCalculateFactory(requiredDataForCalculateProduction);
        }else {
            System.out.println(">>>>>>> start error:");
            System.out.println(unitSpecialization.getSpecialization());
            System.out.println("ingr_name_list "+ingrNameList);
            System.out.println("ingr_quality_list "+ingrQualityList);
            System.out.println("ingr_count_list "+ingrCountList);
            System.out.println("tcs "+ingrCountList.size()+" tqs "+ingrQualityList.size());
            System.out.println(">>>>>>> end error!!");
        }
        return null;
    }

    private double getIngrTotalQuality() {
        double quality = getIngrInitialQuality();
        double v = Math.pow(quality, 0.5) * Math.pow(tech, 0.65);
        if (v > Math.pow(tech, 1.3)) {
            v = Math.pow(tech, 1.3);
        }
        if ( v < 1 ) {
            v = 1;
        }
//        System.out.println("ing tot qualt "+v);
        return v;
    }

    private double getIngrInitialQuality() {
        double[] t = new double[ingrQualityList.size()];

        for (int i = 0; i < ingrCountList.size(); i++) {
            Integer ingrCount = ingrCountList.get(i);
            Double ingrQuality = ingrQualityList.get(i);
            t[i] = ingrCount * ingrQuality;
            Double ingrPrice = ingrPriceList.get(i);
            ingrTotalCost += ingrCount * ingrPrice;
            String ingrName = ingrNameList.get(i);
            ingredientsOfSpecializationList.add(new IngredientsOfSpecialization(ingrName, String.valueOf(ingrCount), String.valueOf(ingrQuality),ingrPrice));

        }

        int sum = Arrays.stream(t)
                .mapToInt(i -> (int) i).sum();
        double total = ingrCountList.stream()
                .mapToDouble(i -> i).sum();
//        System.out.println(ingrQualityList);
//        System.out.println(ingrCountList);
//        System.out.println(ingrPriceList);
//        System.out.println(sum/total);
        return sum/total;
    }


    private void getIngredientsValues(List<TradeOffers> offersList) {
        for (TradeOffers tradeOffers : offersList) {
//            System.out.println(":::::");
//            System.out.println(tradeOffers);
//            System.out.println(tradeOffers.getProduct_name());
//            System.out.println(tradeOffers.getQuality());
//            System.out.println(tradeOffers.getPrice());
            double qlt = tradeOffers.getQuality();
            ingrQualityList.add(qlt);
            double price = tradeOffers.getPrice();
            ingrPriceList.add(price);
        }
    }

    private ResultDataAfterCalculateFactory getResultDataAfterCalculateFactory(RequiredDataForCalculateProduction data) {
        RequiredDataForCalculateProduction requiredDataForCalculateProduction = new RequiredDataForCalculateProduction();
        double ingrTotalQuality = getIngrTotalQuality();
        String qualityModifier = data.getQlt_modif();
        double productionsByPerson = data.getProductionsByPerson();

        int factorySalaryCosts = work_salary * work_count;
        double sumAllIngredients = work_count * productionsByPerson * Math.pow(1.05, tech-1);

        double productionCosts = ingrTotalCost + factorySalaryCosts + factorySalaryCosts * 1.1 ; //exps

        int productCount = (int) Math.floor(sumAllIngredients);
//        System.out.println("price2 "+ factoryCosts+ " "+ sumAllIngredients);
        double price = productionCosts / sumAllIngredients;
//        System.out.println(price);
       double quality = DoubleRounder.round(ingrTotalQuality, 2);
       price = DoubleRounder.round(price, 2);
//        System.out.println("initialQ "+ quality);

       if (qualityModifier.length()>2){
          double v = Double.parseDouble(qualityModifier);
             quality = quality + quality * v;
       }
//        System.out.println("---------------------------------------------------------");
//        for (IngredientsOfSpecialization ingr : ingredientsOfSpecializationList) {
//            System.out.println("ingredientsOfSpecializationList >" +ingr.getMaterial()+" " + ingr.getCount() + " "+ingr.getPrice());
//
//        }
//        System.out.println("productId >" +productId);
//        System.out.println("price >" +price);
//        System.out.println("count >" +productCount);
//        System.out.println("quality >" +quality);
        //        System.out.println(resultDataForFactory.toString());
        return new ResultDataAfterCalculateFactory(ingredientsOfSpecializationList, productId, price, productCount, quality);
    }

    private List<TradeOffers> getTradeOffers(List<IngredientsOfSpecialization> ingredients){
//        System.out.println("tradeoffers block: start");
//        System.out.println(ingredients);

        Set<TradeOffers>tradeOffersSet = new LinkedHashSet<>();
        List<TradeOffers>tradeOffers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Set<List<TradeOffers>> offers = objectMapper.readValue(new File("product_trade_offers.json"), new TypeReference<Set<List<TradeOffers>>>() {});
            for (IngredientsOfSpecialization ingredient : ingredients) {
                String material  = ingredient.getMaterial().replaceAll("\"","");
                String quality = ingredient.getQuality();
                double countOfIngredients = requiredDataForCalculateProduction.formulaGetIngrTotalCount(ingredient);
//                System.out.println("need "+ countOfIngredients);
                if (Double.parseDouble(quality)>1){
                    return null;
                }
                for (List<TradeOffers> offer : offers) {
                    List<TradeOffers> collect = offer.stream()
                            .filter(t -> t.getProduct_name().equalsIgnoreCase(material))
                            .filter(t -> t.getFree_for_buy() >= countOfIngredients)
                            .collect(Collectors.toList());
                    if (!collect.isEmpty()){
                        tradeOffers=collect;
                    }
                }
                Optional<TradeOffers> min = tradeOffers.stream()
                                .min(Comparator.comparing(TradeOffers::getPrice));
//                min.ifPresent(t -> System.out.println("is present " + t));
                min.ifPresent(tradeOffersSet::add);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("start set");
//        tradeOffersSet.forEach(System.out::println);
//        System.out.println("end set");
//        System.out.println("tradeoffers block: end");

        return new ArrayList<>(tradeOffersSet);
    }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CalculateFactoryByIngredients that = (CalculateFactoryByIngredients) o;
      return Double.compare(that.ingrTotalCost, ingrTotalCost) == 0 &&
//              Double.compare(that.ingrTotalQuality, ingrTotalQuality) == 0 &&
//              Double.compare(that.productionsByPerson, productionsByPerson) == 0 &&
              productId == that.productId &&
//              Objects.equals(qualityModifier, that.qualityModifier) &&
              Objects.equals(requiredDataForCalculateProduction, that.requiredDataForCalculateProduction) &&
              Objects.equals(resultDataAfterCalculateFactoryList, that.resultDataAfterCalculateFactoryList) &&
              Objects.equals(ingrCountList, that.ingrCountList) &&
              Objects.equals(ingrQualityList, that.ingrQualityList) &&
              Objects.equals(ingrPriceList, that.ingrPriceList) &&
              Objects.equals(ingrNameList, that.ingrNameList) &&
              Objects.equals(ingredientsOfSpecializationList, that.ingredientsOfSpecializationList);
   }

   @Override
   public int hashCode() {
      return Objects.hash(ingrTotalCost, productId, requiredDataForCalculateProduction, resultDataAfterCalculateFactoryList, ingrCountList, ingrQualityList, ingrPriceList, ingrNameList, ingredientsOfSpecializationList);
   }

//   public String getQlt_modifier() {
//        return qualityModifier;
//    }

//    public void setQlt_modifier(String qualityModifier) {
//        this.qualityModifier = qualityModifier;
//    }

    public RequiredDataForCalculateProduction getRequiredData(UnitSpecialization unitSpecialization) {
        return requiredDataForCalculateProduction.requiredData(unitSpecialization);
    }

    public List<Integer> getTotalCount() {
        return ingrCountList;
    }
}
