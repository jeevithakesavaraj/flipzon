package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ideas2it.flipzon.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        BrandDto brandDto = BrandDto.builder()
                .id(1L)
                .name("apple")
                .build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("electronics")
                .build();
        SubcategoryDto subcategoryDto = SubcategoryDto.builder()
                .id(1L)
                .name("mobile")
                .build();
        productDto = ProductDto.builder()
                .id(1L)
                .name("iphone12")
                .price(80000)
                .brandId(brandDto.getId())
                .categoryId(categoryDto.getId())
                .subcategoryId(subcategoryDto.getCategoryId())
                .build();
    }
    @Test
    void testAddProduct() {
        when(productService.addProduct(productDto)).thenReturn(productDto);
        ResponseEntity<ProductDto> response = productController.addProduct(productDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDto, response.getBody());
        verify(productService, times(1)).addProduct(productDto);
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct(productDto.getId())).thenReturn(true);
        ResponseEntity<ProductDto> response = productController.deleteProduct(productDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(productDto.getId());
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(1L, productDto)).thenReturn(productDto);
        ResponseEntity<ProductDto> response = productController.updateProduct(1L, productDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
        verify(productService, times(1)).updateProduct(1L, productDto);
    }

    @Test
    void testRetrieveProduct() {
        when(productService.retrieveProductById(productDto.getId())).thenReturn(productDto);
        ResponseEntity<ProductDto> response = productController.getProductById(productDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
        verify(productService, times(1)).retrieveProductById(productDto.getId());
    }

    @Test
    void testRetrieveProducts() {
        when(productService.retrieveAllProduct()).thenReturn(List.of(productDto));
        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).retrieveAllProduct();
    }

    @Test
    void testUpdateProductPrice() {
        when(productService.updateProductPrice(1L, productDto.getPrice())).thenReturn(productDto);
        ResponseEntity<ProductDto> response = productController.updateProductPrice(1L, ProductPriceDto.builder()
                .price(12000).build());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).updateProductPrice(1L, productDto.getPrice());
    }
}
