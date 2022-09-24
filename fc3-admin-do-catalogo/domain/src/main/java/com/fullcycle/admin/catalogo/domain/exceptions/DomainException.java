package com.fullcycle.admin.catalogo.domain.exceptions;

import java.util.List;
import com.fullcycle.admin.catalogo.domain.validation.Error;
public class DomainException extends NoStacktraceException{

    protected final List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    private DomainException(final List<Error> anErrors) {
        super("");
        this.errors = anErrors;
    }

    public static DomainException with(final List<Error> anErrors){
        return new DomainException("", anErrors);
    }

    public static DomainException with(final Error anError){
        return new DomainException(anError.message(), List.of(anError));
    }

    public List<Error> getErrors() {
        return this.errors;
    }
}
