package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dto.*;
import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.mapper.*;
import com.ideas2it.flipzon.model.Stock;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
    private static final LocalDate currentDate = LocalDate.now();

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private StockService stockService;

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
        List<Product> products = productDao.findByIsDeletedFalse();
        if (null == products) {
            throw new ResourceNotFoundException();
        }
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
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setBrand(BrandMapper.convertDtoToEntity(brandDto));
        product.setCategory(CategoryMapper.convertDtoToEntity(categoryDto));
        product.setSubcategory(SubcategoryMapper.convertDtoToEntity(subcategoryDto));
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(ProductMapper.convertDtoToEntity(productDto)));
    }

    @Override
    public ProductDto updateProductPrice(ProductDto productDto) {
        Product product = productDao.findByIdAndIsDeletedFalse(productDto.getId());
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", productDto.getId());
        }
        product.setPrice(productDto.getPrice());
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        product.setModifiedDate(modifiedDate);
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
    }

    @SneakyThrows
    @Override
    public ProductDto retrieveProductById(Long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", id);
        } else {
            Stock stock = StockMapper.convertDtoToEntity(stockService.retrieveStockByProductId(product.getId()));
            if (stock.getCurrentQuantity() == 0) {
                throw new OutOfStock("Out of Stocks");
            }
        }
        return ProductMapper.convertEntityToDto(product);
    }
    @Override
    public Product getProductById(Long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            throw new ResourceNotFoundException("Product", "Product ID", id);
        }
        return productDao.findByIdAndIsDeletedFalse(id);
    }

    @Override
    public List<ProductDto> retrieveAllProductByBrandId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (null == products) {
            throw new ResourceNotFoundException("Products", "BrandId", id);
        }
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getBrand().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> retrieveAllProductByCategoryId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (null == products) {
            throw new ResourceNotFoundException("Products", "CategoryId", id);
        }
        return productDao.findByCategoryIdAndIsDeletedFalse(id).stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> retrieveAllProductBySubcategoryId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (null == products) {
            throw new ResourceNotFoundException("Products", "SubcategoryId", id);
        }
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getSubcategory().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
