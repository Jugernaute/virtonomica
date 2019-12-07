package ua.com.virtonomica.utils.create.product.retail_product;

import org.springframework.stereotype.Service;
import ua.com.virtonomica.utils.create.UnitBuilder;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;

@Service
public class RetailProductUnitBuilder extends UnitBuilder {
    public RetailProductUnitBuilder(UnitWrapperService unitWrapperService) {
        super(unitWrapperService);
    }
}
