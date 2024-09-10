package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.CategoryDao;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    CategoryDto categoryDto;
    Category category;

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
    }

    @Test
    void testAddCategory() {
        when(categoryDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(false);
        when(categoryDao.save(any(Category.class))).thenReturn(category);
        CategoryDto createdCategory = categoryService.addCategory(categoryDto);
        assertNotNull(createdCategory);
        assertEquals(categoryDto.getName(), createdCategory.getName());
        verify(categoryDao, times(1)).save(any(Category.class));
    }

    @Test
    void testAddCategoryNegative() {
        when(categoryDao.existsByNameAndIsDeletedFalse(anyString())).thenThrow(new NullPointerException("database error"));
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            categoryService.addCategory(categoryDto);
        });
        assertTrue(exception.getMessage().contains("database error"));
    }

    @Test
    void testAddCategoryException() {
        when(categoryDao.existsByNameAndIsDeletedFalse(anyString())).thenReturn(true);
        assertThrows(MyException.class, () -> categoryService.addCategory(categoryDto));
    }

    @Test
    void testDeleteCategory() {
        when(categoryDao.findByIdAndIsDeletedFalse(category.getId())).thenReturn(category);
        boolean flag = categoryService.deleteCategory(categoryDto.getId());
        assertTrue(flag);
    }

    @Test
    void testDeletedCategory() {
        when(categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(category);
        category.setDeleted(true);
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(categoryDto.getId());
        });
    }

    @Test
    void testGetAllCategories() {
        when(categoryDao.findByIsDeletedFalse()).thenReturn(List.of(category));
        List<CategoryDto> categoryDtos = categoryService.retrieveAllCategory();
        assertNotNull(categoryDtos);
    }

    @Test
    void testGetAllCategoriesEmpty() {
        List<Category> categories = new ArrayList<>();
        when(categoryDao.findByIsDeletedFalse()).thenReturn(categories);
        assertThrows(ResourceNotFoundException.class, () ->{
            categoryService.retrieveAllCategory();
        });
    }

    @Test
    void testUpdateBrand() {
        when(categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(category);
        category.setName("samsung");
        when(categoryDao.saveAndFlush(any(Category.class))).thenReturn(category);
        CategoryDto response = categoryService.updateCategory(anyLong(), categoryDto);
        assertNotNull(response);
        assertEquals(response.getName(), category.getName());
        verify(categoryDao, times(1)).saveAndFlush(any(Category.class));
    }

    @Test
    void getCategoryByIdNotPresent() {
        when(categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.retrieveCategoryById(categoryDto.getId());
        });
    }
    @Test
    void getCategoryById() {
        when(categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(category);
        CategoryDto response = categoryService.retrieveCategoryById(categoryDto.getId());
        assertEquals(response.getId(), categoryDto.getId());
    }
}
