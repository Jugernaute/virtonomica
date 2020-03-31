package ua.com.virtonomica.service.geography.country;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.repository.geography.CountryRepository;

@Repository
@Transactional
public class CountryTransaction implements CountryService{
    private final CountryRepository countryRepository;

    public CountryTransaction(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findById(long id) {
        return countryRepository.findById(id).get();
    }

    @Override
    public void save(Country country) {
        countryRepository.save(country);
    }

}
