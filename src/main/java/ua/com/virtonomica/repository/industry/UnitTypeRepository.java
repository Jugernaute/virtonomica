package ua.com.virtonomica.repository.industry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.industrial.UnitType;

import java.util.List;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType,Long> {
    UnitType findByName(String name);

    List<UnitType> getUnitTypeByUnitClass_Name(String unitClassName);
}
