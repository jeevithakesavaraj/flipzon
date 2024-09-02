package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 * CartMapper class have methods for conversion for Entity to Dto and Dto to Entity
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class CartMapper {

    /**
     * <p>
     * Convert CartDto to Cart
     * </p>
     *
     * @param cartDto  {@link CartDto}
     * @return cart    {@link Cart}
     */
    public static Cart convertDtoToEntity(CartDto cartDto) {
        return Cart.builder()
                .customer(Customer.builder()
                        .id(cartDto.getCartId())
                        .build())
                .build();
    }

    /**
     * <p>
     * Convert Cart to CartDto
     * </p>
     *
     * @param cart   {@link Cart}
     * @return cartDto  {@link CartDto}
     */
    public static CartDto convertEntityToDto(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getId())
                .customerId(cart.getCustomer().getId())
                .build();
    }
}
