package com.fullcycle.admin.catalogo.application.genre.retrieve.list;

import java.time.Instant;
import java.util.List;

import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.genre.Genre;

public record GenreListOutput (
    String name,
    boolean isActive,
    List<String> categories,
    Instant createdAt,
    Instant deletedAt
){
    
    public static GenreListOutput from(final Genre aGenre){
        return new GenreListOutput(
            aGenre.getName(),
            aGenre.isActive(),
            aGenre.getCategories()
                .stream()
                .map(CategoryID::getValue)
                .toList(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
