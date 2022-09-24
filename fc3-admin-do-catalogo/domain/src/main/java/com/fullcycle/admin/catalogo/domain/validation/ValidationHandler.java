package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);
    
    <T> T validate(Validation<T> aValidation);

    List<Error> getErrors();

    default boolean hasError(){
        return getErrors() != null && getErrors().size() > 0;
    }
   
    default Error firstError(){
        return getErrors() != null && !getErrors().isEmpty() ? getErrors().get(0) : null;
    }

    interface Validation<T> {
        T validate();
    }

}
