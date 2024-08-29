package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dto.CartResponseDto;
import com.ideas2it.flipzon.dto.WishlistResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.model.Cart;

/**
 * <p>
 * CartServiceImpl class implements CartService and inherits the methods in Cart service
 * </p>
 *
 * @author Jeevithakesavaraj
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartDto cartDto;

    public CartResponseDto addProductToCart(CartDto cartDto) {
        Customer customer = customerService.getCustomerById(cartDto.getCustomerId());
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(cartDto.getProductId()));

        if (!cartDao.existsByCustomerId(cartDto.getCustomerId())) {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            if (cart.getProducts() == null) {
                cart.setProducts(Set.of(product));
                cart.setQuantity(cartDto.getQuantity());
                cart.setPrice(product.getPrice());
                cart.setTotalPrice(cart.getTotalPrice() + (cartDto.getQuantity() * product.getPrice()));
            }
            cart = cartDao.save(cart);
            return CartResponseDto.builder()
                    .customerName(customerService.getCustomerById(cartDto.getCustomerId()).getUser().getName())
                    .products(cart.getProducts().stream()
                            .map(Product :: getName)
                            .collect(Collectors.toSet()))
                    .quantity(cart.getQuantity())
                    .productPrice(cart.getPrice())
                    .totalPrice(cart.getTotalPrice())
                    .build();
        }
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());
        cart.getProducts().add(product);
        cart = cartDao.save(cart);
        return CartResponseDto.builder()
                .customerName(customerService.getCustomerById(cartDto.getCustomerId()).getUser().getName())
                .products(cart.getProducts().stream()
                        .map(Product :: getName)
                        .collect(Collectors.toSet()))
                .quantity(cart.getQuantity())
                .productPrice(cart.getPrice())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    @Override
    public CartResponseDto getProductsFromCart(long customerId) {
        Cart cart = cartDao.findByCustomerId(customerId);
        if (cart == null) {
            throw new ResourceNotFoundException("Please add products to wishlist", "customerId", customerId);
        }
        return CartResponseDto.builder()
                .customerName(customerService.getCustomerById(cartDto.getCustomerId()).getUser().getName())
                .products(cart.getProducts().stream()
                        .map(Product :: getName)
                        .collect(Collectors.toSet()))
                .quantity(cart.getQuantity())
                .productPrice(cart.getPrice())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    @Override
    public CartResponseDto removeProductFromCart(long customerId, long productId) {
        Customer customer = customerService.getCustomerById(customerId);
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));
        Cart cart = cartDao.findByCustomerId(customerId);
        cart.getProducts().removeIf(product1 -> product1.getId() == productId);
        cart.setProducts(cart.getProducts());
        return CartResponseDto.builder()
                .customerName(customerService.getCustomerById(customerId).getUser().getName())
                .products(cart.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

//    @Override
//    public  CartResponseDto updateProductQuantity(CartDto cartDto) {
//        Customer customer = customerService.getCustomerById(cartDto.getCustomerId());
//        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(cartDto.getProductId()));
//
//    }

    @Override
    public CartResponseDto updateProductQuantity(CartDto cartDto) {
        Customer customer = customerService.getCustomerById(cartDto.getCustomerId());
        Cart cart = cartDao.findByCustomerId(cartDto.getCustomerId());

        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found", "customerId", cartDto.getCustomerId());
        }
        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(cartDto.getProductId()));
        Set<Product> products = cart.getProducts();

        if (products.stream().noneMatch(p -> p.getId() == product.getId())) {
            throw new ResourceNotFoundException("Product not found in cart", "productId", cartDto.getProductId());
        }
        if (cartDto.getQuantity() <= 0) {
            products.removeIf(p -> p.getId() == product.getId());
        } else {
            for (Product cartProduct : products) {
                if (cartProduct.getId() == product.getId()) {
                    int previousQuantity = cart.getQuantity();
                    cart.setQuantity(cartDto.getQuantity());
                    cart.setTotalPrice(cart.getTotalPrice() + (cartDto.getQuantity() - previousQuantity) * cartProduct.getPrice());
                    break;
                }
            }
        }
        cart.setProducts(products);
        cart = cartDao.save(cart);
        return CartResponseDto.builder()
                .customerName(customer.getUser().getName())
                .products(cart.getProducts().stream()
                        .map(Product::getName)
                        .collect(Collectors.toSet()))
                .quantity(cart.getQuantity())
                .productPrice(cart.getPrice())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
