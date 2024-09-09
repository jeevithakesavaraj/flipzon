package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.flipzon.dao.UpsellDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.UpsellDto;
import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Upsell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UpsellServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private UpsellDao upsellDao;

    @InjectMocks
    private UpsellServiceImpl upsellService;

    private Product product;
    private ProductDto productDto;
    private Product upsellProduct;
    private Upsell upsell;
    private UpsellDto upsellDto;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Hamam")
                .price(100.0)
                .description("good soap")
                .build();

        upsellProduct = Product.builder()
                .id(2L)
                .name("Pears")
                .price(120.0)
                .description("Milky soap")
                .build();
        upsell = Upsell.builder()
                .id(1L)
                .product(product)
                .products(new HashSet<>(Set.of(upsellProduct)))
                .build();

        upsellDto = UpsellDto.builder()
                .productId(product.getId())
                .upsellProductId(upsellProduct.getId())
                .build();

        productDto = ProductDto.builder()
                .id(1L)
                .name("Hamam")
                .brandId(1L)
                .build();

    }

    @Test
    void testAddUpsell_NewUpsell() {
        when(productService.retrieveProductById(product.getId())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> {
            upsellService.addUpsell(upsellDto);
        });

    }

    @Test
    void testAddUpsell_ExistingUpsell() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(upsell);
        when(upsellDao.saveAndFlush(any(Upsell.class))).thenReturn(upsell);

        UpsellResponseDto response = upsellService.addUpsell(upsellDto);

        assertEquals("Hamam", response.getProductName());
        assertEquals(100.0, response.getPrice());
        assertEquals(1, response.getProductDtos().size());

        verify(upsellDao, times(1)).saveAndFlush(any(Upsell.class));
    }

    @Test
    void testDeleteUpsell_Success() {
        when(productService.getProductById(product.getId())).thenReturn(product);
        when(productService.getProductById(upsellProduct.getId())).thenReturn(upsellProduct);
        when(upsellDao.findByProductId(product.getId())).thenReturn(upsell);

        UpsellResponseDto response = upsellService.deleteUpsell(upsellDto);

        assertEquals("Pears", response.getProductName());
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
            upsellService.deleteUpsell(upsellDto);
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
            upsellService.deleteUpsell(upsellDto);
        } catch (ResourceNotFoundException ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
        }

        verify(upsellDao, times(0)).saveAndFlush(upsell);
    }
}
