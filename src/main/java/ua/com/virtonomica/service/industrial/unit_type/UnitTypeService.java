package ua.com.virtonomica.service.industrial.unit_type;

import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.service.BaseService;
import ua.com.virtonomica.web.factory_analytics.UnitClassEnum;

import java.util.List;

public interface UnitTypeService extends BaseService<UnitType> {
    long getIdByUnitType_Name(String unitTypeName);
    List<UnitType> getUnitTypeByUnitClassName(UnitClassEnum classEnum);

}
