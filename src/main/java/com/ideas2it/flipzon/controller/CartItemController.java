package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dao.CartItemDao;
import com.ideas2it.flipzon.dto.CartItemDto;
import com.ideas2it.flipzon.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cartitems")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addCartItem(@RequestBody CartItemDto cartItemDto) {
        cartItemService.addCartItem(cartItemDto);
        return ResponseEntity.ok("Product added successfully");
    }
}
