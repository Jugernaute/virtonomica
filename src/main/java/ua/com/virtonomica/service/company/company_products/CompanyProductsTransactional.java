package ua.com.virtonomica.service.company.company_products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.dto.shop_products.ShopsProducts;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.repository.company.CompanyProductsRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CompanyProductsTransactional implements CompanyProductsService {
    private final CompanyProductsRepository companyProductsRepository;

    public CompanyProductsTransactional(CompanyProductsRepository companyProductsRepository) {
        this.companyProductsRepository = companyProductsRepository;
    }

    @Override
    public HashSet<ShopsProducts> findProductsRealizedByUnitClass(long unitClassId) {
        return companyProductsRepository.findProductsRealizedByUnitClass(unitClassId);
    }

    @Override
    public CompanyProducts findBySymbol(String symbol) {
        return companyProductsRepository.findBySymbol(symbol);
    }
}
