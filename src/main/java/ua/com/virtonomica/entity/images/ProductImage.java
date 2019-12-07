package ua.com.virtonomica.entity.images;

import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.RetailProduct;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AttributeOverride(name = "name",column = @Column(name = "product_name"))
@AttributeOverride(name = "id",column = @Column(name = "image_id"))
@IdClass(value = UnitId.class)
public class ProductImage extends AbstractUnit {

    @Id
    private String name;
    private String linkImage;
    private String symbol;

    @OneToMany(cascade = CascadeType.REFRESH,
    fetch = FetchType.LAZY,
    mappedBy = "productImage")
    private List<MainProduct> mainProduct;

    @OneToMany(cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "productImage")
    private List<RetailProduct> retailProducts;

    public ProductImage(long id, String name, String symbol, String linkImage) {
        super(id, name);
        this.name = name;
        this.linkImage = linkImage;
        this.symbol = symbol;
    }

    public ProductImage() {

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<RetailProduct> getRetailProducts() {
        return retailProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImage)) return false;
        if (!super.equals(o)) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(linkImage, that.linkImage) &&
                Objects.equals(mainProduct, that.mainProduct) &&
                Objects.equals(retailProducts, that.retailProducts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), linkImage, mainProduct, retailProducts);
    }

    public void setRetailProducts(List<RetailProduct> retailProducts) {
        this.retailProducts = retailProducts;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public List<MainProduct> getMainProduct() {
        return mainProduct;
    }

    public void setMainProduct(List<MainProduct> mainProduct) {
        this.mainProduct = mainProduct;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                super.toString()+
                " mainProduct=" + mainProduct +
                " link=" + linkImage+
                '}';
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
