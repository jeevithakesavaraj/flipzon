package com.ideas2it.flipzon.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dto.WishlistDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;

/**
 * <p>
 * Wishlist Mapper is for conversion of Dto to Entity and Entity to Dto
 * </p>
 *
 * @author Gowthamraj
 */
public class WishlistMapper {

    /**
     * <p>
     * Convert Wishlist Entity to Wishlist Dto
     * </p>
     * @param wishlist  {@link Wishlist}
     * @return wishlistDto {@link WishlistDto}
     */
    public static WishlistDto convertEntityToDto(Wishlist wishlist) {
        return WishlistDto.builder()
                .id(wishlist.getId())
                .customerId(wishlist.getCustomer().getId())
                .productIds(wishlist.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    /**
     * <p>
     * Convert Wishlist Dto to Wishlist Entity
     * </p>
     * @param wishlistDto {@link WishlistDto}
     * @return wishlist {@link Wishlist}
     */
    public static Wishlist convertDtoToEntity(WishlistDto wishlistDto, Customer customer, Set<Product> products) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(wishlistDto.getId());
        wishlist.setCustomer(customer);
        wishlist.setProducts(products);
        return wishlist;
    }
}
