package com.liancorp.lianstock.application;

import com.liancorp.lianstock.application.dto.request.CategoryRequest;
import com.liancorp.lianstock.application.mapper.CategoryMapper;
import com.liancorp.lianstock.domain.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        categoryMapper = new CategoryMapper();
    }

    @Test
    void shouldMapRequestToModelSuccessfully() {
        //Arrange
        var request = new CategoryRequest("test", "test");

        //Act
        Category category = categoryMapper.toModelFromRequest(request);
        //Assert
        Assertions.assertNull(category.getId());
        Assertions.assertEquals(request.name(), category.getName());
        Assertions.assertEquals(request.description(), category.getDescription());
    }

}
