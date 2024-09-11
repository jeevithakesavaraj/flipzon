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
import com.ideas2it.flipzon.helper.JwtHelper;
import com.ideas2it.flipzon.service.CustomerService;
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
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @PostMapping("/me/carts/products")
    public ResponseEntity<CartResponseDto> addProductToCart(@Valid @RequestBody CartDto cartDto) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        CartResponseDto updatedCart = cartService.addProductToCart(customerId, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * <p>
     * Retrieve all the products from wishlist of a customer.
     * </p>
     *
     * @return CartItems {@link CartResponseDto}
     */
    @GetMapping("/me/carts")
    public ResponseEntity<CartResponseDto> getProductsFromCart() {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return new ResponseEntity<>(cartService.getProductsFromCart(customerId), HttpStatus.OK);
    }

    /**
     * <p>
     * Removes the product from the wishlist of a customer.
     * </p>
     *
     * @param productId To specify which product needs to be deleted.
     * @return Updated Cart. {@link CartResponseDto}
     */
    @DeleteMapping("/me/carts/products/{productId}")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable long productId) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
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
    @PutMapping("/me/carts")
    public ResponseEntity<CartResponseDto> updateProductQuantity(@RequestBody CartDto cartDto) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        return new ResponseEntity<>(cartService.updateProductQuantity(customerId, cartDto), HttpStatus.OK);
    }

    /**
     * <p>
     *  Get the list of products
     * </p>
     *
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
    @GetMapping("/brands/{brandId}/products")
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
    @GetMapping("/categories/{id}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.retrieveAllProductByCategoryId(id), HttpStatus.OK);
    }

    /**
     * <p>
     * Get products by category
     * </p>
     * @param subCategoryId : id of the sub category
     * @return List<ProductDto> {@link ProductDto}
     */
    @GetMapping("/subcategories/{subCategoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsBySubcategoryId(@PathVariable Long subCategoryId) {
        return new ResponseEntity<>(productService.retrieveAllProductBySubcategoryId(subCategoryId), HttpStatus.OK);
    }
}
