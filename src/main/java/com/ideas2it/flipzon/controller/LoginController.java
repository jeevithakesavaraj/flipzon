package com.ideas2it.flipzon.controller;

import com.ideas2it.flipzon.dao.CustomerDao;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.mapper.UserMapper;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.service.CustomerService;
import com.ideas2it.flipzon.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("flipzon/api/v1")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers/signup")
    public void customerSignUp(@Valid @RequestBody CustomerDto customerDto) {
        loginService.customerSignUp(customerDto);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllUsers() {
        return customerService.getAllCustomers();
    }

//    @PostMapping("/delivery/signup")
//    public void deliverySignUp(@RequestBody DeliveryDto deliveryDto) {
//        loginService.deli
//    }
}
