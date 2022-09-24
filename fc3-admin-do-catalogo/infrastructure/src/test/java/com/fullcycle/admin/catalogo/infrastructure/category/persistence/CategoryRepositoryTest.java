package com.fullcycle.admin.catalogo.infrastructure.category.persistence;

import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.fullcycle.admin.catalogo.MySQLGatewayTest;
import com.fullcycle.admin.catalogo.domain.category.Category;

@MySQLGatewayTest
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_shouldReturnError(){
        final String expectedProperty = "name";
        final String expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJPAEntity.name";

        final var aCategory = Category.newCategory("Filmes", "A Categoria mais assistida", true);

        final var anEntity = CategoryJPAEntity.from(aCategory);
        anEntity.setName(null);

        final var actualException 
            = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause 
            = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
    
        Assertions.assertEquals(expectedProperty, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidCreatedAt_whenCallsSave_shouldReturnError(){
        final String expectedProperty = "createdAt";
        final String expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJPAEntity.createdAt";

        final var aCategory = Category.newCategory("Filmes", "A Categoria mais assistida", true);

        final var anEntity = CategoryJPAEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException 
            = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause 
            = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
    
        Assertions.assertEquals(expectedProperty, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidUpdatedAt_whenCallsSave_shouldReturnError(){
        final String expectedProperty = "updatedAt";
        final String expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJPAEntity.updatedAt";

        final var aCategory = Category.newCategory("Filmes", "A Categoria mais assistida", true);

        final var anEntity = CategoryJPAEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException 
            = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause 
            = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
    
        Assertions.assertEquals(expectedProperty, actualCause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, actualCause.getMessage());
    }

    
}
