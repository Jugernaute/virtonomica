package ua.com.virtonomica.service.geography.city;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.repository.geography.CityRepository;

@Service
@Transactional
public class CityTransaction implements CityService {
    private final CityRepository cityRepository;

    public CityTransaction(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findById(long id) {
        return cityRepository.findById(id).get();
    }

    @Override
    public void save(City city) {
        cityRepository.save(city);
    }
}
