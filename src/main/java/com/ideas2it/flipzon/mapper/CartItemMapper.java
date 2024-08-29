package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;

import java.util.Set;
import java.util.stream.Collectors;

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
     * This method converts CartItem to CartItemDto
     * </p>
     * @param cartItem   {@link CartItem}
     * @return cartItemDto  {@link CartItemDto}
     */
    public static CartItemDto convertEntityToDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart().getId())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .productIds(cartItem.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    /**
     * <p>
     * This Method converts CartItemDto to CartItem
     * </p>
     * @param cartItemDto    {@link CartItemDto}
     * @return cartItem     {@link CartItem}
     */
    public static CartItem convertDtoToEntity(CartItemDto cartItemDto, Set<Product> products, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setTotalPrice(cartItemDto.getTotalPrice());
        cartItem.setProducts(products);
        cartItem.setCart(cart);
        return cartItem;
    }
}
