package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Override
    public WishlistResponseDto addProductToWishlist(long customerId, long productId) {
        Customer customer = customerService.getCustomerById(customerId);
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
        if (!wishlistDao.existsByCustomerId(customerId)) {
            Wishlist wishlist = new Wishlist();
            wishlist.setCustomer(customer);
            if (wishlist.getProducts() == null) {
                wishlist.setProducts(Set.of(product));
            }
            wishlistDao.save(wishlist);
            return WishlistResponseDto.builder()
                    .customerName(customerService.getCustomerById(customerId).getUser().getName())
                    .products(wishlist.getProducts().stream()
                            .map(Product :: getName)
                            .collect(Collectors.toSet()))
                    .build();
        }
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        wishlist.getProducts().add(product);
        wishlistDao.save(wishlist);
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                .map(Product :: getName)
                .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public WishlistResponseDto getProductsFromWishlist(long customerId) {
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        if (wishlist == null) {
            throw new ResourceNotFoundException("Please add products to wishlist", "customerId", customerId);
        }
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public WishlistResponseDto removeProductFromWishlist(long customerId, long productId) {
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        for (Product products : wishlist.getProducts()) {
            if (products.getId() == productId) {
                wishlist.getProducts().remove(product);
                wishlist = wishlistDao.saveAndFlush(wishlist);
            }
        }
        return WishlistResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(wishlist.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
