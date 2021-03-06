package ua.com.virtonomica.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.RetailProduct;

import java.util.List;

@Repository
public interface RetailProductRepository extends JpaRepository<RetailProduct,Long> {

    RetailProduct findByName(String name);
    RetailProduct findBySymbol(String symbol);
    @Query("select r from RetailProduct r where r.id=?1")
    List<RetailProduct> findById(long id);
}
