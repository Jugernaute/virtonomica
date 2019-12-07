package ua.com.virtonomica.entity.company;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AttributeOverride(name = "id", column = @Column(name = "company_id"))
public class Company extends AbstractUnit {
    @Column(name = "president")
    private String president_first_name;
    @Column(name = "rating_revenue")
    private long rating_revenue_position;
    private long alliance_id;
    private String alliance_name;

    public Company() {
    }


    public Company(long id, String name, String president_first_name, long rating_revenue_position, long alliance_id, String alliance_name) {
        super(id,name);
        this.president_first_name = president_first_name;
        this.rating_revenue_position = rating_revenue_position;
        this.alliance_id = alliance_id;
        this.alliance_name = alliance_name;
    }

    public String getPresident_first_name() {
        return president_first_name;
    }

    public void setPresident_first_name(String president_first_name) {
        this.president_first_name = president_first_name;
    }

    public long getRating_revenue_position() {
        return rating_revenue_position;
    }

    public void setRating_revenue_position(long rating_revenue_position) {
        this.rating_revenue_position = rating_revenue_position;
    }

    public long getAlliance_id() {
        return alliance_id;
    }

    public void setAlliance_id(long alliance_id) {
        this.alliance_id = alliance_id;
    }

    public String getAlliance_name() {
        return alliance_name;
    }

    public void setAlliance_name(String alliance_name) {
        this.alliance_name = alliance_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return rating_revenue_position == company.rating_revenue_position &&
                alliance_id == company.alliance_id &&
                Objects.equals(president_first_name, company.president_first_name) &&
                Objects.equals(alliance_name, company.alliance_name);/*&&*/
//                Objects.equals(dateOfUpdate, company.dateOfUpdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), president_first_name, rating_revenue_position, alliance_id, alliance_name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id="+getId()+
                ", name="+getName()+
                ", president_first_name='" + president_first_name + '\'' +
                ", rating_revenue_position=" + rating_revenue_position +
                ", alliance_id=" + alliance_id +
                ", alliance_name='" + alliance_name + '\'' +
                '}';
    }
}
