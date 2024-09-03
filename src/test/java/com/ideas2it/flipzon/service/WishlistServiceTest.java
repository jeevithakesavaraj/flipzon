package com.ideas2it.flipzon.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.Wishlist;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistDao wishlistDao;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    private Customer customer;
    private Product product;
    private Wishlist wishlist;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        customer = Customer.builder()
                .id(1L)
                .user(user)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Washing")
                .price(100.0)
                .build();

        wishlist = Wishlist.builder()
                .id(1L)
                .customer(customer)
                .products(new HashSet<>())
                .build();
    }

    @Test
    void testAddProductToWishlist_NewWishlist() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(productService.retrieveProductById(1L)).thenReturn(ProductDto.builder()
                        .name("Samsung A30s")
                .build());
        when(wishlistDao.existsByCustomerId(1L)).thenReturn(false);
        when(wishlistDao.save(any(Wishlist.class))).thenReturn(wishlist);

        WishlistResponseDto response = wishlistService.addProductToWishlist(1L, 1L);

        assertEquals("Test User", response.getCustomerName());
        assertTrue(response.getProducts().contains("Test Product"));

        verify(wishlistDao, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddProductToWishlist_ExistingWishlist() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(productService.retrieveProductById(1L)).thenReturn(ProductDto.builder()
                .name("Samsung A30s")
                .build());
        when(wishlistDao.existsByCustomerId(1L)).thenReturn(true);
        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
        wishlist.getProducts().add(product);
        when(wishlistDao.save(wishlist)).thenReturn(wishlist);

        WishlistResponseDto response = wishlistService.addProductToWishlist(1L, 1L);

        assertEquals("Test User", response.getCustomerName());
        assertTrue(response.getProducts().contains("Test Product"));

        verify(wishlistDao, times(1)).save(wishlist);
    }

    @Test
    void testGetProductsFromWishlist_Success() {
        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        wishlist.getProducts().add(product);
        WishlistResponseDto response = wishlistService.getProductsFromWishlist(1L);

        assertEquals("Test User", response.getCustomerName());
        assertTrue(response.getProducts().contains("Test Product"));
    }

    @Test
    void testGetProductsFromWishlist_WishlistNotFound() {
        when(wishlistDao.findByCustomerId(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            wishlistService.getProductsFromWishlist(1L);
        });

        assertEquals("Please add products to wishlist", exception.getMessage());
    }

    @Test
    void testRemoveProductFromWishlist_Success() {
        wishlist.getProducts().add(product);
        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        WishlistResponseDto response = wishlistService.removeProductFromWishlist(1L, 1L);

        assertEquals("Test User", response.getCustomerName());
        assertFalse(response.getProducts().contains("Test Product"));

        verify(wishlistDao, times(1)).saveAndFlush(wishlist);
    }
}