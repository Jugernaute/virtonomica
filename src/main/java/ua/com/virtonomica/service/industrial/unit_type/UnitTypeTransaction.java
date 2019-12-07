package ua.com.virtonomica.service.industrial.unit_type;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.repository.industry.UnitTypeRepository;
import ua.com.virtonomica.web.factory_analytics.UnitClassEnum;

import java.util.List;

@Service
@Transactional
public class UnitTypeTransaction implements UnitTypeService{
    private final UnitTypeRepository unitTypeRepository;

    public UnitTypeTransaction(UnitTypeRepository unitTypeRepository) {
        this.unitTypeRepository = unitTypeRepository;
    }

    @Override
    public UnitType findById(long id) {
        return unitTypeRepository.findById(id).get();
    }

    @Override
    public void save(UnitType unitType) {
        unitTypeRepository.save(unitType);
    }

    @Override
    public long getIdByUnitType_Name(String unitTypeName) {
        return unitTypeRepository.findByName(unitTypeName).getId();
    }

    @Override
    public List<UnitType> getUnitTypeByUnitClassName(UnitClassEnum unitClassEnum) {
        return unitTypeRepository.getUnitTypeByUnitClass_Name(unitClassEnum.name());
    }
}
