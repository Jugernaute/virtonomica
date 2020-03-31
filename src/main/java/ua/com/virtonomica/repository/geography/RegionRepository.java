package ua.com.virtonomica.repository.geography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.geography.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
}
