package com.ideas2it.flipzon.mapper;


import com.ideas2it.flipzon.dto.WishlistDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;

import java.util.Set;
import java.util.stream.Collectors;

public class WishlistMapper {

    public static WishlistDto convertEntityToDto(Wishlist wishlist) {
        return WishlistDto.builder()
                .id(wishlist.getId())
                .customerId(wishlist.getCustomer().getId())
                .productIds(wishlist.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Wishlist convertDtoToEntity(WishlistDto wishlistDto, Customer customer, Set<Product> products) {
//        return Wishlist.builder()
//                .id(wishlistDto.getId())
//                .build();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistDto.getId());
        wishlist.setCustomer(customer);
        wishlist.setProducts(products);
        return wishlist;
    }
}
