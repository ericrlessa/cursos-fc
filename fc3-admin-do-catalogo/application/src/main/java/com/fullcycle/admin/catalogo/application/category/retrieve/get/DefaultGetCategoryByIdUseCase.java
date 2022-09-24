package com.fullcycle.admin.catalogo.application.category.retrieve.get;

import java.util.function.Supplier;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway aCategoryGateway) {
        this.categoryGateway = aCategoryGateway;
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var anCategoryId = CategoryID.from(anIn);

        return this.categoryGateway.findById(CategoryID.from(anIn))
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryId));
    }

    private Supplier<? extends DomainException> notFound(final CategoryID aCategoryID) {
        return () -> NotFoundException.with(
            Category.class,
            aCategoryID
        );
    }
    
}
