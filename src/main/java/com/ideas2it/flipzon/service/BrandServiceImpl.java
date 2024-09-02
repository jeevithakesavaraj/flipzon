package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.BrandDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.BrandMapper;
import com.ideas2it.flipzon.model.Brand;

@Service
public class BrandServiceImpl implements BrandService{

    private static final Logger LOGGER = LogManager.getLogger(BrandServiceImpl.class);

    private static final LocalDate currentDate = LocalDate.now();

    @Autowired
    private BrandDao brandDao;

    @Override
    public BrandDto addBrand (BrandDto brandDto) {
        if (brandDao.existsByNameAndIsDeletedFalse(brandDto.getName())) {
            LOGGER.warn("Brand name already exist : {}", brandDto.getName());
            throw new MyException("Brand name already present : " + brandDto.getName());
        }
        LOGGER.info("Brand added successfully brand Name : {}", brandDto.getName());
        return BrandMapper.convertEntityToDto(brandDao.save(BrandMapper.convertDtoToEntity(brandDto)));
    }

    @Override
    public void deleteBrand(long id) {
        Brand brand = brandDao.findByIdAndIsDeletedFalse(id);
        if (null == brand) {
            LOGGER.warn("Brand not found in this id : {}", id);
            throw new ResourceNotFoundException("Brand", "BrandId", id);
        }
        brand.setDeleted(true);
        brandDao.saveAndFlush(brand);
        LOGGER.info("Brand deleted successfully by its id : {}", id);
    }

    @Override
    public List<BrandDto> retrieveAllBrand() {
        return brandDao.findByIsDeletedFalse().stream()
                .map(BrandMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto updateBrand(BrandDto brandDto) {
        Brand brand = brandDao.findByIdAndIsDeletedFalse(brandDto.getId());
        if (null == brand) {
            LOGGER.warn("Brand id not found in this id : {}", brandDto.getId());
            throw new ResourceNotFoundException("Brand", "BrandId", brandDto.getId());
        } else if (brandDao.existsByNameAndIsDeletedFalse(brandDto.getName())) {
            LOGGER.warn("Brand name already exist : {}", brandDto.getName());
            throw new MyException("Brand name already present : " + brandDto.getName());
        }
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        brand.setModifiedDate(modifiedDate);
        LOGGER.info("Brand updated successfully ");
        return BrandMapper.convertEntityToDto(brandDao.saveAndFlush(BrandMapper.convertDtoToEntity(brandDto)));
    }

    @Override
    public BrandDto retrieveBrandById(long id) {
        Brand brand = brandDao.findByIdAndIsDeletedFalse(id);
        if (null == brand) {
            LOGGER.warn("Brand id not found in this id : {}", id);
            throw new ResourceNotFoundException("Brand", "BrandId", id);
        }
        LOGGER.info("Brand retrieved by its id : {}", id);
        return BrandMapper.convertEntityToDto(brand);
    }
}
