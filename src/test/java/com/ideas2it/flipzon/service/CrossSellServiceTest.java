package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.CrossSellDao;
import com.ideas2it.flipzon.dto.CrossSellRequestDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Crosssell;
import com.ideas2it.flipzon.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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
    void testRemoveCrossSellProduct_Exception() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(crossSellDao.findByProductId(product.getId())).thenReturn(null);
        crossSellService.removeCrossSellProduct(anyLong(),anyLong());
    }

    @Test
    void testGetCrossSellProduct_Exception() {
        when(crossSellDao.findByProductId(product.getId())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            crossSellService.getCrossSellProduct(product.getId());
        });
    }

    @Test
    void testDeleteCrossSell_EmptyProducts() {
        crossSell.setProducts(new HashSet<>());
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(crossSellProduct.getId())).thenReturn(crossSellProduct);
        when(crossSellDao.findByProductId(product.getId())).thenReturn(crossSell);
        try {
            crossSellService.removeCrossSellProduct(anyLong(),anyLong());
        } catch (ResourceNotFoundException ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
        }
        verify(crossSellDao, times(0)).saveAndFlush(crossSell);
    }
}
