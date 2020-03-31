package ua.com.virtonomica.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RequiredMinValuesForProductToCalculateFactory {
    @Id
    private String name;
    private double minQuality;
    private double maxPrice;

    public RequiredMinValuesForProductToCalculateFactory() {
    }

    public RequiredMinValuesForProductToCalculateFactory(String name, double minQuality, double maxPrice) {

        this.name = name;
        this.minQuality = minQuality;
        this.maxPrice = maxPrice;
    }


    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinQuality() {
        return minQuality;
    }

    public void setMinQuality(double minQuality) {
        this.minQuality = minQuality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequiredMinValuesForProductToCalculateFactory)) return false;
        RequiredMinValuesForProductToCalculateFactory that = (RequiredMinValuesForProductToCalculateFactory) o;
        return Double.compare(that.minQuality, minQuality) == 0 &&
                Double.compare(that.maxPrice, maxPrice) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, minQuality, maxPrice);
    }

    @Override
    public String toString() {
        return "RequiredMinValuesForProductToCalculateFactory{" +
                "name='" + name + '\'' +
                ", minQuality=" + minQuality +
                ", maxPrice=" + maxPrice +
                '}';
    }
}