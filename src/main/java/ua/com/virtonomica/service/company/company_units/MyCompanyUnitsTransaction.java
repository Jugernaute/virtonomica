package ua.com.virtonomica.service.company.company_units;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.repository.AbstractUnitRepository;
import ua.com.virtonomica.repository.company.CompanyUnitsRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class MyCompanyUnitsTransaction implements MyCompanyUnitsService {
    private final AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository;
    private final CompanyUnitsRepository companyUnitsRepository;

    public MyCompanyUnitsTransaction(AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository, CompanyUnitsRepository companyUnitsRepository) {
        this.abstractUnitRepository = abstractUnitRepository;
        this.companyUnitsRepository = companyUnitsRepository;
    }

    @Override
    public void save(CompanyUnits units) {
        List<CompanyProducts> products = units.getProducts();
        if (!units.getUnitClass().getName().equalsIgnoreCase("лаборатория")){
            for (CompanyProducts product : products) {
                abstractUnitRepository.save(product);
            }
//            System.out.println(units);
            abstractUnitRepository.save(units);
        }
    }

    @Override
    public CompanyUnits findById(long id) {
        return companyUnitsRepository.getOne(id);
    }

    @Override
    public List<CompanyUnits> findByProductAndUnitClass_Name(long idProduct, String product_name, String unitClass_name) {
        CompanyProducts product = new CompanyProducts(idProduct, product_name);
        List<CompanyProducts> products = new ArrayList<>();
        products.add(product);
        return companyUnitsRepository.findByProductsContainsAndUnitClass_Name(products,unitClass_name);
    }
}
