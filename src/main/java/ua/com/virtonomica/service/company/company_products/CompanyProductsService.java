package ua.com.virtonomica.service.company.company_products;

import ua.com.virtonomica.dto.shop_products.ShopsProducts;
import ua.com.virtonomica.entity.company.CompanyProducts;

import java.util.HashSet;
import java.util.List;

public interface CompanyProductsService {
    HashSet<ShopsProducts> findProductsRealizedByUnitClass(long unitClassId);

    CompanyProducts findBySymbol(String symbol);
}
