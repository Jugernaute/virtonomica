package ua.com.virtonomica.utils.create.industrial;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.deserializer.IndustrialDeserializer;
import ua.com.virtonomica.entity.industrial.Industry;
import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.utils.create.IUnitWrapper;

@JsonDeserialize(using = IndustrialDeserializer.class)
public class IndustrialWrapper implements IUnitWrapper {
    private Industry industry;
    private UnitClass unitClass;
    private UnitType unitType;

    public IndustrialWrapper() {
    }

    public IndustrialWrapper(long unitType_id, long industry_id, long class_id, String unitType_name, String industry_name, String class_name) {
        this.industry = new Industry(industry_id, industry_name);
        this.unitClass = new UnitClass(class_id, class_name);
        this.unitType = new UnitType(unitType_id, unitType_name);
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public UnitClass getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(UnitClass unitClass) {
        this.unitClass = unitClass;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "IndustrialWrapper{" +
                "industry=" + industry +
                ", unitClass=" + unitClass +
                ", unitType=" + unitType +
                '}';
    }
}
