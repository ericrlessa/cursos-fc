package com.fullcycle.admin.catalogo.domain.exceptions;

import java.util.List;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.validation.Error;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }
    
    public static NotFoundException with(
        final Class<? extends AggregateRoot<?>> anAgreggate,
        final Identifier id
    ){ 
        final var anError = "%s with ID %s was not found".formatted(anAgreggate.getSimpleName(), id.getValue());
        return new NotFoundException(anError, List.of(new Error(anError)));
    }
}
