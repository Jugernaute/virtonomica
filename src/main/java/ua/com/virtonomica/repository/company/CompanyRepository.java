package ua.com.virtonomica.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.virtonomica.entity.company.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

}
