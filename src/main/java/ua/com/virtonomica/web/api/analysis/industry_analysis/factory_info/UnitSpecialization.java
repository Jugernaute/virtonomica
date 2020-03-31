package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import java.util.*;

public class UnitSpecialization {
    private String specialization;
    private String qlt_modif;
    private String productId;
    private double productionsByPerson;
    private List<IngredientsOfSpecialization>ingredients;

    public UnitSpecialization(String specialization, String productId, String qlt_modif, double productionsByPerson, List<IngredientsOfSpecialization> ingredients) {
        this.specialization = specialization;
        this.qlt_modif = qlt_modif;
        this.productId=productId;
        this.productionsByPerson = productionsByPerson;
        this.ingredients = ingredients;
    }

    public UnitSpecialization() {
    }

    public String getSpecialization() {
        return specialization;

    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<IngredientsOfSpecialization> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsOfSpecialization> ingredients) {
        this.ingredients = ingredients;
    }

    public String getQlt_modif() {
        return qlt_modif;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQlt_modif(String qlt_modif) {
        this.qlt_modif = qlt_modif;
    }

    public double getProductionsByPerson() {
        return productionsByPerson;
    }

    public void setProductionsByPerson(double productionsByPerson) {
        this.productionsByPerson = productionsByPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitSpecialization)) return false;
        UnitSpecialization that = (UnitSpecialization) o;
        return Double.compare(that.productionsByPerson, productionsByPerson) == 0 &&
                Objects.equals(specialization, that.specialization) &&
                Objects.equals(qlt_modif, that.qlt_modif) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {

        return Objects.hash(specialization, qlt_modif, productId, productionsByPerson, ingredients);
    }

    @Override
    public String toString() {
        return "UnitSpecialization{" +
                "specialization='" + specialization + '\'' +
                ", qlt_modif='" + qlt_modif + '\'' +
                ", productId='" + productId + '\'' +
                ", productionsByPerson=" + productionsByPerson +
                ", ingredients=" + ingredients +
                '}';
    }
}
