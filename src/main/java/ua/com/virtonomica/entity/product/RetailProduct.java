package ua.com.virtonomica.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.utils.create.IUnitWrapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "rp_name"))
@AttributeOverride(name = "id",column = @Column(name = "rp_id"))
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(value = UnitId.class)
public class RetailProduct extends AbstractUnit implements IUnitWrapper {

    @Id
    private String symbol;

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.REFRESH)
    private RetailProductCategory retailProductCategory;

    @ManyToOne(fetch = FetchType.EAGER,
    cascade = CascadeType.REFRESH)
    private ProductImage productImage;

    public RetailProduct(long id, String name,String symbol) {
        super(id, name);
        this.symbol=symbol;
    }

    public RetailProduct() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public RetailProductCategory getRetailProductCategory() {
        return retailProductCategory;
    }

    public void setRetailProductCategory(RetailProductCategory retailProductCategory) {
        this.retailProductCategory = retailProductCategory;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof RetailProduct)) return false;
        if (!super.equals(o)) return false;
        RetailProduct that = (RetailProduct) o;
        return Objects.equals(symbol, that.symbol) &&
                Objects.equals(retailProductCategory, that.retailProductCategory) &&
                Objects.equals(productImage, that.productImage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), retailProductCategory, productImage);
    }


    @Override
    public String toString() {
        return "RetailProduct{" +
                super.toString()+
                " symbol='" + symbol + '\'' +
                '}';
    }
}
