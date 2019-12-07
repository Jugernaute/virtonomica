package ua.com.virtonomica.entity;


import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractUnit {
    @Id
    private long id;
    private String name;

    public AbstractUnit(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractUnit() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUnit)) return false;
        AbstractUnit that = (AbstractUnit) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AbstractUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
