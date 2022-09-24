package com.fullcycle.admin.catalogo.application.genre.delete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcycle.admin.catalogo.domain.genre.GenreID;

public class DeleteGenreUseCaseTest extends UseCaseTest{

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test 
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre(){
        //given
        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Mockito.doNothing()
            .when(genreGateway).deleteById(any());

        //when 
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }
   
    @Test 
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldBeOk(){
        //given
        final var expectedId = GenreID.from("123");

        Mockito.doNothing()
            .when(genreGateway).deleteById(any());

        //when 
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }
    
    @Test 
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shouldReceiveException(){
        //given
        final var expectedId = GenreID.from("123");

        Mockito.doThrow(new IllegalStateException("Gateway Error"))
            .when(genreGateway).deleteById(any());

        //when 
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);
    }

}
