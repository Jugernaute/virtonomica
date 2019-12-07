package ua.com.virtonomica.service.product.retail_product.wrappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.entity.product.RetailProductCategory;
import ua.com.virtonomica.repository.AbstractUnitRepository;
import ua.com.virtonomica.utils.create.product.retail_product.RetailProductWrapper;

@Service
@Transactional
public class RetailProductWrapperTransaction implements RetailProductWrapperService {
    private final AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository;

    @Autowired
    public RetailProductWrapperTransaction(AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository) {
        this.abstractUnitRepository = abstractUnitRepository;
    }

    @Override
    public void save(RetailProductWrapper retailProductWrapper) {
        RetailProduct retailProduct = retailProductWrapper.getRetailProduct();
        RetailProductCategory retailProductCategory = retailProductWrapper.getRetailProductCategory();
        abstractUnitRepository.save(retailProductCategory);
        retailProduct.setRetailProductCategory(retailProductCategory);
        abstractUnitRepository.save(retailProduct);
    }
}
