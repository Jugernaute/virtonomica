package ua.com.virtonomica.utils.create.product.main_product;

import org.springframework.stereotype.Component;
import ua.com.virtonomica.utils.create.IUnitWrapper;
import ua.com.virtonomica.utils.create.UnitBuilder;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;

@Component
public class MainProductUnitBuilder extends UnitBuilder {
    public MainProductUnitBuilder(UnitWrapperService unitWrapperService) {
        super(unitWrapperService);
    }

    @Override
    public void build(String json, IUnitWrapper IUnitWrapper) {
        super.build(json, IUnitWrapper);
    }
}
