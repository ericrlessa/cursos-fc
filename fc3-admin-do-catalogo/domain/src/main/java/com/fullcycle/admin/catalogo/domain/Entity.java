package com.fullcycle.admin.catalogo.domain;

import java.util.Objects;

import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

public abstract class Entity<ID extends Identifier> {
 
    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
