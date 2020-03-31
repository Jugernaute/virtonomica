package ua.com.virtonomica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.RequiredMinValuesForProductToCalculateFactory;

@Repository
public interface RequiredMinValuesForProductToCreateFactoryRepository extends JpaRepository<RequiredMinValuesForProductToCalculateFactory, String> {
    boolean existsByName(String product);
    RequiredMinValuesForProductToCalculateFactory findByName(String product);
}
