package com.ideas2it.flipzon.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.BrandService;
import com.ideas2it.flipzon.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {
    @Mock
    private BrandService brandService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private BrandController brandController;

    private BrandDto brandDto;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        brandDto = BrandDto.builder()
                .id(1L)
                .name("apple")
                .build();
        productDto = ProductDto.builder()
                .id(1L)
                .build();
    }

    @Test
    void testAddBrand() {
        when(brandService.addBrand(brandDto)).thenReturn(brandDto);
        ResponseEntity<BrandDto> response = brandController.addBrand(brandDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(brandDto, response.getBody());
        verify(brandService, times(1)).addBrand(brandDto);
    }

    @Test
    void testDeleteBrand() {
        when(brandService.deleteBrand(brandDto.getId())).thenReturn(true);
        ResponseEntity<BrandDto> response = brandController.deleteBrand(brandDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandService, times(1)).deleteBrand(brandDto.getId());
    }

    @Test
    void testUpdateBrand() {
        when(brandService.updateBrand(anyLong(), brandDto)).thenReturn(brandDto);
        ResponseEntity<BrandDto> response = brandController.updateBrand(1L, brandDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandDto, response.getBody());
        verify(brandService, times(1)).updateBrand(1L,brandDto);
    }

    @Test
    void testRetrieveBrand() {
        when(brandService.retrieveBrandById(brandDto.getId())).thenReturn(brandDto);
        ResponseEntity<BrandDto> response = brandController.getBrandById(brandDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandDto, response.getBody());
        verify(brandService, times(1)).retrieveBrandById(brandDto.getId());
    }

    @Test
    void testRetrieveBrands() {
        when(brandService.retrieveAllBrand()).thenReturn(List.of(brandDto));
        ResponseEntity<List<BrandDto>> response = brandController.getAllBrands();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandService, times(1)).retrieveAllBrand();
    }

    @Test
    void testGetAllProductsByBrandId() {
        when(productService.retrieveAllProductByBrandId(brandDto.getId())).thenReturn(List.of(productDto));
        ResponseEntity<List<ProductDto>> response = brandController.getAllProductsByBrandId(brandDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productService, times(1)).retrieveAllProductByBrandId(brandDto.getId());
    }
}
