package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.stream.Collectors;

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
public class SubcategoryServiceImpl implements SubcategoryService{

    @Autowired
    private SubcategoryDao subcategoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public SubcategoryDto addSubcategory (SubcategoryDto subcategoryDto) {
        if (subcategoryDao.existsByNameAndIsDeletedFalse(subcategoryDto.getName())) {
            throw new MyException("Subcategory name already present : " + subcategoryDto.getName());
        }
        CategoryDto categoryDto = categoryService.retrieveCategoryById(subcategoryDto.getCategoryId());
         Subcategory subcategory = subcategoryDao.save(SubcategoryMapper.convertDtoToEntity(subcategoryDto));
         subcategory.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
         return SubcategoryMapper.convertEntityToDto(subcategoryDao.saveAndFlush(subcategory));
    }

    @Override
    public void deleteSubcategory(long id) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(id);
        if (subcategory.isDeleted()) {
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", id);
        }
        subcategory.setDeleted(true);
        subcategoryDao.saveAndFlush(subcategory);
    }

    @Override
    public List<SubcategoryDto> retrieveAllSubcategory() {
        return subcategoryDao.findByIsDeletedFalse().stream()
                .map(SubcategoryMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDto updateSubcategory(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(subcategoryDto.getId());
        if (null == subcategory) {
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", subcategoryDto.getId());
        }
        return SubcategoryMapper.convertEntityToDto(subcategoryDao.saveAndFlush(SubcategoryMapper.convertDtoToEntity(subcategoryDto)));
    }

    @Override
    public SubcategoryDto retrieveSubcategoryById(long id) {
        Subcategory subcategory = subcategoryDao.findByIdAndIsDeletedFalse(id);
        if (null == subcategory) {
            throw new ResourceNotFoundException("Subcategory", "Subcategory ID", id);
        }
        return SubcategoryMapper.convertEntityToDto(subcategory);
    }
}
