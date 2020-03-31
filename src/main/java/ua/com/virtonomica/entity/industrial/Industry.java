package ua.com.virtonomica.entity.industrial;


import ua.com.virtonomica.entity.AbstractUnit;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "industry_name"))
@AttributeOverride(name = "id",column = @Column(name = "industry_id"))
public class Industry extends AbstractUnit {

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH,
            mappedBy = "industry"
    )
    private List<UnitType> unitType;

    public List<UnitType> getUnitType() {
        return unitType;
    }

    public void setUnitType(List<UnitType> unitType) {
        this.unitType = unitType;
    }

    public Industry(long id, String name) {
        super(id, name);
    }

    public Industry() {
    }

    @Override
    public String toString() {
        return "Industry{"
    +super.getName()+
    "}";
    }
}
