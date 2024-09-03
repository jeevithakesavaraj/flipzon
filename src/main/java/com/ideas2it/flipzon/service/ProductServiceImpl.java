package com.ideas2it.flipzon.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.dto.BrandDto;
import com.ideas2it.flipzon.dto.CategoryDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.SubcategoryDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.mapper.BrandMapper;
import com.ideas2it.flipzon.mapper.CategoryMapper;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.mapper.StockMapper;
import com.ideas2it.flipzon.mapper.SubcategoryMapper;
import com.ideas2it.flipzon.model.Stock;
import com.ideas2it.flipzon.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

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
        LOGGER.info("Product added successfully");
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
    }

    @Override
    public boolean deleteProduct(long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            LOGGER.warn("Product not found in this id {}", id);
            throw new ResourceNotFoundException("Product", "Product ID", id);
        }
        product.setDeleted(true);
        productDao.saveAndFlush(product);
        LOGGER.info("Product deleted Successfully");
        return true;
    }

    @Override
    public List<ProductDto> retrieveAllProduct() {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (products.isEmpty()) {
            LOGGER.warn("Products not found in DataBase");
            throw new ResourceNotFoundException("No products found", "");
        }
        LOGGER.info("Get Products in Database");
        return productDao.findByIsDeletedFalse().stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productDao.findByIdAndIsDeletedFalse(productDto.getId());
        if (null == product) {
            LOGGER.warn("product not found in this id {}", productDto.getId());
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
        LOGGER.info("product details updated successfully");
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(ProductMapper.convertDtoToEntity(productDto)));
    }

    @Override
    public ProductDto updateProductPrice(ProductDto productDto) {
        Product product = productDao.findByIdAndIsDeletedFalse(productDto.getId());
        if (null == product) {
            LOGGER.warn("Product not found in this id {}", productDto.getId());
            throw new ResourceNotFoundException("Product", "Product ID", productDto.getId());
        }
        product.setPrice(productDto.getPrice());
        Date modifiedDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        product.setModifiedDate(modifiedDate);
        LOGGER.info("Product price updated successfully in this id {}", productDto.getId());
        return ProductMapper.convertEntityToDto(productDao.saveAndFlush(product));
    }

    @Override
    public ProductDto retrieveProductById(Long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            LOGGER.warn("Product not found in this id {}", id);
            throw new ResourceNotFoundException("Product", "Product ID", id);
        } else {
            Stock stock = StockMapper.convertDtoToEntity(stockService.retrieveStockByProductId(product.getId()));
            if (stock.getCurrentQuantity() == 0) {
                LOGGER.warn("No stocks available in this product id {}", id);
                throw new OutOfStock("Out of Stocks");
            }
        }
        LOGGER.info("Get product by its id {}", id);
        return ProductMapper.convertEntityToDto(product);
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productDao.findByIdAndIsDeletedFalse(id);
        if (null == product) {
            LOGGER.warn("Product not found in this id {}", id);
            throw new ResourceNotFoundException("Product", "Product ID", id);
        }
        LOGGER.info("Get product by its id {}", id);
        return productDao.findByIdAndIsDeletedFalse(id);
    }

    @Override
    public List<ProductDto> retrieveAllProductByBrandId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (products.isEmpty()) {
            LOGGER.warn("Product not found in this Brand id {}", id);
            throw new ResourceNotFoundException("Products", "BrandId", id);
        }
        LOGGER.info("Get products by this Brand id {}", id);
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getBrand().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> retrieveAllProductByCategoryId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (products.isEmpty()) {
            LOGGER.warn("Product not found in this category id {}", id);
            throw new ResourceNotFoundException("Products", "CategoryId", id);
        }
        LOGGER.info("Get products in this category id {}", id);
        return productDao.findByCategoryIdAndIsDeletedFalse(id).stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> retrieveAllProductBySubcategoryId(Long id) {
        List<Product> products = productDao.findByIsDeletedFalse();
        if (products.isEmpty()) {
            LOGGER.warn("Product not found in this Subcategory id {}", id);
            throw new ResourceNotFoundException("Products", "SubcategoryId", id);
        }
        LOGGER.info("Get Product in this subcategory id {}", id);
        return productDao.findByIsDeletedFalse().stream()
                .filter((product) -> Objects.equals(product.getSubcategory().getId(), id))
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
