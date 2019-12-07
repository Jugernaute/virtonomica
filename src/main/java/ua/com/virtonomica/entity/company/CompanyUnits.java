package ua.com.virtonomica.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.utils.create.my_company_units.MyCompanyUnitsWrapper;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.entity.industrial.UnitType;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AttributeOverride(name = "id", column = @Column(name = "company_unit_id"))
@AttributeOverride(name = "name", column = @Column(name = "company_unit_name"))
public class CompanyUnits extends AbstractUnit {

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.REFRESH)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    private UnitType unitType;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH)
    private UnitClass unitClass;

//    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL/*,
    mappedBy = "unitsList"*/)
    private List<CompanyProducts> products;


    public CompanyUnits(MyCompanyUnitsWrapper units) {

        super(units.getId(), units.getName());
        this.country = units.getCountry();
        this.region = units.getRegion();
        this.city = units.getCity();
        setCountry(country);
        setRegion(region);
        setCity(city);
        this.unitClass = units.getUnitClass();
        setUnitClass(unitClass);
        this.unitType = units.getUnitType();
        setUnitType(unitType);
        this.products = units.getProducts();
        setProducts(products);
    }

    public CompanyUnits(long id, String name) {
        super(id, name);
    }

    public CompanyUnits() {
    }

    public Country getCountry() {
        return country;
    }

    private void setCountry(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    private void setRegion(Region region) {
        this.region = region;
    }

    public City getCity() {
        return city;
    }

    private void setCity(City city) {
        this.city = city;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    private void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public UnitClass getUnitClass() {
        return unitClass;
    }

    private void setUnitClass(UnitClass unitClass) {
        this.unitClass = unitClass;
    }

    public List<CompanyProducts> getProducts() {
        return products;
    }

    public void setProducts(List<CompanyProducts> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "CompanyUnits{" +
                super.toString()+
                "country=" + country.getName() +
                ", region=" + region.getName() +
                ", city=" + city.getName() +
                ", unitType=" + unitType.getName() +
                ", unitClass=" + unitClass.getName() +
                ", products=" + products +
                '}';
    }
}
