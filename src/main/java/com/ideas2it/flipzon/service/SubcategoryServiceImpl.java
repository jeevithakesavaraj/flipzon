package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.SubcategoryDao;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.mapper.SubcategoryMapper;
import com.ideas2it.flipzon.model.Subcategory;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private static final Logger LOGGER = LogManager.getLogger(SubcategoryServiceImpl.class);

    private static final LocalDate currentDate = LocalDate.now();

    @Autowired
    private SubcategoryDao subcategoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public SubcategoryDto addSubcategory(SubcategoryDto subcategoryDto) {
        if (subcategoryDao.existsByNameAndIsDeletedFalse(subcategoryDto.getName())) {
            LOGGER.warn("Subcategory name already exist {}", subcategoryDto.getName());
            throw new MyException("Subcategory name already present : " + subcategoryDto.getName());
        }
        CategoryDto categoryDto = categoryService.retrieveCategoryById(subcategoryDto.getCategoryId());
        Subcategory subcategory = SubcategoryMapper.convertDtoToEntity(subcategoryDto);
        subcategory.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
        LOGGER.info("Subcategory added successfully");
        return SubcategoryMapper.convertEntityToDto(subcategoryDao.saveAndFlush(subcategory));
    }

    @Override
    public boolean deleteSubcategory(long id) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(id);
        if (subcategory.isDeleted()) {
            LOGGER.warn("Subcategory not found in this id{}", id);
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", id);
        }
        subcategory.setDeleted(true);
        subcategoryDao.saveAndFlush(subcategory);
        LOGGER.info("Subcategory deleted Successfully");
        return true;
    }

    @Override
    public List<SubcategoryDto> retrieveAllSubcategory() {
        List<SubcategoryDto> subcategoryDtos = subcategoryDao.findByIsDeletedFalse().stream()
                .map(SubcategoryMapper::convertEntityToDto).toList();
        if (subcategoryDtos.isEmpty()) {
            LOGGER.warn("Subcategories not fount in database");
            throw new ResourceNotFoundException("Subcategory", "");
        }
        LOGGER.info("Get all subcategories in database");
        return subcategoryDtos;
    }

    @Override
    public SubcategoryDto updateSubcategory(Long id, SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(id);
        if (null == subcategory) {
            LOGGER.warn("subcategory not found in this id {}", id);
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", id);
        }
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        subcategory.setModifiedDate(modifiedDate);
        LOGGER.info("subcategory updated successfully");
        subcategory = subcategoryDao.saveAndFlush(SubcategoryMapper.convertDtoToEntity(subcategoryDto));
        return SubcategoryMapper.convertEntityToDto(subcategory);
    }

    @Override
    public SubcategoryDto retrieveSubcategoryById(long id) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(id);
        if (null == subcategory) {
            LOGGER.warn("subcategory not found in this id {}", id);
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", id);
        }
        LOGGER.info("Get subcategory by its id {}", id);
        return SubcategoryMapper.convertEntityToDto(subcategory);
    }
}
