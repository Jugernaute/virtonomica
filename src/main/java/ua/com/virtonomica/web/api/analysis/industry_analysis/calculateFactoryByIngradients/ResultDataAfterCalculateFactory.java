package ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients;

import ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info.IngredientsOfSpecialization;

import java.util.List;

public class ResultDataAfterCalculateFactory {
    private List<IngredientsOfSpecialization> ingredientsOfSpecializationList;
    private long productId;
    private double productCostPrice;
    private int productCount;
    private double productQuality;


    ResultDataAfterCalculateFactory(List<IngredientsOfSpecialization> ingredientsOfSpecializationList, long productId, double productCostPrice, int productCount, double productQuality) {
        this.ingredientsOfSpecializationList = ingredientsOfSpecializationList;
        this.productId = productId;
        this.productCostPrice = productCostPrice;
        this.productCount = productCount;
        this.productQuality = productQuality;
    }

    public ResultDataAfterCalculateFactory() {
    }

    public double getProductCostPrice() {
        return productCostPrice;
    }

    public void setProductCostPrice(int productCostPrice) {
        this.productCostPrice = productCostPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(double productQuality) {
        this.productQuality = productQuality;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public List<IngredientsOfSpecialization> getIngredientsOfSpecializationList() {
        return ingredientsOfSpecializationList;
    }

    private void setIngredientsOfSpecializationList(List<IngredientsOfSpecialization> ingredientsOfSpecializationList) {
        this.ingredientsOfSpecializationList = ingredientsOfSpecializationList;
    }

    @Override
    public String toString() {
        return "ResultDataAfterCalculateFactory{" +
                "ingredientsOfSpecializationList=" + ingredientsOfSpecializationList +
                ", productId=" + productId +
                ", productCostPrice=" + productCostPrice +
                ", productCount=" + productCount +
                ", productQuality=" + productQuality +
                '}';
    }
}
