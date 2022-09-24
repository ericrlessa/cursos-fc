package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;

public record UpdateCategoryOutput (

    String id

){
    public static UpdateCategoryOutput from(final Category category){
        return from(category.getId().getValue());
    }
    
    public static UpdateCategoryOutput from(final String id){
        return new UpdateCategoryOutput(id);
    }
}
