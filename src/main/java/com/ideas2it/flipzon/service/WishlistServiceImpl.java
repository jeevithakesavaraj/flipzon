package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.WishlistDao;
import com.ideas2it.flipzon.dto.WishlistDto;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private ProductService productService;

    @Override
    public void addProductToWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = wishlistDao.findById(wishlistId).orElseThrow();
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
        wishlist.getProducts().add(product);
        wishlistDao.save(wishlist);
    }
}
