package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.model.Brand;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Category;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Subcategory;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private CartDto cartDto;
    private Customer customer;
    private Set<CartItem> cartItems = new HashSet<>();

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .build();
        Category category = Category.builder().id(1L).build();
        Subcategory subcategory = Subcategory.builder().id(1L).build();
        Product product = Product.builder().id(1L).price(100.0).brand(Brand.builder().id(1L).build())
                .category(category)
                .subcategory(subcategory)
                .build();
        cartItems.add(CartItem.builder().product(product).build());
        cart = Cart.builder()
                .id(1L)
                .customer(customer)
                .cartItems(cartItems)
                .build();
        cartDto = CartMapper.convertEntityToDto(cart);
    }

    @Test
    void testGetProductsFromCart() {
        when(cartDao.findByCustomerId(1L)).thenReturn(cart);
        CartResponseDto cartResponseDto = cartService.getProductsFromCart(1L);
        assertEquals(1L, cartResponseDto.getCustomerId());
    }

}

