package ua.com.virtonomica.service.product.retail_product.wrappers.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.product.RetailProductCategory;
import ua.com.virtonomica.repository.product.RetailProductCategoryRepository;

@Service
@Transactional
public class RetailProductCategoryTransaction implements RetailProductCategoryService{
    private final RetailProductCategoryRepository retailProductCategoryRepository;

    public RetailProductCategoryTransaction(RetailProductCategoryRepository retailProductCategoryRepository) {
        this.retailProductCategoryRepository = retailProductCategoryRepository;
    }

    @Override
    public RetailProductCategory findById(long id) {
        return retailProductCategoryRepository.findById(id).get();
    }

    @Override
    public void save(RetailProductCategory retailProductCategory) {
        retailProductCategoryRepository.save(retailProductCategory);
    }
}
