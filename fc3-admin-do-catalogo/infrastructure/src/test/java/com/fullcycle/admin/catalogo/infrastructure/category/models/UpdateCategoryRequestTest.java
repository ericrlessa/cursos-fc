package com.fullcycle.admin.catalogo.infrastructure.category.models;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import com.fullcycle.admin.catalogo.JacksonTest;

@JacksonTest
public class UpdateCategoryRequestTest {
    
    @Autowired
    private JacksonTester<UpdateCategoryRequest> json;

    @Test
    public void testUnmarshall() throws Exception{
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = true;

        final var json = """
            {
                "name": "%s",
                "description": "%s",
                "is_active": "%s"
            }       
         """.formatted(
            expectedName,
            expectedDescription,
            expectedIsActive) ;

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
            .hasFieldOrPropertyWithValue("name", expectedName)
            .hasFieldOrPropertyWithValue("description", expectedDescription)
            .hasFieldOrPropertyWithValue("active", expectedIsActive);
    }

}
