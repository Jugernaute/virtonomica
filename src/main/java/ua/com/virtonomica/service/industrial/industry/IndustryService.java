package ua.com.virtonomica.service.industrial.industry;

import ua.com.virtonomica.entity.industrial.Industry;
import ua.com.virtonomica.service.BaseService;

public interface IndustryService extends BaseService<Industry> {
    Industry findByName(String name);
}
