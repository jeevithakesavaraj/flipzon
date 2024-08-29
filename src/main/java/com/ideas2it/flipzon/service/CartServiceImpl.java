package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.model.Customer;
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
    private CartDao cartDao;

    @Autowired
    private CustomerService customerService;

    @Override
    public CartDto addCart(CartDto cartDto) {
        Cart savedCart = cartDao.save(CartMapper.convertDtoToEntity(cartDto));
        return CartMapper.convertEntityToDto(savedCart);
    }

    @Override
    public List<CartDto> getCarts() {
        return cartDao.findAll().stream()
                .map(CartMapper::convertEntityToDto).toList();
    }

    @Override
    public Cart getCartById(long id) {
        return cartDao.findById(id).orElseThrow();
    }

//    @Override
//    public Cart getCartByCustomerId(Customer customer) {
//        Cart cart = cartDao.findByCustomerId(customer.getId());
//        if (cart == null) {
//            Cart cart1 = new Cart();
//            cart1.setCustomer(customerService.getCustomerById(customer.getId()));
//            return cartDao.save(cart1);
//        }
//        return cart;
//    }

    @Override
    public Cart getCartByCustomerId(Customer customer) {
        return cartDao.findByCustomerId(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found", "customerId", customer.getId()));
    }

    @Override
    public Cart saveCart(Cart cart) {
        System.out.println(cart);
        return cartDao.save(cart);
    }
}
