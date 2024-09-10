package com.ideas2it.flipzon.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.exception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Wishlist;

@Service
public class WishlistServiceImpl implements WishlistService {

    private static final Logger LOGGER = LogManager.getLogger(WishlistServiceImpl.class);

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Override
    public WishlistResponseDto addProductToWishlist(long customerId, long productId) {
        Customer customer = customerService.getCustomerById(customerId);
        Product product = productService.retrieveProductByIdWithStock(productId);
        if (!wishlistDao.existsByCustomerId(customerId)) {
            Wishlist wishlist = new Wishlist();
            wishlist.setCustomer(customer);
            if (wishlist.getProducts() == null) {
                wishlist.setProducts(Set.of(product));
            }
            wishlistDao.save(wishlist);
            LOGGER.info("Product added to wishlist product Name : {}", product.getName());
            return WishlistResponseDto.builder()
                    .customerName(customerService.getCustomerById(customerId).getUser().getName())
                    .products(wishlist.getProducts().stream()
                            .map(Product :: getName)
                            .collect(Collectors.toSet()))
                    .build();
        }
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        if (!productPresentInWishlist(productId, wishlist)) {
            LOGGER.warn("Product already present in this wishlist productId : {}", productId);
            throw new MyException("Product already present in this wishlist productId : " + productId);
        }
        wishlist.getProducts().add(product);
        wishlistDao.save(wishlist);
        LOGGER.info("Product added to wishlist product Name : {}", product.getName());
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                .map(Product :: getName)
                .collect(Collectors.toSet()))
                .build();
    }

    private boolean productPresentInWishlist(long productId, Wishlist wishlist) {
        for(Product product : wishlist.getProducts()) {
            if (product.getId() == productId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public WishlistResponseDto getProductsFromWishlist(long customerId) {
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        if (wishlist == null) {
            LOGGER.warn("No products in wishlist");
            throw new ResourceNotFoundException("Please add products to wishlist", "customerId", customerId);
        }
        LOGGER.info("Wishlist of the customer with ID: " + customerId);
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public WishlistResponseDto removeProductFromWishlist(long customerId, long productId) {
        productService.retrieveProductById(productId);
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        if (wishlist == null) {
            LOGGER.warn("Wishlist not available ");
            throw new ResourceNotFoundException();
        }
        boolean flag = productPresentInWishlist(productId,wishlist);
        if (!flag) {
            LOGGER.warn("product not available in this wishlist");
            throw new ResourceNotFoundException();
        }
        wishlist.getProducts().removeIf(product1 -> product1.getId() == productId);
        wishlist = wishlistDao.saveAndFlush(wishlist);
        LOGGER.info("Product is removed from wishlist");
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}