package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.BrandMapper;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.mapper.SubcategoryMapper;
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
    @Autowired
    private SubcategoryService subcategoryService;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        BrandDto brandDto = brandService.retrieveBrandById(productDto.getBrandId());
        CategoryDto categoryDto = categoryService.retrieveCategoryById(productDto.getCategoryId());
        SubcategoryDto subcategoryDto = subcategoryService.retrieveSubcategoryById(productDto.getSubcategoryId());
        Product product = ProductMapper.convertDtoToEntity(productDto);
        product.setBrand(BrandMapper.convertDtoToEntity(brandDto));
        product.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
        product.setSubcategory(SubcategoryMapper.convertDtoToEntity(subcategoryDto));
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
    }

    @Override
    public void deleteProduct(long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", id);
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
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", productDto.getId());
        }
        BrandDto brandDto = brandService.retrieveBrandById(productDto.getBrandId());
        CategoryDto categoryDto = categoryService.retrieveCategoryById(productDto.getCategoryId());
        SubcategoryDto subcategoryDto = subcategoryService.retrieveSubcategoryById(productDto.getSubcategoryId());
        product.setName(productDto.getName());
        product.setBrand(BrandMapper.convertDtoToEntity(brandDto));
        product.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
        product.setSubcategory(SubcategoryMapper.convertDtoToEntity(subcategoryDto));
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
    }

    @Override
    public ProductDto retrieveProductById(Long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", id);
        }
        return ProductMapper.convertEntityToDto(product);
    }

    @Override
    public List<ProductDto>retrieveAllProductByBrandId(Long id) {
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getBrand().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto>retrieveAllProductByCategoryId(Long id) {
        return productDao.findByCategoryIdAndIsDeletedFalse(id).stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto>retrieveAllProductBySubcategoryId(Long id) {
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getSubcategory().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
