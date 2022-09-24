package com.fullcycle.admin.catalogo.infrastructure.genre.persistence;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fullcycle.admin.catalogo.domain.category.CategoryID;

@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {
    
    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity() {}

    private GenreCategoryJpaEntity(
        final GenreJpaEntity aGenre,
        final CategoryID aCategoryID){
        this.id = GenreCategoryID.from(aGenre.getId(), aCategoryID.getValue());
        this.genre = aGenre;
    }

    public static GenreCategoryJpaEntity from(
            final GenreJpaEntity aGenre,
            final CategoryID aCategoryId){
        return new GenreCategoryJpaEntity(aGenre, aCategoryId);
    }


    public GenreCategoryID getId() {
        return this.id;
    }

    public void setId(GenreCategoryID id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return this.genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GenreCategoryJpaEntity)) {
            return false;
        }
        GenreCategoryJpaEntity genreCategoryJpaEntity = (GenreCategoryJpaEntity) o;
        return Objects.equals(id, genreCategoryJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
