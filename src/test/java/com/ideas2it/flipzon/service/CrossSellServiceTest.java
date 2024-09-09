package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.CrossSellDao;
import com.ideas2it.flipzon.dto.*;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrossSellServiceTest {
    @Mock
    private CrossSellDao crossSellDao;
    @Mock
    private ProductService productService;

    @InjectMocks
    private CrossSellServiceImpl crossSellService;

    private Product product;
    private Product crossSellProduct;
    private Crosssell crossSell;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("iphone")
                .price(70000)
                .build();
        crossSellProduct = Product.builder()
                .id(2L)
                .name("ipad")
                .price(80000)
                .build();
        crossSell = Crosssell.builder()
                .product(product)
                .products(Set.of(crossSellProduct))
                .build();
    }

    @Test
    void testAddCrossSellProduct() {
    }

    @Test
    void testRemoveCrossSellProduct_Exception() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(crossSellDao.findByProductId(product.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            crossSellService.removeCrossSellProduct(CrossSellRequestDto.builder()
                    .productId(product.getId())
                    .crossSellProductId(crossSellProduct.getId())
                    .build());
        });
    }

    @Test
    void testGetCrossSellProduct() {
        when(crossSellDao.findByProductId(product.getId())).thenReturn(crossSell);
        CrossSellResponseDto response = crossSellService.getCrossSellProduct(product.getId());
        assertEquals(response.getProductName(), product.getName());
    }

    @Test
    void testGetCrossSellProduct_Exception() {
        when(crossSellDao.findByProductId(product.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            crossSellService.getCrossSellProduct(product.getId());
        });
    }

//    @Test
//    void testDeleteCrossSell_Success() {
//        when(productService.getProductById(product.getId())).thenReturn(product);
//        when(productService.getProductById(crossSellProduct.getId())).thenReturn(crossSellProduct);
//        when(crossSellDao.findByProductId(product.getId())).thenReturn(crossSell);
//        CrossSellResponseDto response = crossSellService.removeCrossSellProduct();
//
//    }

    @Test
    void testDeleteCrossSell_EmptyProducts() {
        crossSell.setProducts(new HashSet<>());
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(crossSellProduct.getId())).thenReturn(crossSellProduct);
        when(crossSellDao.findByProductId(product.getId())).thenReturn(crossSell);
        try {
            crossSellService.removeCrossSellProduct(CrossSellRequestDto.builder()
                    .crossSellProductId(crossSellProduct.getId())
                    .productId(product.getId()).build());
        } catch (ResourceNotFoundException ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
        }
        verify(crossSellDao, times(0)).saveAndFlush(crossSell);
    }
}
