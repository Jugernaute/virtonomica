package ua.com.virtonomica.dto;

import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopsProductsDto {
    private HashMap<CompanyUnits,List<CompanyProducts>> map =new HashMap<>();

    public ShopsProductsDto() {
    }

    public HashMap<CompanyUnits, List<CompanyProducts>> shopsAndProducts(List<ShopsProducts>shopsProducts){
        for (ShopsProducts product : shopsProducts) {
            long productId = product.getCompany_product_id();
            String productName = product.getCompany_product_name();
            long unitId = product.getCompany_unit_id();
            String unitName = product.getCompany_unit_name();

            CompanyUnits companyUnits = new CompanyUnits(unitId, unitName);
            CompanyProducts companyProducts1 = new CompanyProducts(productId, productName);
            if (map.containsKey(companyUnits)) {
                List<CompanyProducts> value = map.get(companyUnits);
                value.add(companyProducts1);
                map.put(companyUnits, value);
            } else {
                List<CompanyProducts> companyProducts = new ArrayList<>();
                companyProducts.add(companyProducts1);
                map.put(companyUnits, companyProducts);
            }
        }
       return map;
    }
}
