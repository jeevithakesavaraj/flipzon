package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.service.CartService;

/**
 * <p>
 * Cart controller is the controller for cart operations of the customer
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("flipzon/api/v1/customers")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/addproducts")
    public ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartDto cartDto) {
        CartResponseDto updatedCart = cartService.addProductToCart(cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * <p>
     * Retrieve all the products from wishlist of a customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @return CartItems {@link CartResponseDto}
     */
    @GetMapping("/{customerId}/cart")
    public ResponseEntity<CartResponseDto> getProductsFromCart(@PathVariable long customerId) {
        return new ResponseEntity<>(cartService.getProductsFromCart(customerId), HttpStatus.OK);
    }

    /**
     * <p>
     * Removes the product from the wishlist of a customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @param productId To specify which product needs to be deleted.
     * @return Updated Cart. {@link CartResponseDto}
     */
    @DeleteMapping("/{customerId}/cart/{productId}")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable long customerId, @PathVariable long productId) {
        return new ResponseEntity<>(cartService.removeProductFromCart(customerId, productId), HttpStatus.OK);
    }

    /**
     * <p>
     * Updates the quantity of the existing product in cart.
     * </p>
     *
     * @param cartDto To specify which product and quantity of it.
     * @return Updated Cart. {@link CartResponseDto}
     */
    @PutMapping("/update-quantity")
    public ResponseEntity<CartResponseDto> updateProductQuantity(@RequestBody CartDto cartDto) {
        CartResponseDto updatedCart = cartService.updateProductQuantity(cartDto);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

}
