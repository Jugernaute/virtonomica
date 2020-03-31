package ua.com.virtonomica.repository.industry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.industrial.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry,Long> {
    Industry findByName(String name);
}
