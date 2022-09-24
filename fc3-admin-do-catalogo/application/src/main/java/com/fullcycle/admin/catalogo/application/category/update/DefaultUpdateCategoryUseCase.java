package com.fullcycle.admin.catalogo.application.category.update;

import java.util.Objects;
import java.util.function.Supplier;

import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;

import io.vavr.API;
import io.vavr.control.Either;

public class DefaultUpdateCategoryUseCase 
    extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway){
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());


        var category = this.categoryGateway.findById(anId)
                .orElseThrow(notFound(anId));
        
        final var notification = Notification.create();

        category.update(aCommand.name(), aCommand.description(), aCommand.isActive());
        category.validate(notification);

        return notification.hasError() ? API.Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
            .toEither()
            .bimap(Notification::create, UpdateCategoryOutput::from);
            
    }

    private Supplier<DomainException> notFound(final Identifier anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
