package ua.com.virtonomica.service.product.main_product;

import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.service.BaseService;

import java.util.List;

public interface MainProductService extends BaseService<MainProduct> {
    List<MainProduct> findByCompositeId(long id);
    MainProduct findByName (String name);
}
