package ua.com.virtonomica.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.company.CompanyUnits;

import java.util.List;

@Repository
public interface CompanyUnitsRepository extends JpaRepository<CompanyUnits,Long> {

    List<CompanyUnits> findByUnitType_Name(String name);

    List<CompanyUnits>findByProductsContainsAndUnitClass_Name (List<CompanyProducts> s, String name);
}
