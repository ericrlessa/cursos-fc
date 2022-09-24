package com.fullcycle.admin.catalogo.infrastructure.genre.persistence;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreID;

@Entity
@Table(name = "genres")
public class GenreJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GenreCategoryJpaEntity> categories;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;
    
    @Column(name = "deleted_at", nullable = true, columnDefinition = "DATETIME(6)")
    private Instant deletedAt;
    
    public GenreJpaEntity() {}

    private GenreJpaEntity(
        final String anId,
        final String aName,
        final boolean isActive,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ){
        this.id = anId;
        this.name = aName;
        this.active = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.categories = new HashSet<>();
    }

    public static GenreJpaEntity from(final Genre aGenre){
        final var entity = new GenreJpaEntity(
            aGenre.getId().getValue(),
            aGenre.getName(),
            aGenre.isActive(),
            aGenre.getCreatedAt(),
            aGenre.getUpdatedAt(),
            aGenre.getDeletedAt()
        );

        aGenre.getCategories()
                .forEach(entity::addCategory);

        return entity;
    }

    public Genre toAggreagate(){
        return Genre.with(
            GenreID.from(getId()),
            getName(),
            isActive(),
            getCategoryIds(),
            getCreatedAt(),
            getUpdatedAt(),
            getDeletedAt()
        );
    }

    public List<CategoryID> getCategoryIds() {
        return getCategories().stream()
            .map(it -> CategoryID.from(it.getId().getCategoryId())).toList();
    }

    private void addCategory(final CategoryID anId){
        this.categories.add(GenreCategoryJpaEntity.from(this, anId));
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<GenreCategoryJpaEntity> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<GenreCategoryJpaEntity> categories) {
        this.categories = categories;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
    

}
