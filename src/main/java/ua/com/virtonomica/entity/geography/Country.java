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

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH,
            mappedBy = "country")
    private List<Region> regions;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "country")
    private List<CompanyUnits> companyUnits;


    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }
    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }
    //    private int city_count;


    public List<Region> getRegions() {
        return regions;
    }


    public Country(long id, String country_name/*, int city_count*/) {
        super(id, country_name);
    }

    public Country() {
    }

    @Override
    public String toString() {
        return "Country{" +
                "country_id=" + super.getId()+
                ", country_name=" + super.getName()+
                '}';
    }
}


