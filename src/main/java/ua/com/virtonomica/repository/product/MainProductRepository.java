package ua.com.virtonomica.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.product.MainProduct;

import java.util.List;

@Repository
public interface MainProductRepository extends JpaRepository<MainProduct,UnitId> {

    List<MainProduct> findMainProductById(long id);
//    @Query(value = "select p.id from MainProduct p where p.name=:name")
//    long getIdByName(@Param("name") String name);
    MainProduct findByName(String name);
}
