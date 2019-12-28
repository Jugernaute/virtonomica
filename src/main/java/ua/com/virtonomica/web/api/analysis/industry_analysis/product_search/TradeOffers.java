package ua.com.virtonomica.web.api.analysis.industry_analysis.product_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOffers {
    private String product_name;
    private String product_symbol;
    private String country_id;
    private String country_name;
    private String city_id;
    private long quantity;
    private long free_for_buy;
    private double quality;
    private double brand_value_at_customer_city;
    private double price;

    public TradeOffers(String product_name, String product_symbol, String country_id, String country_name, String city_id, long quantity, long free_for_buy, double quality, double brand_value_at_customer_city, double price) {
        this.product_name = product_name;
        this.product_symbol = product_symbol;
        this.country_id = country_id;
        this.country_name = country_name;
        this.city_id = city_id;
        this.quantity = quantity;
        this.free_for_buy = free_for_buy;
        this.quality = quality;
        this.brand_value_at_customer_city = brand_value_at_customer_city;
        this.price = price;
    }

    public TradeOffers() {
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_symbol() {
        return product_symbol;
    }

    public void setProduct_symbol(String product_symbol) {
        this.product_symbol = product_symbol;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getFree_for_buy() {
        return free_for_buy;
    }

    public void setFree_for_buy(long free_for_buy) {
        this.free_for_buy = free_for_buy;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getBrand_value_at_customer_city() {
        return brand_value_at_customer_city;
    }

    public void setBrand_value_at_customer_city(double brand_value_at_customer_city) {
        this.brand_value_at_customer_city = brand_value_at_customer_city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeOffers)) return false;
        TradeOffers that = (TradeOffers) o;
        return quantity == that.quantity &&
                free_for_buy == that.free_for_buy &&
                Double.compare(that.quality, quality) == 0 &&
                Double.compare(that.brand_value_at_customer_city, brand_value_at_customer_city) == 0 &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(product_name, that.product_name) &&
                Objects.equals(product_symbol, that.product_symbol) &&
                Objects.equals(country_id, that.country_id) &&
                Objects.equals(country_name, that.country_name) &&
                Objects.equals(city_id, that.city_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(product_name, product_symbol, country_id, country_name, city_id, quantity, free_for_buy, quality, brand_value_at_customer_city, price);
    }

    @Override
    public String toString() {
        return "TradeOffers{" +
                "product_name='" + product_name + '\'' +
                ", product_symbol='" + product_symbol + '\'' +
                ", country_id='" + country_id + '\'' +
                ", country_name='" + country_name + '\'' +
                ", city_id='" + city_id + '\'' +
                ", quantity=" + quantity +
                ", free_for_buy=" + free_for_buy +
                ", quality=" + quality +
                ", brand_value_at_customer_city=" + brand_value_at_customer_city +
                ", price=" + price +
                '}';
    }
}
