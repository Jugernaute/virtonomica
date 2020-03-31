package ua.com.virtonomica.entity.geography;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyUnits;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "country_name"))
@AttributeOverride(name = "id",column = @Column(name = "country_id"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country extends AbstractUnit {
    private String country_symbol;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH,
            mappedBy = "country")
    private List<Region> regions;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "country")
    private List<CompanyUnits> companyUnits;


    public Country() {
    }

    public Country(long id, String country_name) {
        super(id, country_name);
    }

    public String getCountry_symbol() {
        return country_symbol;
    }

    public void setCountry_symbol(String country_symbol) {
        this.country_symbol = country_symbol;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
    public List<Region> getRegions() {
        return regions;
    }

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }


    @Override
    public String toString() {
        return "Country{" +
                "country_id=" + super.getId()+
                ", country_name=" + super.getName()+
                ", country_symbol=" + country_symbol+
                '}';
    }
}


