package ua.com.virtonomica.repository.geography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.geography.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
