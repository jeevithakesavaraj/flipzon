package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.flipzon.dao.UpsellDao;
import com.ideas2it.flipzon.dto.UpsellDto;
import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Upsell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UpsellServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private UpsellDao upsellDao;

    @InjectMocks
    private UpsellServiceImpl upsellServiceImpl;

    private Product product;
    private Product upsellProduct;
    private Upsell upsell;
    private UpsellDto upsellDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create the product and upsellProduct using the builder pattern
        product = Product.builder()
                .id(1L)
                .name("Product 1")
                .price(100.0)
                .build();

        upsellProduct = Product.builder()
                .id(2L)
                .name("Upsell Product 1")
                .price(120.0)
                .build();

        upsellDto = UpsellDto.builder()
                .productId(product.getId())
                .upsellProductId(upsellProduct.getId())
                .build();

        // Create Upsell instance
        upsell = Upsell.builder()
                .id(1L)
                .product(product)
                .products(new HashSet<>(Set.of(upsellProduct)))
                .build();
    }

    @Test
    void testAddUpsell_NewUpsell() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(null);
        when(upsellDao.saveAndFlush(any(Upsell.class))).thenReturn(upsell);

        UpsellResponseDto response = upsellServiceImpl.addUpsell(upsellDto);

        assertEquals("Product 1", response.getProductName());
        assertEquals(100.0, response.getPrice());
        assertEquals(1, response.getProductDtos().size());

        verify(upsellDao, times(1)).saveAndFlush(any(Upsell.class));
    }

    @Test
    void testAddUpsell_ExistingUpsell() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(upsell);
        when(upsellDao.saveAndFlush(any(Upsell.class))).thenReturn(upsell);

        UpsellResponseDto response = upsellServiceImpl.addUpsell(upsellDto);

        assertEquals("Product 1", response.getProductName());
        assertEquals(100.0, response.getPrice());
        assertEquals(1, response.getProductDtos().size());

        verify(upsellDao, times(1)).saveAndFlush(any(Upsell.class));
    }

    @Test
    void testDeleteUpsell_Success() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(upsell);

        UpsellResponseDto response = upsellServiceImpl.deleteUpsell(upsellDto);

        assertEquals("Upsell Product 1", response.getProductName());
        assertEquals(120.0, response.getPrice());
        assertEquals(0, response.getProductDtos().size());

        verify(upsellDao, times(1)).saveAndFlush(upsell);
    }

    @Test
    void testDeleteUpsell_ResourceNotFound() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(null);

        try {
            upsellServiceImpl.deleteUpsell(upsellDto);
        } catch (ResourceNotFoundException ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
        }

        verify(upsellDao, times(0)).saveAndFlush(upsell);
    }

    @Test
    void testDeleteUpsell_EmptyProducts() {
        upsell.setProducts(new HashSet<>());

        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(upsell);

        try {
            upsellServiceImpl.deleteUpsell(upsellDto);
        } catch (ResourceNotFoundException ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
        }

        verify(upsellDao, times(0)).saveAndFlush(upsell);
    }
}
//import java.util.Set;
//
//import com.ideas2it.flipzon.dao.UpsellDao;
//import com.ideas2it.flipzon.dto.UpsellDto;
//import com.ideas2it.flipzon.dto.UpsellResponseDto;
//import com.ideas2it.flipzon.model.Product;
//import com.ideas2it.flipzon.model.Upsell;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class UpsellServiceTest {
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private UpsellDao upsellDao;
//
//    @InjectMocks
//    private UpsellServiceImpl upsellService;
//
//    private Product product;
//    private Product upsellProduct;
//    private Upsell upsell;
//    private UpsellDto upsellDto;
//
//    @BeforeEach
//    void setUp() {
//        product = Product.builder()
//                .id(1L)
//                .name("Hamam")
//                .price(100.00)
//                .build();
//
//        upsellProduct = Product.builder()
//                .id(2L)
//                .name("Lux")
//                .price(100.00)
//                .build();
//
//        upsellDto = UpsellDto.builder()
//                .productId(product.getId())
//                .upsellProductId(upsellProduct.getId())
//                .build();
//
//        upsell = Upsell.builder()
//                .id(1L)
//                .product(product)
//                .products(Set.of(upsellProduct))
//                .build();
//    }
//
//    @Test
//    void testAddUpsell() {
//        when(productService.getProductById(1L)).thenReturn(product);
//        when(productService.getProductById(2L)).thenReturn(upsellProduct);
//        when(upsellDao.findByProductId(1L)).thenReturn(null);
//        when(upsellDao.saveAndFlush(any(Upsell.class))).thenReturn(upsell);
//        UpsellResponseDto response = upsellService.addUpsell(upsellDto);
//        assertEquals(product.getName(), response.getProductName());
//        verify(upsellDao, times(1)).saveAndFlush(any(Upsell.class));
//    }
//}
