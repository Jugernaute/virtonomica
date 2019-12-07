package ua.com.virtonomica.repository.industry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.industrial.UnitClass;

@Repository
public interface UnitClassRepository extends JpaRepository<UnitClass,Long> {
    UnitClass getByName(String name);
}
