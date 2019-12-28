package ua.com.virtonomica.dto.shop_products;

import java.util.Objects;

public class ShopInfo {
    private String countryName;
    private String cityName;
    private long shopId;
    private String shopName;

    ShopInfo(String countryName, String cityName, long shopId, String shopName) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopInfo)) return false;
        ShopInfo shopInfo = (ShopInfo) o;
        return shopId == shopInfo.shopId &&
                Objects.equals(countryName, shopInfo.countryName) &&
                Objects.equals(cityName, shopInfo.cityName) &&
                Objects.equals(shopName, shopInfo.shopName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(countryName, cityName, shopId, shopName);
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}
