package com.fullcycle.admin.catalogo.domain.genre;

import java.util.Optional;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;

public interface GenreGateway {
    Genre create(Genre genre);

    void deleteById(GenreID anId);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);
    
    Pagination<Genre> findAll(SearchQuery aQuery);
}