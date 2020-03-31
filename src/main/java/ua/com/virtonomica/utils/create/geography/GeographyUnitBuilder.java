package ua.com.virtonomica.utils.create.geography;

import org.springframework.stereotype.Component;
import ua.com.virtonomica.utils.create.UnitBuilder;
import ua.com.virtonomica.utils.create.IUnitWrapper;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;

@Component
public class GeographyUnitBuilder extends UnitBuilder {


    public GeographyUnitBuilder(UnitWrapperService unitWrapperService) {
        super(unitWrapperService);
    }

    @Override
    public void build(String json, IUnitWrapper IUnitWrapper) {
        super.build(json, new GeographyWrapper());
    }

}
