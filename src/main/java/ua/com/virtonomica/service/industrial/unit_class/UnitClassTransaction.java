package ua.com.virtonomica.service.industrial.unit_class;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.repository.industry.UnitClassRepository;

@Service
@Transactional
public class UnitClassTransaction implements UnitClassService{
    private final UnitClassRepository unitClassRepository;

    public UnitClassTransaction(UnitClassRepository unitClassRepository) {
        this.unitClassRepository = unitClassRepository;
    }

    @Override
    public UnitClass findById(long id) {
        return unitClassRepository.findById(id).get();
    }

    @Override
    public void save(UnitClass unitClass) {
        unitClassRepository.save(unitClass);
    }

    @Override
    public long getIdByUnitClass_Name(String unitClassName) {
        return unitClassRepository.getByName(unitClassName).getId();
    }
}
