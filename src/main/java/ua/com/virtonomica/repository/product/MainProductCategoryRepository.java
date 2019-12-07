package ua.com.virtonomica.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.product.MainProductCategory;

@Repository
public interface MainProductCategoryRepository extends JpaRepository<MainProductCategory,Long> {
}
