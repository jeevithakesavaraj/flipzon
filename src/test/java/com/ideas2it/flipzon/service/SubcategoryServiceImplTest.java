package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.SubcategoryDao;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Category;
import com.ideas2it.flipzon.model.Subcategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryDao subcategoryDao;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryService;

    private SubcategoryDto subcategoryDto;
    private Subcategory subcategory;
    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("electronics")
                .build();
        category = Category.builder()
                .id(1L)
                .name("electronics")
                .isDeleted(false)
                .build();
        subcategoryDto = SubcategoryDto.builder()
                .id(1L)
                .name("mobile")
                .categoryId(category.getId())
                .build();
        subcategory = Subcategory.builder()
                .id(1L)
                .name("mobile")
                .isDeleted(false)
                .category(category)
                .build();
    }

    @Test
    void testAddSubcategoryNegative() {
        when(subcategoryDao.existsByNameAndIsDeletedFalse(anyString())).thenThrow(new NullPointerException("database error"));
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            subcategoryService.addSubcategory(subcategoryDto);
        });
        assertTrue(exception.getMessage().contains("database error"));
    }

    @Test
    void testAddSubcategoryException() {
        when(subcategoryDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(true);
        assertThrows(MyException.class, () -> subcategoryService.addSubcategory(subcategoryDto));
    }

    @Test
    void testAddSubcategory() {
        when(subcategoryDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(false);
        when(categoryService.retrieveCategoryById(subcategoryDto.getCategoryId())).thenReturn(categoryDto);
        subcategory.setCategory(category);
        when(subcategoryDao.saveAndFlush(any(Subcategory.class))).thenReturn(subcategory);
        SubcategoryDto response = subcategoryService.addSubcategory(subcategoryDto);
        assertEquals(subcategory.getName(), response.getName());
    }

    @Test
    void testDeleteSubcategory() {
        when(subcategoryDao.findByIdAndIsDeletedFalse(subcategory.getId())).thenReturn(subcategory);
        boolean flag = subcategoryService.deleteSubcategory(subcategoryDto.getId());
        assertTrue(flag);
    }

    @Test
    void testDeletedSubcategory() {
        when(subcategoryDao.findByIdAndIsDeletedFalse(subcategoryDto.getId())).thenReturn(subcategory);
        subcategory.setDeleted(true);
        assertThrows(ResourceNotFoundException.class, () -> {
            subcategoryService.deleteSubcategory(subcategoryDto.getId());
        });
    }

    @Test
    void testGetAllCategories() {
        when(subcategoryDao.findByIsDeletedFalse()).thenReturn(List.of(subcategory));
        List<SubcategoryDto> subcategoryDtos = subcategoryService.retrieveAllSubcategory();
        assertNotNull(subcategoryDtos);
    }

    @Test
    void testGetAllCategoriesEmpty() {
        List<Subcategory> subcategories = new ArrayList<>();
        when(subcategoryDao.findByIsDeletedFalse()).thenReturn(subcategories);
        assertThrows(ResourceNotFoundException.class, () ->{
            subcategoryService.retrieveAllSubcategory();
        });
    }

    @Test
    void testUpdateSubcategoryNotPresent() {
        when(subcategoryDao.findByIdAndIsDeletedFalse(subcategoryDto.getId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () ->{
            subcategoryService.updateSubcategory(anyLong(), subcategoryDto);
        });
    }

    @Test
    void testGetSubcategoryByIdNotPresent() {
        when(subcategoryDao.findByIdAndIsDeletedFalse(subcategoryDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            subcategoryService.retrieveSubcategoryById(subcategoryDto.getId());
        });
    }
    @Test
    void testGetSubcategoryById() {
        when(subcategoryDao.findByIdAndIsDeletedFalse(subcategoryDto.getId())).thenReturn(subcategory);
        SubcategoryDto response = subcategoryService.retrieveSubcategoryById(subcategoryDto.getId());
        assertEquals(response.getId(), subcategoryDto.getId());
    }
}
