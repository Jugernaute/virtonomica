package ua.com.virtonomica.service.industrial.industry;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.industrial.Industry;
import ua.com.virtonomica.repository.industry.IndustryRepository;

@Service
@Transactional
public class IndustryTransaction implements IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryTransaction(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public Industry findById(long id) {
        return industryRepository.findById(id).get();
    }

    @Override
    public void save(Industry industry) {
        industryRepository.save(industry);
    }
}
