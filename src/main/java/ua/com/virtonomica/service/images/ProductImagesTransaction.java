package ua.com.virtonomica.service.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.repository.images.ProductImagesRepository;

@Service
@Transactional
public class ProductImagesTransaction implements ProductImagesService {
    private final ProductImagesRepository repository;

    @Autowired
    public ProductImagesTransaction(ProductImagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductImage findById(long id) {
        return repository.getOne(id);
    }

    @Override
    public void save(ProductImage productImage) {
        repository.save(productImage);
    }

    @Override
    public ProductImage findByProductSymbol(String productSymbol) {
        return repository.getBySymbol(productSymbol);
    }
}
