package ua.com.virtonomica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.AbstractUnit;

@Repository
public interface AbstractUnitRepository<T extends AbstractUnit> extends JpaRepository<T,Long> {
}
