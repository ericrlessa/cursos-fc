package com.fullcycle.admin.catalogo.application.genre.create;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase{

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultCreateGenreUseCase(
        final CategoryGateway aCategoryGateway,
        final GenreGateway aGenreGateway) {
        this.categoryGateway = aCategoryGateway;
        this.genreGateway = aGenreGateway;
    }

    @Override
    public CreateGenreOutput execute(final CreateGenreCommand aCommand){
        final var aName = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryId(aCommand.categories());

        final var notification = Notification.create();
        notification.append(validateCategories(categories));

        final var aGenre = notification.validate(() -> Genre.newGenre(aName, isActive));

        if(notification.hasError()){
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        aGenre.addCategories(categories);

        return CreateGenreOutput.from(this.genreGateway.create(aGenre));
    }


    private ValidationHandler validateCategories(final List<CategoryID> ids) {
        final var notification = Notification.create();
        if(ids == null || ids.isEmpty()){
            return notification;
        }

        final var retrieveIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrieveIds.size()){
            final var commandsIds = new ArrayList<>(ids);
            commandsIds.removeAll(retrieveIds);

            final var missingIdsMessage = commandsIds.stream()
                .map(CategoryID::getValue)
                .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }


    private List<CategoryID> toCategoryId(List<String> categories) {
        return categories.stream()
                .map(CategoryID::from)
                .toList();
    }
    
}
