package ua.com.virtonomica.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ShopsProducts {
//        @Value("#{target.company_product_id+' '+" +
//                "target.company_product_name+' '+" +
//                "target.Company_unit_id+' '+" +
//                "target.Company_unit_name}")
        String getFullCompany();
        long getCompany_product_id();
        String getCompany_product_name();
        long getCompany_unit_id();
        String getCompany_unit_name();
}
