package ua.com.virtonomica.service.product.retail_product;

import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.service.BaseService;

public interface RetailProductService extends BaseService<RetailProduct> {
    RetailProduct findByName(String name);
    RetailProduct findBySymbol(String symbol);
}
