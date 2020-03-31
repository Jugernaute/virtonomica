package ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients;

import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;
import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.UnitSpecialization;

import java.util.ArrayList;
import java.util.List;

public class RequiredDataForCalculateProduction {
    public final static int tech = 23; //must do dynamic
    public final static int work_count = 5000;
    public final static int work_salary = 300;
    private double productionsByPerson = 0;
    private List<String> ingrNameList = new ArrayList<>();
    private double ingrRequiredQuality;
    private String qlt_modif;
    private List<Integer> totalCountList = new ArrayList<>();

    public RequiredDataForCalculateProduction(double productionsByPerson, double ingrRequiredCount, List<String> ingrNameList, double ingrRequiredQuality, List<Integer> totalCountList, String qlt_modif) {
        this.productionsByPerson = productionsByPerson;
        this.ingrNameList = ingrNameList;
        this.qlt_modif = qlt_modif;
        this.ingrRequiredQuality = ingrRequiredQuality;
        this.totalCountList = totalCountList;
    }

    public RequiredDataForCalculateProduction() {
    }

    RequiredDataForCalculateProduction requiredData(UnitSpecialization unitSpecialization){
        double ingrRequiredCount = 0;
        productionsByPerson = unitSpecialization.getProductionsByPerson();
        qlt_modif = unitSpecialization.getQlt_modif();
        List<IngredientsOfSpecialization> ingredients = unitSpecialization.getIngredients();
        for (IngredientsOfSpecialization ingredient : ingredients) {
            ingrRequiredQuality = Double.parseDouble(ingredient.getQuality());
            ingrRequiredCount = calcCountOfIngredients(ingredient);
            ingrNameList.add(ingredient.getMaterial());
//            System.out.println(ingredient.getMaterial());
            totalCountList.add(formulaGetIngrTotalCount(ingredient));
        }
        return new RequiredDataForCalculateProduction(productionsByPerson,ingrRequiredCount, ingrNameList, ingrRequiredQuality,totalCountList,qlt_modif);
    }

    private double calcCountOfIngredients(IngredientsOfSpecialization ingredient){
        double ingrRequiredCount;
        String count = ingredient.getCount().replaceAll(" ","");
//            System.out.println("count: " + count);
        if (count.contains("/")){
            String[] split = count.split("/");
            double c1 = Double.parseDouble(split[0]);
            double c2 = Double.parseDouble(split[1]);
            ingrRequiredCount = c1 / c2;
        } else ingrRequiredCount=Integer.parseInt(count);
        return ingrRequiredCount;
    }
    public int getIngrTotalCount(IngredientsOfSpecialization ingredient, double productionsByPerson){
        double ingrRequiredCount = calcCountOfIngredients(ingredient);
        ingrRequiredQuality = Double.parseDouble(ingredient.getQuality());

        return (int) (ingrRequiredCount / ingrRequiredQuality * productionsByPerson * work_count * Math.pow(1.05, tech -1 ));

    }


    public int formulaGetIngrTotalCount(IngredientsOfSpecialization ingredient) {
        double ingrRequiredCount = calcCountOfIngredients(ingredient);
        ingrRequiredQuality = Double.parseDouble(ingredient.getQuality());

        return (int) (ingrRequiredCount / ingrRequiredQuality * productionsByPerson * work_count * Math.pow(1.05, tech -1 ));
    }

    public double getRequiredQuantity() {
        return ingrRequiredQuality;
    }

    public List<Integer> getTotalCountList() {
        return totalCountList;
    }

    public double getProductionsByPerson() {
        return productionsByPerson;
    }

    public String getQlt_modif() {
        return qlt_modif;
    }

    public List<String> getIngrNameList() {
        return ingrNameList;
    }

    public void setIngrName(List<String> ingrNameList) {
        this.ingrNameList = ingrNameList;
    }

    public double getIngrRequiredQuality() {
        return ingrRequiredQuality;
    }
}
