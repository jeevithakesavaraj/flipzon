package com.ideas2it.flipzon.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CartItemServiceTest {

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProductToCartItem_NewItem() {
        CartDto cartDto = CartDto.builder().cartId(1L).productId(1L).quantity(2).build();
        Cart cart = CartMapper.convertDtoToEntity(cartDto);
        Category category = Category.builder().id(1L).build();
        Subcategory subcategory = Subcategory.builder().id(1L).build();
        Product product = Product.builder().id(1L).price(100.0).brand(Brand.builder().id(1L).build())
                .category(category)
                .subcategory(subcategory)
                .build();

        when(cartItemDao.findByProductId(1L)).thenReturn(null);
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

}
