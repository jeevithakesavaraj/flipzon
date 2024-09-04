package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(CategoryServiceImpl.class);
    private static final LocalDate currentDate = LocalDate.now();

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (categoryDao.existsByNameAndIsDeletedFalse(categoryDto.getName())) {
            LOGGER.warn("Category already exist in this name {}", categoryDto.getName());
            throw new MyException("Category name already present : " + categoryDto.getName());
        }
        LOGGER.info("Category added  successfully");
        return CategoryMapper.convertEntityToDto(categoryDao.save(CategoryMapper.convertDtoToEntity(categoryDto)));
    }

    @Override
    public boolean deleteCategory(long id) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(id);
        if (category.isDeleted()) {
            LOGGER.warn("Category not found in this id {}", id);
            throw new ResourceNotFoundException("Category", "Category ID", id);
        }
        category.setDeleted(true);
        categoryDao.saveAndFlush(category);
        LOGGER.info("Category deleted successfully");
        return true;
    }

    @Override
    public List<CategoryDto> retrieveAllCategory() {
        List<CategoryDto> categoryDtos =  categoryDao.findByIsDeletedFalse().stream()
                .map(CategoryMapper::convertEntityToDto).toList();
        if (categoryDtos.isEmpty()) {
            LOGGER.warn("No Categories found ");
            throw new ResourceNotFoundException("Category", "");
        }
        LOGGER.info("Get All categories");
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(categoryDto.getId());
        if (null == category) {
            LOGGER.warn("Category not Available in this id {}", categoryDto.getId());
            throw new ResourceNotFoundException("Category", "Category ID", categoryDto.getId());
        }
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        category.setModifiedDate(modifiedDate);
        LOGGER.info("Category updated");
        return CategoryMapper.convertEntityToDto(categoryDao.saveAndFlush(CategoryMapper.convertDtoToEntity(categoryDto)));
    }

    @Override
    public CategoryDto retrieveCategoryById(long id) {
        Category category = categoryDao.findByIdAndIsDeletedFalse(id);
        if (null == category) {
            LOGGER.warn("Category not found in this id {}", id);
            throw new ResourceNotFoundException("Category", "Category ID", id);
        }
        LOGGER.info("Get category by its id {}", id);
        return CategoryMapper.convertEntityToDto(category);
    }
}
