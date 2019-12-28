package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingReport {
     private long unit_id;
     private String unit_name;
     private long company_id;
     private String company_name;
     private long country_id;
     private String country_name;
     private String country_symbol;
     private long region_id;
     private String region_name;
     private long city_id;
     private String city_name;
     private int shop_size;
     private String district_name;
     private long qty;
     private double price;
     private double quality;
     private double brand;

    public MarketingReport(long unit_id, String unit_name, long company_id, String company_name, long country_id, String country_name, String country_symbol, long region_id, String region_name, long city_id, String city_name, int shop_size, String district_name, long qty, double price, double quality, double brand) {
        this.unit_id = unit_id;
        this.unit_name = unit_name;
        this.company_id = company_id;
        this.company_name = company_name;
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_symbol = country_symbol;
        this.region_id = region_id;
        this.region_name = region_name;
        this.city_id = city_id;
        this.city_name = city_name;
        this.shop_size = shop_size;
        this.district_name = district_name;
        this.qty = qty;
        this.price = price;
        this.quality = quality;
        this.brand = brand;
    }

    public MarketingReport() {
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

    public long getCountry_id() {
        return country_id;
    }

    public void setCountry_id(long country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_symbol() {
        return country_symbol;
    }

    public void setCountry_symbol(String country_symbol) {
        this.country_symbol = country_symbol;
    }

    public long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(long region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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
        if (!(o instanceof MarketingReport)) return false;
        MarketingReport that = (MarketingReport) o;
        return unit_id == that.unit_id &&
                company_id == that.company_id &&
                country_id == that.country_id &&
                region_id == that.region_id &&
                city_id == that.city_id &&
                shop_size == that.shop_size &&
                qty == that.qty &&
                Double.compare(that.price, price) == 0 &&
                Double.compare(that.quality, quality) == 0 &&
                Double.compare(that.brand, brand) == 0 &&
                Objects.equals(unit_name, that.unit_name) &&
                Objects.equals(company_name, that.company_name) &&
                Objects.equals(country_name, that.country_name) &&
                Objects.equals(country_symbol, that.country_symbol) &&
                Objects.equals(region_name, that.region_name) &&
                Objects.equals(city_name, that.city_name) &&
                Objects.equals(district_name, that.district_name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(unit_id, unit_name, company_id, company_name, country_id, country_name, country_symbol, region_id, region_name, city_id, city_name, shop_size, district_name, qty, price, quality, brand);
    }

    @Override
    public String toString() {
        return "MarketingReport{" +
                "unit_id=" + unit_id +
                ", unit_name='" + unit_name + '\'' +
                ", company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", country_id=" + country_id +
                ", country_name='" + country_name + '\'' +
                ", country_symbol='" + country_symbol + '\'' +
                ", region_id=" + region_id +
                ", region_name='" + region_name + '\'' +
                ", city_id=" + city_id +
                ", city_name='" + city_name + '\'' +
                ", shop_size=" + shop_size +
                ", district_name='" + district_name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", quality=" + quality +
                ", brand=" + brand +
                '}';
    }
}
