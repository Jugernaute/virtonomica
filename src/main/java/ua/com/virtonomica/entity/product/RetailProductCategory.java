package ua.com.virtonomica.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "rpc_name"))
@AttributeOverride(name = "id",column = @Column(name = "rpc_id"))
public class RetailProductCategory extends AbstractUnit {

    @OneToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.DETACH,
    mappedBy = "retailProductCategory")
    private List<RetailProduct> retailProducts;

    public RetailProductCategory() {
    }

    public RetailProductCategory(long id, String name) {
        super(id, name);
    }

    public List<RetailProduct> getRetailProducts() {
        return retailProducts;
    }

    public void setRetailProducts(List<RetailProduct> retailProducts) {
        this.retailProducts = retailProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RetailProductCategory)) return false;
        if (!super.equals(o)) return false;
        RetailProductCategory that = (RetailProductCategory) o;
        return Objects.equals(retailProducts, that.retailProducts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), retailProducts);
    }

    @Override
    public String toString() {
        return "RetailProductCategory{" +
                super.getName()+
                '}';
    }
}
