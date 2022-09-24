package com.fullcycle.admin.catalogo.infrastructure.genre;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcycle.admin.catalogo.domain.genre.GenreID;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;

@Component
public class GenreMySqlGateway implements GenreGateway{

    private final GenreRepository genreRepository;

    public GenreMySqlGateway(GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }


    @Override
    public Genre create(Genre genre) {
        return save(genre);
    }


    private Genre save(Genre genre) {
        return this.genreRepository.save(GenreJpaEntity.from(genre))
                    .toAggreagate();
    }

    @Override
    public void deleteById(GenreID anId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<Genre> findById(GenreID anId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Genre update(Genre aGenre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
