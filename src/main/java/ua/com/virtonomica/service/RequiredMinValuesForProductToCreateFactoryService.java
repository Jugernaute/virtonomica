package ua.com.virtonomica.service;

import ua.com.virtonomica.entity.RequiredMinValuesForProductToCalculateFactory;


public interface RequiredMinValuesForProductToCreateFactoryService {
    void save(RequiredMinValuesForProductToCalculateFactory values);
    RequiredMinValuesForProductToCalculateFactory findByName(String product);
    boolean existsByName(String product);
}
