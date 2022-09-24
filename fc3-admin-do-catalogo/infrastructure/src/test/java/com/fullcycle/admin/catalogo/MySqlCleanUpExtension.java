package com.fullcycle.admin.catalogo;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;

public class MySqlCleanUpExtension implements BeforeEachCallback {
 
    @Override
    public void beforeEach(final ExtensionContext context){
        final var appContext = SpringExtension.getApplicationContext(context);
        
        cleanUp(List.of(
            appContext.getBean(GenreRepository.class),
            appContext.getBean(CategoryRepository.class)
        ));

    //    final var em = appContext.getBean(TestEntityManager.class);
    //    em.flush();
    //    em.clear();
    }
    
    private void cleanUp(final Collection<CrudRepository> repositories){
        repositories.forEach(CrudRepository::deleteAll);
    }
}
    
  
