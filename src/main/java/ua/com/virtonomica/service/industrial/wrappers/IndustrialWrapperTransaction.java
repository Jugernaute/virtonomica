package ua.com.virtonomica.service.industrial.wrappers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.industrial.Industry;
import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.service.abstractUnit.AbstractUnitService;
import ua.com.virtonomica.utils.create.industrial.IndustrialWrapper;

@Service
@Transactional
public class IndustrialWrapperTransaction implements IndustrialWrapperService {
    private final AbstractUnitService<? super AbstractUnit> abstractUnitService;

    public IndustrialWrapperTransaction(AbstractUnitService<? super AbstractUnit> abstractUnitService) {
        this.abstractUnitService = abstractUnitService;
    }

    @Override
    public void save(IndustrialWrapper industrialWrapper) {
        Industry industry = industrialWrapper.getIndustry();
        UnitClass unitClass = industrialWrapper.getUnitClass();
        UnitType unitType = industrialWrapper.getUnitType();
        abstractUnitService.save(industry);
        abstractUnitService.save(unitClass);
        unitType.setIndustry(industry);
        unitType.setUnitClass(unitClass);
        abstractUnitService.save(unitType);
    }
}
