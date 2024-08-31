package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CategoryDao;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
    private static final LocalDate currentDate = LocalDate.now();

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (categoryDao.existsByNameAndIsDeletedFalse(categoryDto.getName())) {
            throw new MyException("Category name already present : " + categoryDto.getName());
        }
        return CategoryMapper.convertEntityToDto(categoryDao.save(CategoryMapper.convertDtoToEntity(categoryDto)));
    }

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
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        category.setModifiedDate(modifiedDate);
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
}
