package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.ProductDto;

import java.util.List;

public interface WishlistService {

    void addProductToWishlist(Long wishlistId, Long productId);

//    void removeProductFromWishlist(Long wishlistId, Long productId);
//
//    List<ProductDto> getWishlistProducts(Long wishlistId);
}
