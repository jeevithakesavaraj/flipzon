package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.User;
import com.ideas2it.flipzon.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    User user;

    Customer customer;

    WishlistResponseDto wishlistResponseDto;

    Product product;

    Product product2;

    Wishlist wishlist;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Gowtham")
                .email("gowtham@gmail.com")
                .build();

        customer = Customer.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Hamam")
                .price(100.0)
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("LifeBoy")
                .price(100.0)
                .build();

        wishlistResponseDto = WishlistResponseDto.builder()
                .products(Set.of("Hamam"))
                .customerName("Hermoine")
                .build();

        wishlist = Wishlist.builder()
                .customer(customer)
                .products(Set.of(product))
                .id(1L)
                .build();

        productDto = ProductDto.builder()
                .id(1L)
                .name("Hamam")
                .brandId(1L)
                .build();
    }

    @Test
    void testAddProductToWishlist_NewWishlist() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(productService.retrieveProductById(1L)).thenReturn(ProductDto.builder()
                        .name(product.getName())
                .build());
        when(wishlistDao.existsByCustomerId(1L)).thenReturn(false);
        when(wishlistDao.save(any(Wishlist.class))).thenReturn(wishlist);
        WishlistResponseDto response = wishlistService.addProductToWishlist(1L, 1L);
        assertEquals(user.getName(), response.getCustomerName());
        assertTrue(response.getProducts().contains("Hamam"));
        verify(wishlistDao, times(1)).save(any(Wishlist.class));
    }


    @Test
    void testGetProductsFromWishlist_Success() {
        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        WishlistResponseDto response = wishlistService.getProductsFromWishlist(1L);
        assertEquals("Gowtham", response.getCustomerName());
        assertTrue(response.getProducts().contains("Hamam"));
    }

    @Test
    void getProductsFromWishlistException() {
        when(wishlistDao.findByCustomerId(customer.getId())).thenThrow(new NullPointerException("No customer"));
        assertThrows(NullPointerException.class, () -> wishlistService.getProductsFromWishlist(customer.getId()));
    }

    @Test
    void getProductsFromWishlistResourceNotFoundException() {
        when(wishlistDao.findByCustomerId(customer.getId())).thenThrow(new ResourceNotFoundException());
        assertThrows(ResourceNotFoundException.class, () -> wishlistService.getProductsFromWishlist(customer.getId()));
    }

    @Test
    void removeProductFromWishlist() {
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);
        when(productService.retrieveProductById(anyLong())).thenReturn(productDto);
        when(wishlistDao.findByCustomerId(anyLong())).thenReturn(wishlist);
        when(wishlistDao.saveAndFlush(wishlist)).thenReturn(wishlist);
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);
        WishlistResponseDto response = wishlistService.removeProductFromWishlist(1L, 1L);
        assertEquals(wishlistResponseDto, response);
    }
}
