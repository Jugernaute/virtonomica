package ua.com.virtonomica.service.abstractUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.repository.AbstractUnitRepository;

@Service
@Transactional
public class AbstractUnitTransaction<T extends AbstractUnit> implements AbstractUnitService<T>{
    private final AbstractUnitRepository<T> repository;

    @Autowired
    public AbstractUnitTransaction(AbstractUnitRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public void save(T t) {
        repository.save(t);
    }

    @Override
    public T findById(long id) {
        System.out.println(repository.findById(id));
        System.out.println(repository.getOne(id));
        return repository.findById(id).get();
    }
}

