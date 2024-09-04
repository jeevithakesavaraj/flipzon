package com.ideas2it.flipzon.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.BrandDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Brand;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    @Mock
    private BrandDao brandDao;

    @InjectMocks
    private BrandServiceImpl brandService;

    private BrandDto brandDto;
    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = Brand.builder()
                .id(1L)
                .name("apple")
                .isDeleted(false)
                .build();
        brandDto = BrandDto.builder()
                .id(1L)
                .name("apple")
                .build();
    }

    @Test
    void testAddBrand() {
        when(brandDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(false);
        when(brandDao.save(any(Brand.class))).thenReturn(brand);
        BrandDto createdBrand = brandService.addBrand(brandDto);
        assertNotNull(createdBrand);
        assertEquals(brandDto.getName(), createdBrand.getName());
        verify(brandDao, times(1)).save(any(Brand.class));
    }

    @Test
    void testAddBrandNegative() {
        when(brandDao.existsByNameAndIsDeletedFalse(anyString())).thenThrow(new NullPointerException("database error"));
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            brandService.addBrand(brandDto);
        });
        assertTrue(exception.getMessage().contains("database error"));
    }

    @Test
    void testAddBrandException() {
        when(brandDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(true);
        assertThrows(MyException.class, () -> brandService.addBrand(brandDto));
    }

    @Test
    void testDeleteBrand() {
        when(brandDao.findByIdAndIsDeletedFalse(brand.getId())).thenReturn(brand);
        boolean flag = brandService.deleteBrand(brandDto.getId());
        assertTrue(flag);
    }

    @Test
    void testDeletedBrand() {
        when(brandDao.findByIdAndIsDeletedFalse(brandDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            brandService.deleteBrand(brandDto.getId());
        });
    }

    @Test
    void testGetAllBrands() {
        when(brandDao.findByIsDeletedFalse()).thenReturn(List.of(brand));
        List<BrandDto> brandDtos = brandService.retrieveAllBrand();
        assertNotNull(brandDtos);
    }

    @Test
    void testGetAllBrandsEmpty() {
        when(brandDao.findByIsDeletedFalse()).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> {
            brandService.retrieveAllBrand();
        });
    }

    @Test
    void testUpdateBrandNotPresent() {
        when(brandDao.findByIdAndIsDeletedFalse(brandDto.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> {
            brandService.updateBrand(brandDto);
        });
    }

    @Test
    void testUpdateBrand() {
        when(brandDao.findByIdAndIsDeletedFalse(brandDto.getId())).thenReturn(brand);
        when(brandDao.existsByNameAndIsDeletedFalse(brandDto.getName())).thenReturn(false);
        brand.setName("samsung");
        when(brandDao.saveAndFlush(any(Brand.class))).thenReturn(brand);
        BrandDto response = brandService.updateBrand(brandDto);
        assertNotNull(response);
        assertEquals(response.getName(), brand.getName());
        verify(brandDao, times(1)).saveAndFlush(any(Brand.class));
    }

    @Test
    void getBrandByIdNotPresent() {
        when(brandDao.findByIdAndIsDeletedFalse(brandDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            brandService.retrieveBrandById(brandDto.getId());
        });
    }

    @Test
    void getBrandById() {
        when(brandDao.findByIdAndIsDeletedFalse(brandDto.getId())).thenReturn(brand);
        BrandDto response = brandService.retrieveBrandById(brandDto.getId());
        assertEquals(response.getId(), brandDto.getId());
    }
}
