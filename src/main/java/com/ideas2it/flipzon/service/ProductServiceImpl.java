package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.mapper.BrandMapper;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        BrandDto brandDto = brandService.retrieveBrandById(productDto.getBrandId());
        CategoryDto categoryDto = categoryService.retrieveCategoryById(productDto.getCategoryId());
        Product product = ProductMapper.convertDtoToEntity(productDto);
        product.setBrand(BrandMapper.convertDtoToEntity(brandDto));
        product.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
        product.setSubcategory(CategoryMapper.convertDtoToEntity(categoryDto).getSubcategory());
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
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
        if (product == null) {
            throw new MyException("Product not present with id: " + productDto.getId());
        }
        if (productDto.getBrandId() != 0) {
            BrandDto brandDto = brandService.retrieveBrandById(productDto.getBrandId());
            if (brandDto == null) {
                throw new MyException("Brand not found with id: " + productDto.getBrandId());
            }
            product.setBrand(BrandMapper.convertDtoToEntity(brandDto));
        } else {
            product.setBrand(null); // Handle case where brand is not set
        }

        if (productDto.getCategoryId() != 0) {
            CategoryDto categoryDto = categoryService.retrieveCategoryById(productDto.getCategoryId());
            if (categoryDto == null) {
                throw new MyException("Category not found with id: " + productDto.getCategoryId());
            }
            product.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
            product.setSubcategory(categoryDto.getSubcategory()); // Use the DTO directly
        } else {
            product.setCategory(null); // Handle case where category is not set
            product.setSubcategory(null); // Ensure subcategory is cleared if no category is set
        }
        product = productDao.saveAndFlush(product);
        return ProductMapper.convertEntityToDto(product);
    }


    @Override
    public ProductDto retrieveProductById(long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            throw new MyException("Product not present in this Id :" + id);
        }
        return ProductMapper.convertEntityToDto(product);
    }
}
