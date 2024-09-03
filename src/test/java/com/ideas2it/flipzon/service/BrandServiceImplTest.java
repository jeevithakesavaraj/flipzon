package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.BrandDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    BrandDto brandDto;
    Brand brand;

    @BeforeEach
    void setUp() {
        brandDto = BrandDto.builder()
                .id(1L)
                .name("apple")
                .build();
        brand = Brand.builder()
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
    void testAddBrand_negative() {
        when(brandDao.existsByNameAndIsDeletedFalse(anyString())).thenThrow(new NullPointerException("database error"));
        NullPointerException exception = assertThrows(NullPointerException.class, ()-> {
            brandService.addBrand(brandDto);
        });
        assertTrue(exception.getMessage().contains("database error"));
    }

    @Test
    void testAddBrand_exception() {
        when(brandDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(true);
        assertThrows(MyException.class, () -> brandService.addBrand(brandDto));
    }

}
