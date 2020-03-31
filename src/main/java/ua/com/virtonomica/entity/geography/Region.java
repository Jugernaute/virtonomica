package ua.com.virtonomica.entity.geography;

import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyUnits;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "region_name"))
@AttributeOverride(name = "id",column = @Column(name = "region_id"))
public class Region extends AbstractUnit {

    @ManyToOne(fetch = FetchType.LAZY,
    cascade = CascadeType.REFRESH)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    mappedBy = "region")
    private List<City> cities;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "region")
    private List<CompanyUnits> companyUnits;

    public Region() {
    }

    public Region(long id, String name) {
        super(id, name);
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        if (!super.equals(o)) return false;
        Region region = (Region) o;
        return Objects.equals(country, region.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), country);
    }

    @Override
    public String toString() {
        return "Region{" +
                super.toString()+
                '}';
    }

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }
}
