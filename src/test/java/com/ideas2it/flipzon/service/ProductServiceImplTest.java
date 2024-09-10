package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.ProductDao;
import com.ideas2it.flipzon.dto.*;
import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;
    @Mock
    private BrandService brandService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private SubcategoryService subcategoryService;
    @Mock
    private StockService stockService;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;
    private Brand brand;
    private BrandDto brandDto;
    private Category category;
    private CategoryDto categoryDto;
    private Subcategory subcategory;
    private SubcategoryDto subcategoryDto;
    private Stock stock;
    private StockDto stockDto;
    @BeforeEach
    void setUp() {
        brand = Brand.builder()
                .id(1L)
                .name("apple")
                .isDeleted(false)
                .build();
        brandDto = BrandDto.builder()
                .id(1L)
                .name("apple")
                .build();

        category = Category.builder()
                .id(1L)
                .name("apple")
                .isDeleted(false)
                .build();
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("electronics")
                .build();
        subcategory = Subcategory.builder()
                .id(1L)
                .name("mobile")
                .isDeleted(false)
                .category(category)
                .build();
        subcategoryDto = SubcategoryDto.builder()
                .id(1L)
                .name("mobile")
                .categoryId(category.getId())
                .build();
        stock = Stock.builder()
                .id(1L)
                .initialQuantity(10)
                .currentQuantity(10)
                .build();
        stockDto = StockDto.builder()
                .id(1L)
                .initialQuantity(10)
                .currentQuantity(10)
                .build();
        product = Product.builder()
                .id(1L)
                .name("iphone")
                .price(70000)
                .brand(brand)
                .category(category)
                .subcategory(subcategory)
                .stock(stock)
                .build();
        productDto =ProductDto.builder()
                .id(1L)
                .name("iphone12")
                .price(70000)
                .brandId(brand.getId())
                .subcategoryId(subcategory.getId())
                .categoryId(category.getId())
                .build();
    }

    @Test
    void testAddProduct() {
        when(brandService.retrieveBrandById(brand.getId())).thenReturn(brandDto);
        when(categoryService.retrieveCategoryById(category.getId())).thenReturn(categoryDto);
        when(subcategoryService.retrieveSubcategoryById(subcategory.getId())).thenReturn(subcategoryDto);
        when(productDao.saveAndFlush(any(Product.class))).thenReturn(product);
        product.setBrand(brand);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        ProductDto response = productService.addProduct(productDto);
        assertEquals(response.getName(), product.getName());
    }

    @Test
    void testDeleteProduct() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(product);
        boolean flag = productService.deleteProduct(productDto.getId());
        assertTrue(flag);
    }

    @Test
    void testDeletedProduct() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class,() -> {
            productService.deleteProduct(productDto.getId());
        });
    }
    @Test
    void testGetAllCategories() {
        when(productDao.findByIsDeletedFalse()).thenReturn(List.of(product));
        List<ProductDto> productDtos = productService.retrieveAllProduct();
        assertNotNull(productDtos);
    }

    @Test
    void testGetAllCategoriesEmpty() {
        List<Product> products = new ArrayList<>();
        when(productDao.findByIsDeletedFalse()).thenReturn(products);
        assertThrows(ResourceNotFoundException.class, () ->{
            productService.retrieveAllProduct();
        });
    }

    @Test
    void testRetrieveProductById() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(product);
        when(stockService.retrieveStockByProductId(productDto.getId())).thenReturn(stockDto);
        ProductDto response = productService.retrieveProductById(productDto.getId());
        assertEquals(response.getId(), productDto.getId());
    }

    @Test
    void testRetrieveProductOutOfStock() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(product);
        when(stockService.retrieveStockByProductId(productDto.getId())).thenThrow(OutOfStock.class);
        assertThrows(OutOfStock.class, () -> {
            productService.retrieveProductById(productDto.getId());
        });
    }
    @Test
    void testRetrieveProductEmpty() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.retrieveProductById(productDto.getId());
        });
    }

    @Test
    void testGetProductById() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(product);
        Product response = productService.getProductById(productDto.getId());
        assertEquals(response.getId(), productDto.getId());
    }
    @Test
    void testGetProductEmpty() {
        when(productDao.findByIdAndIsDeletedFalse(productDto.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.retrieveProductById(productDto.getId());
        });
    }

    @Test
    void testGetAllProductsByBrandIdEmpty() {
        List<Product> products = new ArrayList<>();
        when(productDao.findByIsDeletedFalse()).thenReturn(products);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.retrieveAllProductByBrandId(brandDto.getId());
        });
    }
    @Test
    void testGetAllProductByBrandId() {
        when(productDao.findByIsDeletedFalse()).thenReturn(List.of(product));
        List<ProductDto> productDtos = productService.retrieveAllProductByBrandId(brandDto.getId());
        assertNotNull(productDtos);
    }
    @Test
    void testGetAllProductsByCategoryIdEmpty() {
        List<Product> products = new ArrayList<>();
        when(productDao.findByCategoryIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(products);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.retrieveAllProductByCategoryId(categoryDto.getId());
        });
    }

    @Test
    void testGetAllProductByCategoryId() {
        when(productDao.findByCategoryIdAndIsDeletedFalse(categoryDto.getId())).thenReturn(List.of(product));
        List<ProductDto> response = productService.retrieveAllProductByCategoryId(categoryDto.getId());
        assertNotNull(response);
    }

    @Test
    void testGetAllProductsBySubcategoryIdEmpty() {
        List<Product> products = new ArrayList<>();
        when(productDao.findByIsDeletedFalse()).thenReturn(products);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.retrieveAllProductBySubcategoryId(subcategoryDto.getId());
        });
    }
    @Test
    void testGetAllProductBySubcategoryId() {
        when(productDao.findByIsDeletedFalse()).thenReturn(List.of(product));
        List<ProductDto> productDtos = productService.retrieveAllProductByBrandId(subcategoryDto.getId());
        assertNotNull(productDtos);
    }

    @Test
    void testUpdatePriceEmptyProduct() {
        when(productDao.findByIdAndIsDeletedFalse(anyLong())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.updateProductPrice(1L, productDto.getPrice());
        });
    }

    @Test
    void testUpdatePrice() {
        when(productDao.findByIdAndIsDeletedFalse(anyLong())).thenReturn(product);
        product.setPrice(90000);
        when(productDao.saveAndFlush(product)).thenReturn(product);
        var response = productService.updateProductPrice(1L, productDto.getPrice());
        assertEquals(product.getPrice(), response.getPrice());
    }

    @Test
    void testUpdateProductEmpty() {
        when(productDao.findByIdAndIsDeletedFalse(anyLong())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, ()-> {
            productService.updateProduct(1L, productDto);
        });
    }

    @Test
    void testUpdateProduct() {
        when(productDao.findByIdAndIsDeletedFalse(anyLong())).thenReturn(product);
        when(brandService.retrieveBrandById(productDto.getBrandId())).thenReturn(brandDto);
        when(categoryService.retrieveCategoryById(productDto.getCategoryId())).thenReturn(categoryDto);
        when(subcategoryService.retrieveSubcategoryById(productDto.getSubcategoryId())).thenReturn(subcategoryDto);
        when(productDao.saveAndFlush(any(Product.class))).thenReturn(product);
        productDto.setPrice(20000);
        ProductDto response = productService.updateProduct(1L, productDto);
        assertEquals(productDto.getPrice(), response.getPrice());
    }
}
