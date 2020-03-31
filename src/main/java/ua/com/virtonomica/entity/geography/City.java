package ua.com.virtonomica.entity.geography;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyUnits;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "city_name"))
@AttributeOverride(name = "id",column = @Column(name = "city_id"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class City extends AbstractUnit {
    private double citySalary;
    private double education;

    @ManyToOne(fetch = FetchType.EAGER,
        cascade = CascadeType.DETACH)
        private Region region;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            mappedBy = "city")
    private List<CompanyUnits> companyUnits;

    public City() {
    }

    public City(long id, String name) {
        super(id, name);
    }

    public City(long id, String name, double citySalary, double education) {
        super(id, name);
        this.citySalary = citySalary;
        this.education = education;
    }

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }


    public double getEducation() {
        return education;
    }

    public void setEducation(double education) {
        this.education = education;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public double getCitySalary() {
        return citySalary;
    }

    public void setCitySalary(double citySalary) {
        this.citySalary = citySalary;
    }

    @Override
    public String toString() {
        return "City{" +
                "city_id=" + super.getId() +
                ", city_name=" + super.getName() +
                '}';
    }
}
