//package com.ideas2it.flipzon.controller;
//
//import com.ideas2it.flipzon.model.Cart;
//import com.ideas2it.flipzon.service.CartItemService;
//import com.ideas2it.flipzon.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ideas2it.flipzon.dto.CartDto;
//import com.ideas2it.flipzon.model.Customer;
//import com.ideas2it.flipzon.service.CustomerService;
//
//@RestController
//@RequestMapping("flipzon/customers")
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    @PostMapping("/productId")
//    public ResponseEntity<CartDto> addProductToCart(CartDto cartDto, long productId) {
//        cartService.addCart(cartDto);
//        cartItemService.addCartItem();
//        Customer customer = customerService.getCustomerById(customerId);
//        if (null == customer.getCart()) {
//            cartService.addCart()
//        }
//        cartService.getCartById(customer.getCart().getId());
//
//    }
//
//}
