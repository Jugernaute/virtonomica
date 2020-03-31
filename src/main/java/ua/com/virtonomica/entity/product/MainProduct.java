package ua.com.virtonomica.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.utils.create.IUnitWrapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "mp_name"))
@AttributeOverride(name = "id",column = @Column(name = "mp_id"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainProduct extends AbstractUnit implements IUnitWrapper {

    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY,
    cascade = CascadeType.MERGE)
    private MainProductCategory mainProductCategory;

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private ProductImage productImage;

    public MainProduct(long id, String name, String symbol) {
        super(id, name);
        this.symbol = symbol;
    }

    public MainProduct() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public MainProduct(MainProductCategory mainProductCategory) {
        this.mainProductCategory = mainProductCategory;
    }

    public MainProductCategory getMainProductCategory() {
        return mainProductCategory;
    }

    public void setMainProductCategory(MainProductCategory mainProductCategory) {
        this.mainProductCategory = mainProductCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainProduct)) return false;
        if (!super.equals(o)) return false;
        MainProduct product = (MainProduct) o;
        return Objects.equals(mainProductCategory, product.mainProductCategory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), mainProductCategory);
    }

    @Override
    public String toString() {
        return "MainProduct{ " +
                super.getName()+
                '}';
    }
}
