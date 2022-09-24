package com.fullcycle.admin.catalogo.domain.genre;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;

public class Genre extends AggregateRoot<GenreID>{

    private String name;

    private boolean active;

    private List<CategoryID> categories;

    private Instant createdAt;
    
    private Instant updatedAt;
    
    private Instant deletedAt;

    public Genre(
        GenreID genreID,
        String name,
        boolean active, 
        List<CategoryID> categories,       
        Instant createdAt, 
        Instant updatedAt,
        Instant deletedAt) {
        super(genreID);
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.categories = categories;

        selfValidate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if(notification.hasError()){
            throw new NotificationException("", notification);
        }
    }
    
    public static Genre newGenre(
            final String name,
            final boolean isActive) {
        final var anId = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Genre(anId,
                name,
                isActive,
                new ArrayList<>(),
                now,
                now,
                deletedAt);
    }

    public static Genre with(
            GenreID genreID,
            String name,
            boolean active,
            List<CategoryID> categories,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt) {
        return new Genre(genreID, name, active, categories, createdAt, updatedAt, deletedAt);
    }

    public static Genre with(final Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.getName(),
                aGenre.isActive(),
                new ArrayList<>(aGenre.getCategories()),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt());
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre deactivate() {
        if(getDeletedAt() == null){
            this.deletedAt = InstantUtils.now();
        }
        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }
    
    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryID> categories){
        if(isActive){
            activate();
        }else{
            deactivate();
        }
        this.name = aName;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public String getName() {
        return this.name;
    }

    public boolean getActive() {
        return this.active;
    }

    public boolean isActive() {
        return this.active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Genre addCategory(CategoryID aCategoryID) {
        if(aCategoryID == null){
            return this;
        }

        this.categories.add(aCategoryID);
        this.updatedAt = InstantUtils.now();
        return this;
    }
    
    public Genre removeCategory(CategoryID aCategoryID) {
        if(aCategoryID == null){
            return this;
        }
        
        this.categories.remove(aCategoryID);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre addCategories(List<CategoryID> categories) {
        if(categories == null || categories.isEmpty()){
            return this;
        }

        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();
        return this;
    }
    
}
