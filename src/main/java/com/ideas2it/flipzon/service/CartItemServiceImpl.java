package com.ideas2it.flipzon.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.mapper.CartItemMapper;
import com.ideas2it.flipzon.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;


@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemDao cartItemDao;

//    public CartItemDto addProductToCartItem(long customerId, long productId) {
//        Customer customer = customerService.getCustomerById(customerId);
//        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
//        Cart cart = cartService.getCartByCustomerId(customer);
//
//        if (!cartItemDao.existsByCartItemId(customer.getCart().getId())) {
//            CartItem cartItem = new CartItem();
//            cartItem.setCart(cart);
//            if (cartItem.getProducts() == null) {
//                cartItem.setProducts(Set.of(product));
//            }
//            cartItemDao.save(cartItem);
//            return CartItemDto.builder()
//                    .cartId(cartItem.getId())
//                    .price(cartItem.getPrice())
//                    .quantity(cartItem.getQuantity())
//                    .totalPrice(cartItem.getTotalPrice())
//                    .productIds(cartItem.getProducts().stream()
//                            .map(Product::getId)
//                            .collect(Collectors.toSet()))
//                    .build();
//        }
//        CartItem cartItem = cartItemDao.findByCartId(customer.getCart().getId());
//        cartItem.getProducts().add(product);
//        return CartItemMapper.convertEntityToDto(cartItemDao.save(cartItem));
////        return WishlistResponseDto.builder()
////                .customerName(customerService.getCustomerById(customerId).getUser().getName())
////                .products(wishlist.getProducts().stream()
////                        .map(Product :: getName)
////                        .collect(Collectors.toSet()))
////                .build();
//    }

//    public CartItemDto addProductToCartItem(long customerId, long productId) {
//        Customer customer = customerService.getCustomerById(customerId);
//        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
//
//        Cart cart = cartService.getCartByCustomerId(customer);
//
//        CartItem cartItem;
//        if (!cartItemDao.existsByCartId(cart.getId())) {
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProducts(Set.of(product));  // Initialize with the first product
//            cartItem.setQuantity(1);  // Initial quantity as 1
//            cartItem.setPrice(product.getPrice());  // Set price of product
//            cartItem.setTotalPrice(product.getPrice());  // Set total price
//
//            cartItemDao.save(cartItem);
//        } else {
//            cartItem = cartItemDao.findByCartId(cart.getId());
//            cartItem.getProducts().add(product);
//            cartItem.setQuantity(cartItem.getQuantity() + 1);  // Increase quantity
//            cartItem.setTotalPrice(cartItem.getTotalPrice() + product.getPrice());  // Update total price
//            cartItemDao.save(cartItem);
//        }
//
//        return CartItemDto.builder()
//                .cartId(cartItem.getId())
//                .price(cartItem.getPrice())
//                .quantity(cartItem.getQuantity())
//                .totalPrice(cartItem.getTotalPrice())
//                .productIds(cartItem.getProducts().stream()
//                        .map(Product::getId)
//                        .collect(Collectors.toSet()))
//                .build();
//    }

//    public CartItemDto addProductToCartItem(long customerId, long productId, int quantity) {
//        Customer customer = customerService.getCustomerById(customerId);
//        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
//
//        Cart cart = cartService.getCartByCustomerId(customer);
//
//        CartItem cartItem;
//        if (!cartItemDao.existsByCartId(cart.getId())) {
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProducts(Set.of(product));  // Initialize with the first product
//            cartItem.setQuantity(quantity);  // Set quantity from JSON
//            cartItem.setPrice(product.getPrice());  // Set price of product
//            cartItem.setTotalPrice(product.getPrice() * quantity);  // Set total price
//
//            cartItemDao.save(cartItem);
//        } else {
//            cartItem = cartItemDao.findByCartId(cart.getId());
//            cartItem.getProducts().add(product);
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);  // Increase quantity from JSON
//            cartItem.setTotalPrice(cartItem.getTotalPrice() + product.getPrice() * quantity);  // Update total price
//            cartItemDao.save(cartItem);
//        }
//
//        return CartItemDto.builder()
//                .cartId(cartItem.getId())
//                .price(cartItem.getPrice())
//                .quantity(cartItem.getQuantity())
//                .totalPrice(cartItem.getTotalPrice())
//                .productIds(cartItem.getProducts().stream()
//                        .map(Product::getId)
//                        .collect(Collectors.toSet()))
//                .build();
//    }

    public CartItemDto addProductToCartItem(long customerId, long productId, int quantity) {
        Customer customer = customerService.getCustomerById(customerId);
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));

        Cart cart = cartService.getCartByCustomerId(customer);

        if (cart == null) {
            // If no cart exists, create a new one
            cart = new Cart();
            cart.setCustomer(customer);
            cart = cartService.saveCart(cart);  // Assuming you have a save method in cartService
        }

        CartItem cartItem;
        if (!cartItemDao.existsByCartId(cart.getId())) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProducts(Set.of(product));  // Initialize with the first product
            cartItem.setQuantity(quantity);  // Set quantity from JSON
            cartItem.setPrice(product.getPrice());  // Set price of product
            cartItem.setTotalPrice(product.getPrice() * quantity);  // Set total price

            cartItemDao.save(cartItem);
        } else {
            cartItem = cartItemDao.findByCartId(cart.getId());
            cartItem.getProducts().add(product);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);  // Increase quantity from JSON
            cartItem.setTotalPrice(cartItem.getTotalPrice() + product.getPrice() * quantity);  // Update total price
            cartItemDao.save(cartItem);
        }

        return CartItemDto.builder()
                .cartId(cartItem.getId())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .productIds(cartItem.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

}
