package com.ideas2it.flipzon.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.dto.CartItemResponseDto;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.model.Brand;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.Category;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Subcategory;
import com.ideas2it.flipzon.service.CartService;
import com.ideas2it.flipzon.service.ProductService;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartController cartController;

    private Cart cart;
    private CartDto cartDto;
    private CartResponseDto cartResponseDto;
    private ProductDto productDto;
    private List<ProductDto> productDtoList;
    private CartItemResponseDto cartItemResponseDto;

    @BeforeEach
    void setUp() {
        productDto = ProductDto.builder()
                .id(1L)
                .name("Hamam")
                .price(100.0)
                .categoryId(1L)
                .brandId(1L)
                .subcategoryId(1L)
                .build();
        cartDto = CartDto.builder()
                .productId(1L)
                .quantity(2)
                .cartId(1L)
                .build();
        cart = Cart.builder()
                .id(1L)
                .totalPrice(200)
                .customer(Customer.builder()
                        .id(1L)
                        .build())
                .build();

        cartItemResponseDto = CartItemResponseDto.builder()
                .productName(productDto.getName())
                .totalPrice(100.00)
                .quantity(2)
                .build();

        cartResponseDto = CartResponseDto.builder()
                .customerId(1L)
                .totalPrice(200.0)
                .cartItemResponseDtos(Set.of(cartItemResponseDto))
                .build();



        productDtoList = Arrays.asList(productDto);
    }

    @Test
    void testAddCart() {
        when(cartService.addProductToCart(1L, cartDto)).thenReturn(cartResponseDto);
        ResponseEntity<CartResponseDto> response = cartController.addProductToCart(1L,cartDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResponseDto, response.getBody());
        verify(cartService, times(1)).addProductToCart(1L, cartDto);
    }

    @Test
    void testGetProductsFromCart_Success() {
        when(cartService.getProductsFromCart(anyLong())).thenReturn(cartResponseDto);
        ResponseEntity<CartResponseDto> response = cartController.getProductsFromCart(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResponseDto, response.getBody());
    }

    @Test
    void testRemoveProductFromCart_Success() {
        when(cartService.removeProductFromCart(anyLong(), anyLong())).thenReturn(cartResponseDto);
        ResponseEntity<CartResponseDto> response = cartController.removeProductFromCart(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResponseDto, response.getBody());
    }

    @Test
    void testUpdateProductQuantity_Success() {
        when(cartService.updateProductQuantity(1L, any(CartDto.class))).thenReturn(cartResponseDto);
        ResponseEntity<CartResponseDto> response = cartController.updateProductQuantity(1L, cartDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartResponseDto, response.getBody());
    }

    @Test
    void testGetAllProducts_Success() {
        when(productService.retrieveAllProduct()).thenReturn(productDtoList);
        ResponseEntity<List<ProductDto>> response = cartController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtoList, response.getBody());
    }

    @Test
    void testGetAllProductsByBrandId_Success() {
        when(productService.retrieveAllProductByBrandId(anyLong())).thenReturn(productDtoList);
        ResponseEntity<List<ProductDto>> response = cartController.getAllProductsByBrandId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtoList, response.getBody());
    }

    @Test
    void testGetAllProductsByCategoryId_Success() {
        when(productService.retrieveAllProductByCategoryId(anyLong())).thenReturn(productDtoList);
        ResponseEntity<List<ProductDto>> response = cartController.getAllProductsByCategoryId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtoList, response.getBody());
    }
}