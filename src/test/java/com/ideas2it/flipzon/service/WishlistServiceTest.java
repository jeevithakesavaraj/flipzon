package com.ideas2it.flipzon.service;

import org.mockito.Mock;

public class WishlistServiceTest {

}








//@ExtendWith(MockitoExtension.class)
//public class WishlistServiceTest {
//
//    @Mock
//    private WishlistDao wishlistDao;
//
//    @Mock
//    private ProductService productService;
//
//    @Mock
//    private CustomerService customerService;
//
//    @InjectMocks
//    private WishlistServiceImpl wishlistService;
//
//    private Customer customer;
//    private Product product;
//    private Wishlist wishlist;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = User.builder()
//                .id(1)
//                .name("Test User")
//                .email("test@example.com")
//                .build();
//
//        customer = Customer.builder()
//                .id(1)
//                .user(user)
//                .build();
//
//        product = Product.builder()
//                .id(1L)
//                .name("Washing")
//                .price(100.0)
//                .build();
//
//        wishlist = Wishlist.builder()
//                .id(1)
//                .customer(customer)
//                .products(new HashSet<Product>())
//                .build();
//    }
//
//    @Test
//    void testAddProductToWishlist_NewWishlist() {
//        when(customerService.getCustomerById(1L)).thenReturn(customer);
//        when(productService.retrieveProductById(1L)).thenReturn(ProductDto.builder()
//                        .name("Samsung A30s")
//                .build());
//        when(wishlistDao.existsByCustomerId(1L)).thenReturn(false);
//        when(wishlistDao.save(any(Wishlist.class))).thenReturn(wishlist);
//
//        WishlistResponseDto response = wishlistService.addProductToWishlist(1L, 1L);
//
//        assertEquals("Test User", response.getCustomerName());
//        assertTrue(response.getProducts().contains("Test Product"));
//
//        verify(wishlistDao, times(1)).save(any(Wishlist.class));
//    }
//
//    @Test
//    void testAddProductToWishlist_ExistingWishlist() {
//        when(customerService.getCustomerById(1L)).thenReturn(customer);
//        when(productService.retrieveProductById(1L)).thenReturn(ProductDto.builder()
//                .name("Samsung A30s")
//                .build());
//        when(wishlistDao.existsByCustomerId(1L)).thenReturn(true);
//        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
//        wishlist.getProducts().add(product);
//        when(wishlistDao.save(wishlist)).thenReturn(wishlist);
//
//        WishlistResponseDto response = wishlistService.addProductToWishlist(1L, 1L);
//
//        assertEquals("Test User", response.getCustomerName());
//        assertTrue(response.getProducts().contains("Test Product"));
//
//        verify(wishlistDao, times(1)).save(wishlist);
//    }
//
//    @Test
//    void testGetProductsFromWishlist_Success() {
//        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
//        when(customerService.getCustomerById(1L)).thenReturn(customer);
//
//        wishlist.getProducts().add(product);
//        WishlistResponseDto response = wishlistService.getProductsFromWishlist(1L);
//
//        assertEquals("Test User", response.getCustomerName());
//        assertTrue(response.getProducts().contains("Test Product"));
//    }
//
//    @Test
//    void testGetProductsFromWishlist_WishlistNotFound() {
//        when(wishlistDao.findByCustomerId(1L)).thenReturn(null);
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            wishlistService.getProductsFromWishlist(1L);
//        });
//
//        assertEquals("Please add products to wishlist", exception.getMessage());
//    }
//
//    @Test
//    void testRemoveProductFromWishlist_Success() {
//        wishlist.getProducts().add(product);
//        when(wishlistDao.findByCustomerId(1L)).thenReturn(wishlist);
//        when(customerService.getCustomerById(1L)).thenReturn(customer);
//
//        WishlistResponseDto response = wishlistService.removeProductFromWishlist(1L, 1L);
//
//        assertEquals("Test User", response.getCustomerName());
//        assertFalse(response.getProducts().contains("Test Product"));
//
//        verify(wishlistDao, times(1)).saveAndFlush(wishlist);
//    }
//}