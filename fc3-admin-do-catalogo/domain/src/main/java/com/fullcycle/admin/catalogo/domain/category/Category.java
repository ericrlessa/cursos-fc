package com.fullcycle.admin.catalogo.domain.category;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
        final CategoryID id, 
        final String name,
        final String description,
        final boolean isActive,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.deletedAt = deletedAt;
        this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
        this.deletedAt = deletedAt != null ? deletedAt.truncatedTo(ChronoUnit.MICROS) : null;
    }

    public static Category newCategory(
        final String aName, 
        final String aDescription, 
        final boolean isActive){

        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }

    public static Category with(
        final Category aCategory
    ){
        return with(
            aCategory.getId(), 
            aCategory.name,
            aCategory.description,
            aCategory.active, 
            aCategory.createdAt,
            aCategory.updatedAt,
            aCategory.deletedAt
        );
    }

    public static Category with(
        final CategoryID id, 
        final String name,
        final String description,
        final boolean isActive,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt){
        return new Category(
                id, name, description, isActive, createdAt, updatedAt, deletedAt); 
    }

    @Override
    public void validate(final ValidationHandler handler){
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
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

    public Category deactivate() {
        if(getDeletedAt() == null){
            this.deletedAt = Instant.now();
        }
        
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(
        final String aName,
        final String aDescription,
        final boolean isActive) {

        if (isActive){
            activate();
        }else{
            deactivate();
        }

        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();        

        return this;
    }

    @Override
    public Category clone(){
        try{
            return (Category) super.clone();
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

}
