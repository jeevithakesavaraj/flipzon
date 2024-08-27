package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.WishlistDto;
import com.ideas2it.flipzon.model.Wishlist;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public void addProductToWishlist(@RequestBody Wishlist wishlist, @RequestBody ProductDto productDto) {
        wishlistService.addProductToWishlist(wishlist);
    }


}
