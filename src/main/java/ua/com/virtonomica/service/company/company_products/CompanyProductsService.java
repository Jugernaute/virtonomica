package ua.com.virtonomica.service.company.company_products;

import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.product.RetailProduct;

import java.util.List;

public interface CompanyProductsService {
    List<CompanyProducts>findProductsRealizedByUnitClass(long unitClassId);

    CompanyProducts findBySymbol(String symbol);
}
