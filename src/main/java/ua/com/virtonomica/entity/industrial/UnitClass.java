package ua.com.virtonomica.entity.industrial;

import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyUnits;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "uc_name"))
@AttributeOverride(name = "id",column = @Column(name = "uc_id"))
public class UnitClass extends AbstractUnit {

    @OneToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.REFRESH,
    mappedBy = "unitClass")
    private List<UnitType> unitTypes;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "unitClass")
    private List<CompanyUnits> companyUnits;

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }

    public List<UnitType> getUnitTypes() {
        return unitTypes;
    }

    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public UnitClass(long id, String name) {
        super(id, name);
    }

    public UnitClass() {
    }


    @Override
    public String toString() {
        return "UnitClass{"
                + super.getName() +
                "}";
    }
}
