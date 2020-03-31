package ua.com.virtonomica.service.company.company_units;

import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.service.BaseService;

import java.util.List;

public interface MyCompanyUnitsService extends BaseService<CompanyUnits> {
    List<CompanyUnits>findByProductAndUnitClass_Name (long idProduct, String product_name, String unitClass_name);
    List<CompanyUnits>findByProduct(String product);


}
