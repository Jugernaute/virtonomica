package ua.com.virtonomica.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.dto.ShopsProducts;
import ua.com.virtonomica.entity.UnitId;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.product.RetailProduct;

import java.util.List;

@Repository
public interface CompanyProductsRepository extends JpaRepository<CompanyProducts,UnitId> {

    @Query(value = "select cp.*, cu.company_unit_name from company_products cp\n" +
            "  join company_units_products cup\n" +
            "    on cp.company_product_id=cup.products_company_product_id\n" +
            "  join company_units cu\n" +
            "    on cu.company_unit_id=cup.company_units_company_unit_id\n" +
            "where cu.unit_class_uc_id=:unit_class",nativeQuery = true)
    List<CompanyProducts> findProductsRealizedByUnitClass(@Param("unit_class")long unitClassId);

//    @Query(value = "select cp.company_product_id, cp.company_product_name, cu.company_unit_id, cu.company_unit_name from company_products cp join company_units_products cup on\n" +
//            "  cp.company_product_id=cup.products_company_product_id join company_units cu on cu.company_unit_id=cup.company_units_company_unit_id\n" +
//            "  where cu.unit_type_ut_id=1886",nativeQuery = true)
//    List<ShopsProducts> test();

    CompanyProducts findBySymbol(String symbol);
}
