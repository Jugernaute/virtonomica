package ua.com.virtonomica.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.company.Company;
import ua.com.virtonomica.repository.company.CompanyRepository;

@Service
@Transactional
public class CompanyTransaction implements CompanyService{

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyTransaction(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }



    @Override
    public void saveCompany (Company company) {
        if (company!=null)
        companyRepository.save(company);
    }

    public void deleteAll() {
        companyRepository.deleteAll();
    }

    @Override
    public void update(Company company) {
        try {
            getCompany();
        }catch (IndexOutOfBoundsException e){
            saveCompany(company);
        }
    }

    @Override
    public Company getCompany() {
        System.out.println(companyRepository.findAll());
        return companyRepository.findAll().get(0);
    }
}
