package ua.com.virtonomica.service.product.main_product.wrappers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.MainProductCategory;
import ua.com.virtonomica.repository.AbstractUnitRepository;
import ua.com.virtonomica.utils.create.product.main_product.MainProductWrapper;

@Service
@Transactional
public class MainProductWrapperTransaction implements MainProductWrapperService {
    private final AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository;

    public MainProductWrapperTransaction(AbstractUnitRepository<? super AbstractUnit> abstractUnitRepository) {
        this.abstractUnitRepository = abstractUnitRepository;
    }

    @Override
    public void save(MainProductWrapper mainProductWrapper) {
        MainProduct mainProduct = mainProductWrapper.getMainProduct();
        MainProductCategory mainProductCategory = mainProductWrapper.getMainProductCategory();
        abstractUnitRepository.save(mainProductCategory);
        mainProduct.setMainProductCategory(mainProductCategory);
        abstractUnitRepository.save(mainProduct);
    }
}
