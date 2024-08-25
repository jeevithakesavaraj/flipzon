package com.ideas2it.flipzon.service;

import java.util.Set;

import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.flipzon.dao.CartDao;
import com.ideas2it.flipzon.dto.CartDto;
import com.ideas2it.flipzon.mapper.CartMapper;
import com.ideas2it.flipzon.model.Cart;
import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.CartItem;
import com.ideas2it.flipzon.model.Product;

/**
 * <p>
 * CartServiceImpl class implements CartService and inherits the methods in Cart service
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductService productService;

    @Override
    public CartDto addProductToCart(Long cartId, Long productId, Integer quantity) {

        Cart cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = ProductMapper.convertDtoToEntity(productService.retrieveProductById(productId));

        CartItem cartItem = cartItemDao.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem != null) {
            throw new MyException("Product " + product.getName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0) {
            throw new MyException(product.getName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new MyException("Please, make an order of the " + product.getName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .quantity(quantity)
                .productPrice(product.getPrice())
                .build();

        cartItemDao.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        CartDto cartDto = CartMapper.convertEntityToDto(cart);

        Set<ProductDto> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        cartDto.setProducts(productDTOs);

        return cartDto;

    }

}
