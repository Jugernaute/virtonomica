package ua.com.virtonomica.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.RetailProduct;

@Repository
public interface RetailProductRepository extends JpaRepository<RetailProduct,Long> {
    RetailProduct findByName(String name);

    RetailProduct findBySymbol(String symbol);
}
