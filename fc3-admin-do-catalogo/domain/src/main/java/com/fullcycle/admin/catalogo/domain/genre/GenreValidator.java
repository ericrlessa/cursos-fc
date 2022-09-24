package com.fullcycle.admin.catalogo.domain.genre;

import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.domain.validation.Validator;

public class GenreValidator extends Validator{

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 255;

    private final Genre genre;

    protected GenreValidator(final Genre aGenre, ValidationHandler aHandler) {
        super(aHandler);
        this.genre = aGenre;
    }


    @Override
    public void validate() {
        cheackNameConstraints();
    }
    
    private void cheackNameConstraints() {
        final var name = this.genre.getName();

        if(name == null){
            this.validationHandler().append(new Error("'name' shouldn't be null"));
            return;
        }
       
        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' shouldn't be empty"));
            return;
        }
        
        final int length = name.trim().length();
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH){
            this.validationHandler().append(new Error("'name' should be between 3 and 255 characters"));
            return;
        }
    }

    
}
