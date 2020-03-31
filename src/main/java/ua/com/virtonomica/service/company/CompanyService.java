package ua.com.virtonomica.service.company;

import ua.com.virtonomica.entity.company.Company;
import ua.com.virtonomica.service.BaseService;

public interface CompanyService{
    void saveCompany(Company company);
    void update (Company company);
    Company getCompany ();
}
