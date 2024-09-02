package com.ideas2it.flipzon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.service.AddressService;
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
    /**
     * <p>
     * Adds an address to the customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @param addressDto To provide the address details.
     * @return Updated addresses. {@link APIResponse}
     */
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> addAddress(@PathVariable long customerId, @RequestBody AddressDto addressDto) {
        APIResponse apiResponse =  addressService.addAddress(customerId, addressDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Retrieves a specific address of a customer.
     * </p>
     *
     * @param customerId To identify which customer.
     * @return Address of the customer. {@link APIResponse}
     */
    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> getAddressesByCustomerId(@PathVariable long customerId) {
        APIResponse apiResponse = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Updates the existing address of customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @param addressDto To specify which address needs to be updated.
     * @return Updated address of the customer. {@link APIResponse}
     */
    @PutMapping("/{customerId}/addresses")
    public ResponseEntity<APIResponse> updateAddressByCustomerId(@PathVariable long customerId, @RequestBody AddressDto addressDto) {
        APIResponse apiResponse = addressService.updateAddressByCustomerId(customerId, addressDto);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }

    /**
     * <p>
     * Deletes the address of the customer.
     * </p>
     *
     * @param customerId To specify which customer.
     * @param addressId To specify which address needs to be deleted.
     * @return Updated addresses. {@link APIResponse}
     */
    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<APIResponse> deleteAddressByCustomerId(@PathVariable long customerId, @PathVariable long addressId) {
        APIResponse apiResponse = addressService.deleteAddressByCustomerId(customerId, addressId);
        return ResponseEntity.status(apiResponse.getStatus())
                .body(apiResponse);
    }
}
