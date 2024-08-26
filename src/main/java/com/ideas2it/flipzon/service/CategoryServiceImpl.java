package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dao.CategoryDao;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public CategoryDto addCategory (CategoryDto categoryDto) {
        if (categoryDao.existsByNameAndIsDeletedFalse(categoryDto.getName())) {
            throw new MyException("Category name already present : " + categoryDto.getName());
        }
        return CategoryMapper.convertEntityToDto(categoryDao.save(CategoryMapper.convertDtoToEntity(categoryDto)));
    }

//    @Override
//    public CategoryDto addSubcategory(long parentId, CategoryDto categoryDto) {
//        Category parentCategory = categoryDao.findById(parentId)
//                .orElseThrow(() -> new MyException("Parent category not found"));
//
//        Category subcategory = CategoryMapper.convertDtoToEntity(categoryDto);
//        subcategory.setParent(parentCategory);
//        subcategory = categoryDao.saveAndFlush(subcategory);
//        parentCategory.getSubcategories().add(subcategory);
//        categoryDao.saveAndFlush(parentCategory);
//        return CategoryMapper.convertEntityToDto(subcategory);
//    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(id);
        if (category.isDeleted()) {
            throw new ResourceNotFoundException("Category", "Category ID", id);
        }
        category.setDeleted(true);
        categoryDao.saveAndFlush(category);
    }

    @Override
    public List<CategoryDto> retrieveAllCategory() {
        return categoryDao.findByIsDeletedFalse().stream()
                .map(CategoryMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId());
        if (null == category) {
            throw new ResourceNotFoundException("Category", "Category ID", categoryDto.getId());
        }
        return CategoryMapper.convertEntityToDto(categoryDao.saveAndFlush(CategoryMapper.convertDtoToEntity(categoryDto)));
    }

    @Override
    public CategoryDto retrieveCategoryById(long id) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(id);
        if (null == category) {
            throw new ResourceNotFoundException("Category", "Category ID", id);
        }
        return CategoryMapper.convertEntityToDto(category);
    }

//    @Override
//    public List<CategoryDto> retrieveSubcategoriesByCategoryId(long id) {
//        Category parentCategory = categoryDao.findById(id)
//                .orElseThrow(() -> new MyException("Category not found"));
//        return parentCategory.getSubcategories().stream()
//                .map(CategoryMapper::convertEntityToDto)
//                .collect(Collectors.toList());
//    }
}
