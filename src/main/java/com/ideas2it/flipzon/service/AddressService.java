package com.ideas2it.flipzon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.model.Address;

/**
 * <p>
 *  This class has methods for CRUD operations of address
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public interface AddressService {

    /**
     * <p>
     *  Add address to the customer by customer Id
     * </p>
     * @param customerId id of the customer to whom we have to add address
     * @param addressDto  {@link AddressDto}
     * @return savedAddressDto {@link AddressDto}
     */
    AddressDto addAddress(long customerId, AddressDto addressDto);

    /**
     * <p>
     * Get list of address for the particular customer by customer Id
     * </p>
     * @param customerId : id of the customer
     * @return list of address {@link AddressDto}
     */
    List<AddressDto> getAddressesByCustomerId(long customerId);

    /**
     * <p>
     * Update address by customer Id
     * </p>
     * @param customerId : id of the customer
     * @param addressDto  {@link AddressDto}
     * @return updatedAddressDto {@link AddressDto}
     */
    AddressDto updateAddressByCustomerId(long customerId, AddressDto addressDto);


    /**
     * <p>
     * Delete address using address Id for the particular customer
     * </p>
     * @param customerId : id of the customer
     * @param addressId : id of the address
     */
    void deleteAddressByCustomerId(long customerId, long addressId);

    /**
     * @param id : id of the address
     * @return {@link Address}
     */
    Address getAddressById(long id);
}
