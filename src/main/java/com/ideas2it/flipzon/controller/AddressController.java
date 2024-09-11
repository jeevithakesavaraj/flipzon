package com.ideas2it.flipzon.controller;

import java.util.List;

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

import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.helper.JwtHelper;
import com.ideas2it.flipzon.service.AddressService;
import com.ideas2it.flipzon.service.CustomerService;

/**
 * <p>
 * AddressController is the controller for address operations of the customers
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@RestController
@RequestMapping("flipzon/api/v1/customers")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    /**
     * <p>
     * Adds an address to the customer.
     * </p>
     *
     * @param addressDto  {@link AddressDto}
     * @return savedAddressDto {@link AddressDto}
     */
    @PostMapping("/me/addresses")
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto addressDto) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        AddressDto savedAddressDto = addressService.addAddress(customerId, addressDto);
        return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
    }

    /**
     * <p>
     * Retrieves a specific address of a customer.
     * </p>
     *
     * @return list of addresses {@link AddressDto}
     */
    @GetMapping("/me/addresses")
    public ResponseEntity<List<AddressDto>> getAddressesByCustomerId() {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        List<AddressDto> addressDtos = addressService.getAddressesByCustomerId(customerId);
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * <p>
     * Updates the existing address of customer.
     * </p>
     *
     * @param addressDto To specify which address needs to be updated.
     * @return Updated address of the customer {@link AddressDto}
     */
    @PutMapping("/me/addresses")
    public ResponseEntity<AddressDto> updateAddressByCustomerId(@RequestBody AddressDto addressDto) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        AddressDto updatedAddressDto = addressService.updateAddressByCustomerId(customerId, addressDto);
        return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
    }

    /**
     * <p>
     * Deletes the address of the customer.
     * </p>
     *
     * @param addressId To specify which address needs to be deleted.
     */
    @DeleteMapping("/me/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddressByCustomerId(@PathVariable long addressId) {
        long customerId = customerService.getCustomerIdByUserName(JwtHelper.extractUserNameFromToken());
        addressService.deleteAddressByCustomerId(customerId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
