package ua.com.virtonomica.service.geography.region;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.repository.geography.RegionRepository;

@Service
@Transactional
public class RegionTransaction implements RegionService{
    private final RegionRepository regionRepository;

    public RegionTransaction(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region findById(long id) {
        return regionRepository.findById(id).get();
    }

    @Override
    public void save(Region region) {
        regionRepository.save(region);
    }
}
