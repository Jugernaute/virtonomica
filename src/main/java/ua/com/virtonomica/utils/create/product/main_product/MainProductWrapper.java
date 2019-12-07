package ua.com.virtonomica.utils.create.product.main_product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.deserializer.MainProductDeserializer;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.MainProductCategory;
import ua.com.virtonomica.utils.create.IUnitWrapper;

@JsonDeserialize(using = MainProductDeserializer.class)
public class MainProductWrapper implements IUnitWrapper {
    private MainProduct mainProduct;
    private MainProductCategory mainProductCategory;

    public MainProductWrapper() {
    }

    public MainProductWrapper(long product_id, String product_name, long category_id, String category_name, String symbol) {
        this.mainProduct = new MainProduct(product_id, product_name,symbol);
        this.mainProductCategory = new MainProductCategory(category_id,category_name);
    }

    public MainProduct getMainProduct() {
        return mainProduct;
    }

    public void setMainProduct(MainProduct mainProduct) {
        this.mainProduct = mainProduct;
    }

    public MainProductCategory getMainProductCategory() {
        return mainProductCategory;
    }

    public void setMainProductCategory(MainProductCategory mainProductCategory) {
        this.mainProductCategory = mainProductCategory;
    }
}
