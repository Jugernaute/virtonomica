package ua.com.virtonomica.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.RequiredMinValuesForProductToCalculateFactory;
import ua.com.virtonomica.repository.RequiredMinValuesForProductToCreateFactoryRepository;

@Service
@Transactional
public class RequiredMinValuesForProductToCreateFactoryTransaction implements RequiredMinValuesForProductToCreateFactoryService {
    private final RequiredMinValuesForProductToCreateFactoryRepository repository;

    public RequiredMinValuesForProductToCreateFactoryTransaction(RequiredMinValuesForProductToCreateFactoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(RequiredMinValuesForProductToCalculateFactory values) {
        repository.save(values);
    }

    @Override
    public RequiredMinValuesForProductToCalculateFactory findByName(String product) {
        return repository.findByName(product);
    }

    @Override
    public boolean existsByName(String product) {
        return repository.existsByName(product);
    }
}
