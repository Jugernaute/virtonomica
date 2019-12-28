package ua.com.virtonomica.dto.shop_products;

import ua.com.virtonomica.entity.company.CompanyProducts;

import java.util.*;

public class ShopsProductsDto {
    private HashMap<ShopInfo,HashSet<CompanyProducts>> map =new HashMap<>();
    private CompanyProducts companyProducts;
    private ShopInfo shopInfo;

    public CompanyProducts getCompanyProducts() {
        return companyProducts;
    }

    public void setCompanyProducts(CompanyProducts companyProducts) {
        this.companyProducts = companyProducts;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }



    public ShopsProductsDto() {
    }

    public HashMap<ShopInfo, HashSet<CompanyProducts>> shopsAndProducts(HashSet<ShopsProducts>shopsProducts){
        for (ShopsProducts product : shopsProducts) {
            long productId = product.getCompany_product_id();
            String productName = product.getCompany_product_name();
            String productSymbol = product.getSymbol();
            long shopId = product.getCompany_unit_id();
            String shopName = product.getCompany_unit_name();
            String countryName = product.getCountry_name();
            String cityName = product.getCity_name();

            ShopInfo shopInfo = new ShopInfo(countryName, cityName, shopId, shopName);
            CompanyProducts companyProducts1 = new CompanyProducts(productId, productName,productSymbol);

            if (map.containsKey(shopInfo)) {
                HashSet<CompanyProducts> value = map.get(shopInfo);
                value.add(companyProducts1);
                map.put(shopInfo, value);
            } else {
                HashSet<CompanyProducts> companyProducts = new HashSet<>();
                companyProducts.add(companyProducts1);
                map.put(shopInfo, companyProducts);
            }
        }
       return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopsProductsDto)) return false;
        ShopsProductsDto that = (ShopsProductsDto) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(companyProducts, that.companyProducts) &&
                Objects.equals(shopInfo, that.shopInfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(map, companyProducts, shopInfo);
    }

}
