package ua.com.virtonomica.entity;

import java.io.Serializable;
import java.util.Objects;

public class UnitId implements Serializable {
    private long id;
    private String name;

    public UnitId() {
    }

    public UnitId(long id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitId)) return false;
        UnitId unitId = (UnitId) o;
        return id == unitId.id &&
                Objects.equals(name, unitId.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
