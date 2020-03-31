package ua.com.virtonomica.dto.shop_products;

import org.springframework.beans.factory.annotation.Value;

public interface ShopsProducts {
//        @Value("#{target.company_product_id+' '+" +
//                "target.company_product_name+' '+" +
//                "target.Company_unit_id+' '+" +
//                "target.Company_unit_name}")
//        String getFullCompany();
        long getCompany_product_id();
        String getCompany_product_name();
        String getSymbol();
        long getCompany_unit_id();
        String getCompany_unit_name();
        String getCountry_name();
        String getCity_name();
}
