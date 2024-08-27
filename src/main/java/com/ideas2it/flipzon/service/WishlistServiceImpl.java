package com.ideas2it.flipzon.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ProductService productService;

    public Wishlist addWishlistToCustomer(Customer customer) {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        Wishlist savedWishlist = wishlistDao.save(wishlist);
        return savedWishlist;
    }

    public void addProductToWishlist(long customerId, long productId) {
        Wishlist wishlist = wishlistDao.findByCustomerId(customerId);
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
        Set<Product> products = null;
        products.add(product);
        wishlist.setProducts(products);
    }
}
