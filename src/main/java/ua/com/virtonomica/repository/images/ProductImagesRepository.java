package ua.com.virtonomica.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.images.ProductImage;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage,Long> {

    ProductImage getBySymbol(String productSymbol);
}
