package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.deserializer.GeographyDeserializer;
import ua.com.virtonomica.deserializer.MarketingReportDeserializer;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
//крупнейшие продавцы (магазины)
@JsonDeserialize(using = MarketingReportDeserializer.class)
public class MarketingReport {
    private GeographyWrapper geographyWrapper;
    private long unit_id;
    private String unit_name;
    private long company_id;
    private String company_name;
    private int shop_size;
    private String district_name;
    private long qty;
    private double price;
    private double quality;
    private double brand;

    public MarketingReport(GeographyWrapper geographyWrapper, long unit_id, String unit_name, long company_id, String company_name, int shop_size, String district_name, long qty, double price, double quality, double brand) {
        this.geographyWrapper = geographyWrapper;
        this.unit_id = unit_id;
        this.unit_name = unit_name;
        this.company_id = company_id;
        this.company_name = company_name;
        this.shop_size = shop_size;
        this.district_name = district_name;
        this.qty = qty;
        this.price = price;
        this.quality = quality;
        this.brand = brand;
    }

    public MarketingReport() {
    }

    public GeographyWrapper getGeographyWrapper() {
        return geographyWrapper;
    }

    public void setGeographyWrapper(GeographyWrapper geographyWrapper) {
        this.geographyWrapper = geographyWrapper;
    }

    public long getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(long unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getShop_size() {
        return shop_size;
    }

    public void setShop_size(int shop_size) {
        this.shop_size = shop_size;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getBrand() {
        return brand;
    }

    public void setBrand(double brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketingReport that = (MarketingReport) o;
        return unit_id == that.unit_id &&
                company_id == that.company_id &&
                shop_size == that.shop_size &&
                qty == that.qty &&
                Double.compare(that.price, price) == 0 &&
                Double.compare(that.quality, quality) == 0 &&
                Double.compare(that.brand, brand) == 0 &&
                Objects.equals(geographyWrapper, that.geographyWrapper) &&
                Objects.equals(unit_name, that.unit_name) &&
                Objects.equals(company_name, that.company_name) &&
                Objects.equals(district_name, that.district_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geographyWrapper, unit_id, unit_name, company_id, company_name, shop_size, district_name, qty, price, quality, brand);
    }

    @Override
    public String toString() {
        return "MarketingReport{" +
                " geographyWrapper{ "+ geographyWrapper +
                "} ,unit_id=" + unit_id +
                ", unit_name='" + unit_name + '\'' +
                ", company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", shop_size=" + shop_size +
                ", district_name='" + district_name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", quality=" + quality +
                ", brand=" + brand +
                '}';
    }
}
