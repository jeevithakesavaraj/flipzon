package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.WishlistDto;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    public static Wishlist toEntity(WishlistDto wishlistDto) {
        Wishlist wishlist = new Wishlist();
         Customer customer = customerService.getUserById(wishlistDto.getUserId());
        Product product = productService.getProductById(wishlistDto.retrieveProductById());
        wishlist.setCustomer(customer);
        wishlist.setProduct(product);
        return wishlist;
    }

    public static WishlistDto toDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setId(wishlist.getId());
        wishlistDto.setCustomerId(wishlist.getCustomer().getId());
        wishlistDto.setProductId(wishlist.getProduct().getId());
        return wishlistDto;
    }
}