package com.ideas2it.flipzon.controller;

import java.util.List;

import jakarta.validation.Valid;
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
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.service.ProductService;

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

    @Autowired
    private ProductService productService;

    @PostMapping("/{customerId}/products")
    public ResponseEntity<CartResponseDto> addProductToCart(@PathVariable Long customerId , @Valid @RequestBody CartDto cartDto) {
        CartResponseDto updatedCart = cartService.addProductToCart(customerId, cartDto);
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
    @GetMapping("/{customerId}/carts")
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
        return new ResponseEntity<>(cartService.removeProductFromCart(customerId, productId), HttpStatus.NO_CONTENT);
    }

    /**
     * <p>
     * Updates the quantity of the existing product in cart.
     * </p>
     *
     * @param cartDto {@link CartDto}
     * @return Updated Cart. {@link CartResponseDto}
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<CartResponseDto> updateProductQuantity(@Valid @PathVariable Long customerId, @RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.updateProductQuantity(customerId, cartDto), HttpStatus.OK);
    }

    /**
     * <p>
     *  Get the list of products
     * </p>
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.retrieveAllProduct(), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by brand Id
     * </p>
     * @param id : id of the brand
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/products/brands/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsByBrandId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByBrandId(id), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by category
     * </p>
     * @param id : id of the category
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/products/categories/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByCategoryId(id), HttpStatus.OK);
    }
}
