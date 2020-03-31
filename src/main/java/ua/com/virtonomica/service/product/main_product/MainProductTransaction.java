package ua.com.virtonomica.service.product.main_product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.repository.product.MainProductRepository;

import java.util.List;

@Service
@Transactional
public class MainProductTransaction implements MainProductService{
    private final MainProductRepository mainProductRepository;

    public MainProductTransaction(MainProductRepository mainProductRepository) {
        this.mainProductRepository = mainProductRepository;
    }

    @Override
    public List<MainProduct> findByCompositeId(long id) {
        return mainProductRepository.findMainProductById(id);
    }

    @Override
    public MainProduct findByName (String name) {
        return mainProductRepository.findByName(name);
    }

    @Override
    public boolean isExistByName(String name) {
        return mainProductRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public MainProduct findById(long id) {
        return null;
    }

    @Override
    public void save(MainProduct mainProduct) {
        mainProductRepository.save(mainProduct);
    }
}
