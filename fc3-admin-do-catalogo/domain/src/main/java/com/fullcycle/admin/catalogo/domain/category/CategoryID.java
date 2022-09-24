package com.fullcycle.admin.catalogo.domain.category;

import java.util.Objects;
import java.util.UUID;

import com.fullcycle.admin.catalogo.domain.Identifier;

public class CategoryID extends Identifier{
    
    private final String value;

    private CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique(){
        return from(UUID.randomUUID());
    }

    public static CategoryID from(final String anId){
        return new CategoryID(anId);
    }

    public static CategoryID from(final UUID anId){
        return new CategoryID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CategoryID)) {
            return false;
        }
        CategoryID categoryID = (CategoryID) o;
        return Objects.equals(value, categoryID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }



}
