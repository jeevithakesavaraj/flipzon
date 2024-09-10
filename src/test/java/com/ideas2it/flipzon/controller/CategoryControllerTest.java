package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.CategoryService;
import com.ideas2it.flipzon.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CategoryController categoryController;

    CategoryDto categoryDto;
    ProductDto productDto;
    @BeforeEach
    void setUp() {
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("electronics")
                .build();

        productDto = ProductDto.builder()
                .id(1L)
                .build();
    }
    @Test
    void testAddCategory() {
        when(categoryService.addCategory(categoryDto)).thenReturn(categoryDto);
        ResponseEntity<CategoryDto> response = categoryController.addCategory(categoryDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService, times(1)).addCategory(categoryDto);
    }
    @Test
    void testDeleteCategory() {
        when(categoryService.deleteCategory(categoryDto.getId())).thenReturn(true);
        ResponseEntity<CategoryDto> response = categoryController.deleteCategory(categoryDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryService, times(1)).deleteCategory(categoryDto.getId());
    }
    @Test
    void testUpdateCategory() {
        when(categoryService.updateCategory(anyLong(), categoryDto)).thenReturn(categoryDto);
        ResponseEntity<CategoryDto> response = categoryController.updateCategory(anyLong(), categoryDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService, times(1)).updateCategory(1L, categoryDto);
    }
    @Test
    void testRetrieveCategory() {
        when(categoryService.retrieveCategoryById(categoryDto.getId())).thenReturn(categoryDto);
        ResponseEntity<CategoryDto> response = categoryController.getCategoryById(categoryDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService, times(1)).retrieveCategoryById(categoryDto.getId());
    }
    @Test
    void testRetrieveCategories() {
        when(categoryService.retrieveAllCategory()).thenReturn(List.of(categoryDto));
        ResponseEntity<List<CategoryDto>> response = categoryController.getAllCategory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryService, times(1)).retrieveAllCategory();
    }
    @Test
    void testGetAllProductsByCategoryId() {
        when(productService.retrieveAllProductByCategoryId(productDto.getId())).thenReturn(List.of(productDto));
        ResponseEntity<List<ProductDto>> response = categoryController.getAllProductsByCategoryId(productDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).retrieveAllProductByCategoryId(productDto.getId());
    }
}

