package com.ideas2it.flipzon.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    CartDto cartDto;
    Cart cart;
    Category category;
    Subcategory subcategory;
    Product product;
    CartItem cartItem;

    @BeforeEach
    public void setUp() {
        cartDto = CartDto.builder().cartId(1L).productId(1L).quantity(2).build();
        cart = CartMapper.convertDtoToEntity(cartDto);
        category = Category.builder().id(1L).build();
        subcategory = Subcategory.builder().id(1L).build();
        product = Product.builder().id(1L).price(100.0).brand(Brand.builder().id(1L).build())
                .category(category)
                .subcategory(subcategory)
                .build();
        cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .quantity(cartDto.getQuantity())
                .price(product.getPrice())
                .build();
    }

    @Test
    void testAddProductToCartItem_NewItem() {
        when(cartItemDao.findByProductId(1L)).thenReturn(cartItem);
        when(productService.retrieveProductById(1L)).thenReturn(ProductMapper.convertEntityToDto(product));
        when(cartItemDao.saveAndFlush(any(CartItem.class))).thenAnswer(i -> i.getArguments()[0]);
        CartItem cartItem = cartItemService.addProductToCartItem(cart, cartDto);
        assertNotNull(cartItem);
        assertEquals(cart, cartItem.getCart());
        assertEquals(2, cartItem.getQuantity());
        assertEquals(100.0, cartItem.getPrice());
        assertEquals(200.0, cartItem.getTotalPrice());
        verify(cartItemDao).saveAndFlush(cartItem);
    }

    @Test
    void testAddProductToCartItem() {
        when(cartItemDao.findByProductId(1L)).thenReturn(null);
        when(productService.retrieveProductById(1L)).thenReturn(ProductMapper.convertEntityToDto(product));
        when(cartItemDao.saveAndFlush(any(CartItem.class))).thenAnswer(i -> i.getArguments()[0]);
        CartItem cartItem = cartItemService.addProductToCartItem(cart, cartDto);
        assertEquals(cart, cartItem.getCart());
        assertEquals(2, cartItem.getQuantity());
        assertEquals(100.0, cartItem.getPrice());
        assertEquals(200.0, cartItem.getTotalPrice());
        verify(cartItemDao).saveAndFlush(cartItem);
    }

    @Test
    void testUpdateProductToCartItem() {
        when(cartItemDao.saveAndFlush(cartItem)).thenReturn(cartItem);
        CartItem response = cartItemService.updateProductToCartItem(cartItem, cartDto);
        assertEquals(cart, response.getCart());
        verify(cartItemDao).saveAndFlush(cartItem);
    }
}
