package ua.com.virtonomica.entity.company;

import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.UnitId;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "company_product_id"))
@AttributeOverride(name = "name", column = @Column(name = "company_product_name"))
@IdClass(UnitId.class)
public class CompanyProducts extends AbstractUnit {
    @Id
    private String name;
    private String symbol;

    @ManyToMany(cascade = CascadeType.REFRESH,
    fetch = FetchType.EAGER)
    private List<CompanyUnits> companyUnits;

    public CompanyProducts(long id, String name) {
        super(id, name);
        this.name=name;
    }

    public CompanyProducts(long id, String name, String symbol) {
        super(id, name);
        this.name=name;
        this.symbol = symbol;
    }

    public CompanyProducts() {
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<CompanyUnits> getCompanyUnits() {
        return companyUnits;
    }

    public void setCompanyUnits(List<CompanyUnits> companyUnits) {
        this.companyUnits = companyUnits;
    }

    public List<CompanyUnits> getUnitsList() {
        return companyUnits;
    }

    public void setUnitsList(List<CompanyUnits> unitsList) {
        this.companyUnits = unitsList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyProducts)) return false;
        if (!super.equals(o)) return false;
        CompanyProducts that = (CompanyProducts) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(companyUnits, that.companyUnits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, symbol, companyUnits);
    }

    @Override
    public String toString() {
        return "CompanyProducts{" +
                "id= " + super.getId()+
                " name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
