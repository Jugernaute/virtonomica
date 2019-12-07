package ua.com.virtonomica.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.RetailProductCategory;

@Repository
public interface RetailProductCategoryRepository extends JpaRepository<RetailProductCategory,Long> {
}
