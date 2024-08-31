package com.ideas2it.flipzon.service;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private static final LocalDate currentDate = LocalDate.now();
    @Autowired
    private BrandDao brandDao;

    @Override
    public BrandDto addBrand (BrandDto brandDto) {
        if (brandDao.existsByNameAndIsDeletedFalse(brandDto.getName())) {
            throw new MyException("Brand name already present : " + brandDto.getName());
        }
        return BrandMapper.convertEntityToDto(brandDao.save(BrandMapper.convertDtoToEntity(brandDto)));
    }

    @Override
    public void deleteBrand(long id) {
        Brand brand = brandDao.findByIdAndIsDeletedFalse(id);
        if (null == brand) {
            throw new ResourceNotFoundException("Brand", "BrandId", id);
        }
        brand.setDeleted(true);
        brandDao.saveAndFlush(brand);
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
            throw new ResourceNotFoundException("Brand", "BrandId", brandDto.getId());
        } else if (brandDao.existsByNameAndIsDeletedFalse(brandDto.getName())) {
            throw new MyException("Brand name already present : " + brandDto.getName());
        }
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        brand.setModifiedDate(modifiedDate);
        return BrandMapper.convertEntityToDto(brandDao.saveAndFlush(BrandMapper.convertDtoToEntity(brandDto)));
    }

    @Override
    public BrandDto retrieveBrandById(long id) {
        Brand brand = brandDao.findByIdAndIsDeletedFalse(id);
        if (null == brand) {
            throw new ResourceNotFoundException("Brand", "BrandId", id);
        }
        return BrandMapper.convertEntityToDto(brand);
    }
}
