package ua.com.virtonomica.service;

public interface BaseService<T> {
    T findById(long id);
    void save(T t);

}
