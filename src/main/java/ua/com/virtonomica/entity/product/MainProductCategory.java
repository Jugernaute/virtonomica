package ua.com.virtonomica.entity.product;

import ua.com.virtonomica.entity.AbstractUnit;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "mpc_name"))
@AttributeOverride(name = "id",column = @Column(name = "mpc_id"))
public class MainProductCategory extends AbstractUnit {

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH,
            mappedBy = "mainProductCategory")
    private List<MainProduct> mainProducts;

    public MainProductCategory() {
    }

    public MainProductCategory(long id, String name) {
        super(id, name);
    }

    public List<MainProduct> getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(List<MainProduct> mainProducts) {
        this.mainProducts = mainProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainProductCategory)) return false;
        if (!super.equals(o)) return false;
        MainProductCategory that = (MainProductCategory) o;
        return Objects.equals(mainProducts, that.mainProducts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), mainProducts);
    }

    @Override
    public String toString() {
        return "MainProductCategory{" +
                super.getName()+
//                " ,mainProducts=" + mainProducts +
                '}';
    }
}
