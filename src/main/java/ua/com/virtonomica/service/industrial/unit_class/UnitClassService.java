package ua.com.virtonomica.service.industrial.unit_class;

import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.service.BaseService;

public interface UnitClassService extends BaseService<UnitClass> {
    long getIdByUnitClass_Name(String unitClassName);
}
