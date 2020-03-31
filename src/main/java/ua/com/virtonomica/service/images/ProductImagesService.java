package ua.com.virtonomica.service.images;

import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.service.BaseService;

public interface ProductImagesService extends BaseService<ProductImage> {
    ProductImage findByProductSymbol(String productSymbol);

}
