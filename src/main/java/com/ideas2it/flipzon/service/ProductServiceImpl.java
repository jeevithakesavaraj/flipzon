package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductDto addProduct (ProductDto productDto) {
        return ProductMapper.convertEntityToDto(productDao.save(ProductMapper.convertDtoToEntity(productDto)));
    }

    @Override
    public void deleteProduct(long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (product.isDeleted()) {
            throw new MyException("Product Already Deleted : " + id);
        }
        product.setDeleted(true);
        productDao.saveAndFlush(product);
    }

    @Override
    public List<ProductDto> retrieveAllProduct() {
        return productDao.findByIsDeletedFalse().stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productDao.findByIdAndIsDeletedFalse(productDto.getId());
        if (null != product) {
            throw new MyException("Product not present in this Id :" + productDto.getId());
        }
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(ProductMapper.convertDtoToEntity(productDto)));
    }

    @Override
    public ProductDto retrieveProductById(long id) {
        Product product = productDao.findByIdAndIsDeletedTrue(id);
        if (null != product) {
            throw new MyException("Product not present in this Id :" + id);
        }
        return ProductMapper.convertEntityToDto(productDao.findByIdAndIsDeletedFalse(id));
    }
}
