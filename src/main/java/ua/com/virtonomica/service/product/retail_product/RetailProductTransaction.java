package ua.com.virtonomica.service.product.retail_product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.repository.product.RetailProductRepository;

import java.util.List;

@Service
@Transactional
public class RetailProductTransaction implements RetailProductService {
    private final RetailProductRepository retailProductRepository;

    public RetailProductTransaction(RetailProductRepository retailProductRepository) {
        this.retailProductRepository = retailProductRepository;
    }

    @Override
    public RetailProduct findByName(String name) {
        return retailProductRepository.findByName(name);
    }

    @Override
    public RetailProduct findBySymbol(String symbol) {
        return retailProductRepository.findBySymbol(symbol);
    }

    @Override
    public List<RetailProduct> findByIdKey(long id) {
        String s = String.valueOf(id);
        return retailProductRepository.findById(id);
    }

    @Override
    public RetailProduct findById(long id) {
//        String s = String.valueOf(id);
        return retailProductRepository.findById(id).get(0);
    }

    @Override
    public void save(RetailProduct retailProduct) {
        retailProductRepository.save(retailProduct);
    }
}
