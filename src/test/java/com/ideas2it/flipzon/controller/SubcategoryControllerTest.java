package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.SubcategoryService;
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
public class SubcategoryControllerTest {
    @Mock
    private SubcategoryService subcategoryService;
    @Mock
    private ProductService productService;

    @InjectMocks
    private SubcategoryController subcategoryController;

    SubcategoryDto subcategoryDto;

    ProductDto productDto;

    @BeforeEach
    void setUp() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("electronics")
                .build();
        subcategoryDto = SubcategoryDto.builder()
                .id(1L)
                .name("mobile")
                .categoryId(categoryDto.getId())
                .build();
        productDto = ProductDto.builder()
                .id(1L)
                .build();
    }

    @Test
    void testAddSubcategory() {
        when(subcategoryService.addSubcategory(subcategoryDto)).thenReturn(subcategoryDto);
        ResponseEntity<SubcategoryDto> response = subcategoryController.addSubcategory(subcategoryDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(subcategoryDto, response.getBody());
        verify(subcategoryService, times(1)).addSubcategory(subcategoryDto);
    }

    @Test
    void testDeleteSubcategory() {
        when(subcategoryService.deleteSubcategory(subcategoryDto.getId())).thenReturn(true);
        ResponseEntity<SubcategoryDto> response = subcategoryController.deleteSubcategory(subcategoryDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(subcategoryService, times(1)).deleteSubcategory(subcategoryDto.getId());
    }

    @Test
    void testUpdateSubcategory() {
        when(subcategoryService.updateSubcategory(anyLong(), subcategoryDto)).thenReturn(subcategoryDto);
        ResponseEntity<SubcategoryDto> response = subcategoryController.updateSubcategory(anyLong(),subcategoryDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subcategoryDto, response.getBody());
        verify(subcategoryService, times(1)).updateSubcategory(1L, subcategoryDto);
    }

    @Test
    void testRetrieveSubcategory() {
        when(subcategoryService.retrieveSubcategoryById(subcategoryDto.getId())).thenReturn(subcategoryDto);
        ResponseEntity<SubcategoryDto> response = subcategoryController.getSubcategoryById(subcategoryDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subcategoryDto, response.getBody());
        verify(subcategoryService, times(1)).retrieveSubcategoryById(subcategoryDto.getId());
    }

    @Test
    void testRetrieveSubcategories() {
        when(subcategoryService.retrieveAllSubcategory()).thenReturn(List.of(subcategoryDto));
        ResponseEntity<List<SubcategoryDto>> response = subcategoryController.getAllSubcategory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(subcategoryService, times(1)).retrieveAllSubcategory();
    }

    @Test
    void testGetAllProductsBySubcategoryId() {
        when(productService.retrieveAllProductBySubcategoryId(productDto.getId())).thenReturn(List.of(productDto));
        ResponseEntity<List<ProductDto>> response = subcategoryController.getAllProductBySubcategoryId(productDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).retrieveAllProductBySubcategoryId(productDto.getId());
    }
}

