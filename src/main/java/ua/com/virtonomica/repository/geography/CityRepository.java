package ua.com.virtonomica.repository.geography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.geography.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
}
