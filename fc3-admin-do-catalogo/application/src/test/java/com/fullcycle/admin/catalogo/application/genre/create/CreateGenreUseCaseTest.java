package com.fullcycle.admin.catalogo.application.genre.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;

public class CreateGenreUseCaseTest extends UseCaseTest{

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks(){
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId(){
        //given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        Mockito.when(genreGateway.create(Mockito.any()))
            .thenAnswer(AdditionalAnswers.returnsFirstArg());
        
        //when
        final var actualOutput = useCase.execute(aCommand);
            
        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, times(1)).create(Mockito.argThat(aGenre -> 
                Objects.equals(expectedName, aGenre.getName())
                && Objects.equals(expectedIsActive, aGenre.isActive())
                && Objects.equals(expectedCategories, aGenre.getCategories())
                && Objects.nonNull(aGenre.getId())
                && Objects.nonNull(aGenre.getCreatedAt())
                && Objects.nonNull(aGenre.getUpdatedAt())
                && Objects.isNull(aGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_shouldReturnGenreId(){

        //given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
            CategoryID.from("123"),
            CategoryID.from("456")
        );

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        Mockito.when(categoryGateway.existsByIds(Mockito.any()))
            .thenReturn(expectedCategories);

        Mockito.when(genreGateway.create(Mockito.any()))
            .thenAnswer(AdditionalAnswers.returnsFirstArg());
        
        //when
        final var actualOutput = useCase.execute(aCommand);
            
        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);

        Mockito.verify(genreGateway, times(1)).create(Mockito.argThat(aGenre -> 
                Objects.equals(expectedName, aGenre.getName())
                && Objects.equals(expectedIsActive, aGenre.isActive())
                && Objects.equals(expectedCategories, aGenre.getCategories())
                && Objects.nonNull(aGenre.getId())
                && Objects.nonNull(aGenre.getCreatedAt())
                && Objects.nonNull(aGenre.getUpdatedAt())
                && Objects.isNull(aGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateGenre_shouldReturnDomainException(){
        
        //given
        final var expectedName = "  ";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' shouldn't be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualException = Assertions.assertThrows(
            NotificationException.class,
            () -> useCase.execute(aCommand));
        
        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(0)).existsByIds(Mockito.any());
        Mockito.verify(genreGateway, times(0)).create(Mockito.any());
    }
    
    @Test
    public void givenAInvalidNullName_whenCallsCreateGenre_shouldReturnDomainException(){
        
        //given
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' shouldn't be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualException = Assertions.assertThrows(
            NotificationException.class,
            () -> useCase.execute(aCommand));
        
        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(0)).existsByIds(Mockito.any());
        Mockito.verify(genreGateway, times(0)).create(Mockito.any());
    }
   
    @Test
    public void givenAValidCommand_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException(){
        
        //given
        final var filmes = CategoryID.from("456");
        final var series = CategoryID.from("123");
        final var documentarios = CategoryID.from("789");

        final String expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
               filmes, series, documentarios
        );

        final var expectedErrorMessage = "Some categories could not be found: 456, 789";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(any()))
            .thenReturn(List.of(series));

        //when
        final var actualException = Assertions.assertThrows(
            NotificationException.class,
            () -> useCase.execute(aCommand));
        
        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(1)).existsByIds(Mockito.any());
        Mockito.verify(genreGateway, times(0)).create(Mockito.any());
    }
    
    @Test
    public void givenAnInvalidName_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException(){
        
        //given
        final var filmes = CategoryID.from("456");
        final var series = CategoryID.from("123");
        final var documentarios = CategoryID.from("789");

        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
               filmes, series, documentarios
        );

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' shouldn't be empty";
        final var expectedErrorCount = 2;

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(any()))
            .thenReturn(List.of(series));

        //when
        final var actualException = Assertions.assertThrows(
            NotificationException.class,
            () -> useCase.execute(aCommand));
        
        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        Mockito.verify(categoryGateway, times(1)).existsByIds(Mockito.any());
        Mockito.verify(genreGateway, times(0)).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreate_shouldReturnGenreId(){
        
          //given
          final var expectedName = "Ação";
          final var expectedIsActive = false;
          final var expectedCategories = List.<CategoryID>of(
              CategoryID.from("123"),
              CategoryID.from("456")
          );
  
          final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));
  
          Mockito.when(categoryGateway.existsByIds(Mockito.any()))
              .thenReturn(expectedCategories);
  
          Mockito.when(genreGateway.create(Mockito.any()))
              .thenAnswer(AdditionalAnswers.returnsFirstArg());
          
          //when
          final var actualOutput = useCase.execute(aCommand);
              
          //then
          Assertions.assertNotNull(actualOutput);
          Assertions.assertNotNull(actualOutput.id());
  
          Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);
  
          Mockito.verify(genreGateway, times(1)).create(Mockito.argThat(aGenre -> 
                  Objects.equals(expectedName, aGenre.getName())
                  && Objects.equals(expectedIsActive, aGenre.isActive())
                  && Objects.equals(expectedCategories, aGenre.getCategories())
                  && Objects.nonNull(aGenre.getId())
                  && Objects.nonNull(aGenre.getCreatedAt())
                  && Objects.nonNull(aGenre.getUpdatedAt())
                  && Objects.nonNull(aGenre.getDeletedAt())
          ));

    }

    private List<String> asString(final List<CategoryID> categories){
        return categories.stream()
                .map(CategoryID::getValue)
                .toList();
    }

}
