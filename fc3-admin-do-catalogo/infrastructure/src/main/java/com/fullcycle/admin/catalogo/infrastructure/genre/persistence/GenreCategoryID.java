package com.fullcycle.admin.catalogo.infrastructure.genre.persistence;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GenreCategoryID implements Serializable {
    
    @Column(name = "genreId", nullable = false)
    private String genreId;
    
    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public GenreCategoryID() {
    }

    public GenreCategoryID(String genreId, String categoryId) {
        this.genreId = genreId;
        this.categoryId = categoryId;
    }

    public static GenreCategoryID from(final String aGenreId, final String aCategoryId){
        return new GenreCategoryID(aGenreId, aCategoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GenreCategoryID)) {
            return false;
        }
        GenreCategoryID genreCategoryId = (GenreCategoryID) o;
        return Objects.equals(genreId, genreCategoryId.genreId) && Objects.equals(categoryId, genreCategoryId.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, categoryId);
    }

    public String getGenreId() {
        return this.genreId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }



}
