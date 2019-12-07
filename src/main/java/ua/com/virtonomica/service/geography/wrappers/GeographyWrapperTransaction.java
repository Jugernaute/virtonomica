package ua.com.virtonomica.service.geography.wrappers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.service.abstractUnit.AbstractUnitService;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;

@Component
@Transactional
public class GeographyWrapperTransaction implements GeographyWrapperService {
    private final AbstractUnitService<? super AbstractUnit> abstractUnitService;

    public GeographyWrapperTransaction(AbstractUnitService<? super AbstractUnit> abstractUnitService) {
        this.abstractUnitService = abstractUnitService;
    }

    @Override
    public void save(GeographyWrapper geographyWrapper) {
        Country country = geographyWrapper.getCountry();
        Region region = geographyWrapper.getRegion();
        City city = geographyWrapper.getCity();

        abstractUnitService.save(country);
        region.setCountry(country);
        abstractUnitService.save(region);
        city.setRegion(region);
        abstractUnitService.save(city);
    }
}
