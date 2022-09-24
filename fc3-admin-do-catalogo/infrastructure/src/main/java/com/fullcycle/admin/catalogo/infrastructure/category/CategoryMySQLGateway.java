package com.fullcycle.admin.catalogo.infrastructure.category;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJPAEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils;

@Component
public class CategoryMySQLGateway implements CategoryGateway{
    
    private final CategoryRepository repository;

    public CategoryMySQLGateway(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @Override
    public Category create(Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(final CategoryID anId) {
        final var anIdValue = anId.getValue();

        if(this.repository.existsById(anIdValue)){
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return this.repository.findById(anId.getValue())
                .map(CategoryJPAEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryJPAEntity.from(aCategory)).toAggregate();
    }

    @Override
    public Pagination<Category> findAll(final SearchQuery aQuery) {
        //Paginacao
        //Busca dinamica pelo criterio terms (name ou description)

        PageRequest page = PageRequest.of(
            aQuery.page(),
            aQuery.perPage(),
            Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
            .filter(str -> !str.isBlank())
            .map(
                str -> SpecificationUtils.<CategoryJPAEntity>like("name", str)
                .or(SpecificationUtils.<CategoryJPAEntity>like("description", str))
            )
            .orElse(null);
            

        final var pageResult = this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(), 
                pageResult.getTotalElements(), 
                pageResult.map(CategoryJPAEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CategoryID> existsByIds(Iterable<CategoryID> ids) {
        // TODO
        return Collections.emptyList();
    }

}
