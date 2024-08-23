package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.BrandDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandDao brandDao;

    public BrandDto addBrand (BrandDto brandDto) {
        if (brandDao.existsByName(brandDto.getName())) {
            throw new MyException("Brand name already present : " + brandDto.getName());
        }
        return BrandMapper.convertEntityToDto(brandDao.save(BrandMapper.convertDtoToEntity(brandDto)));
    }
}
