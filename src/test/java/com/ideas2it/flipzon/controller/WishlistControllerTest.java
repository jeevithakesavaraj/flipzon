package com.ideas2it.flipzon.controller;

import java.util.Set;

import com.ideas2it.flipzon.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.User;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    WishlistResponseDto wishlistResponseDto;

    Customer customer;

    User user;

    Product product;

    @BeforeEach
    void setUp() {
        wishlistResponseDto = WishlistResponseDto.builder()
                .products(Set.of("Hamam"))
                .customerName("Hermoine")
                .build();

        customer = Customer.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .build();

        user = User.builder()
                .id(1L)
                .name("Hermoine")
                .email("hermoine@xampp.com")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Hamam")
                .price(100.0)
                .build();
    }

    @Test
    void addProductToWishlist() {
        when(wishlistService.addProductToWishlist(1L, 1L)).thenReturn(wishlistResponseDto);
        ResponseEntity<WishlistResponseDto> response = wishlistController.addProductToWishlist(1L,1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlistResponseDto, response.getBody());
        verify(wishlistService, times(1)).addProductToWishlist(1L, 1L);
    }

    @Test
    void getProductsFromWishlist() {
        when(wishlistService.getProductsFromWishlist(1L)).thenReturn(wishlistResponseDto);
        ResponseEntity<WishlistResponseDto> response = wishlistController.getProductsFromWishlist(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlistResponseDto, response.getBody());
        verify(wishlistService, times(1)).getProductsFromWishlist(1L);
    }

    @Test
    void removeProductFromWishlist() {
        when(wishlistService.removeProductFromWishlist(1L, 1L)).thenReturn(wishlistResponseDto);
        ResponseEntity<WishlistResponseDto> response = wishlistController.removeProductFromWishlist(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlistResponseDto, response.getBody());
        verify(wishlistService, times(1)).removeProductFromWishlist(1L, 1L);
    }
}
