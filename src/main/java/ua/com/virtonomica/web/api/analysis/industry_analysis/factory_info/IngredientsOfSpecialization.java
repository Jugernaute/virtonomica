package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class IngredientsOfSpecialization {
    private String material, count, quality;
    private double price;

    public IngredientsOfSpecialization() {
    }

    public IngredientsOfSpecialization(String material, String count, String quality, double price) {
        this.material = material;
        this.count = count;
        this.quality = quality;
        this.price = price;
    }

    public IngredientsOfSpecialization(String material, String count, String quality) {
        this.material = material;
        this.count = count;
        this.quality = quality;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientsOfSpecialization that = (IngredientsOfSpecialization) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(material, that.material) &&
                Objects.equals(count, that.count) &&
                Objects.equals(quality, that.quality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, count, quality, price);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "IngredientsOfSpecialization{" +
                "material='" + material + '\'' +
                ", count='" + count + '\'' +
                ", quality='" + quality + '\'' +
                ", price=" + price +
                '}';
    }
}
