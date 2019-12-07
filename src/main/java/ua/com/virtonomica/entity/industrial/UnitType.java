package ua.com.virtonomica.entity.industrial;

import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyUnits;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "ut_name"))
@AttributeOverride(name = "id",column = @Column(name = "ut_id"))
public class UnitType extends AbstractUnit {

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.REFRESH)
    private Industry industry;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    private UnitClass unitClass;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "unitType")
    private List<CompanyUnits> companyUnits;


    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }

    public UnitClass getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(UnitClass unitClass) {
        this.unitClass = unitClass;
    }

    public UnitType(long id, String name) {
        super(id, name);
    }

    public UnitType() {
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "UnitType{"+
    super.getName()+
    "}";
    }
}
