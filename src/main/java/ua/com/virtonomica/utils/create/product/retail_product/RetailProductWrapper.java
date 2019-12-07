package ua.com.virtonomica.utils.create.product.retail_product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.deserializer.RetailProductDeserializer;
import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.entity.product.RetailProductCategory;
import ua.com.virtonomica.utils.create.IUnitWrapper;

@JsonDeserialize(using = RetailProductDeserializer.class)
public class RetailProductWrapper implements IUnitWrapper {
    private RetailProduct retailProduct;
    private RetailProductCategory retailProductCategory;

    public RetailProductWrapper() {
    }

    public RetailProductWrapper(long product_id, String product_name, long category_id, String category_name, String symbol) {
        this.retailProduct = new RetailProduct(product_id, product_name,symbol);
        this.retailProductCategory = new RetailProductCategory(category_id,category_name);
    }

    public RetailProduct getRetailProduct() {
        return retailProduct;
    }

    public void setRetailProduct(RetailProduct retailProduct) {
        this.retailProduct = retailProduct;
    }

    public RetailProductCategory getRetailProductCategory() {
        return retailProductCategory;
    }

    public void setRetailProductCategory(RetailProductCategory retailProductCategory) {
        this.retailProductCategory = retailProductCategory;
    }
}
