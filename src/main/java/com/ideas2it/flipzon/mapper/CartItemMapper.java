package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;

/**
 * <p>
 * CartItem Mapper class represents methods for Conversion of Dto to Entity and Entity to Dto
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class CartItemMapper {

    /**
     * <p>
     * This Method converts CartItemDto to CartItem
     * </p>
     * @param cartItemDto    {@link CartItemDto}
     * @return cartItem     {@link CartItem}
     */
    public static CartItem convertDtoToEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .cart(Cart.builder().id(cartItemDto.getCartId()).build())
                .product(Product.builder().id(cartItemDto.getProduct_id()).build())
                .quantity(cartItemDto.getQuantity())
                .build();
    }

    /**
     * <p>
     * This method converts CartItem to CartItemDto
     * </p>
     * @param cartItem   {@link CartItem}
     * @return cartItemDto  {@link CartItemDto}
     */
    public static CartItemDto convertEntityToDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart().getId())
                .product_id(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}
