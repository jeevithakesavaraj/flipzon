package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.dto.AddressDto;

public interface AddressService {

    /**
     * <p>
     *  Add address to the customer by customer Id
     * </p>
     * @param customerId   Id of the customer to whom we have to add address
     * @param addressDto  {@link AddressDto}
     * @return APIResponse {@link APIResponse}
     */
    APIResponse addAddress(long customerId, AddressDto addressDto);

    /**
     * <p>
     * Get list of address for the particular customer by customer Id
     * </p>
     * @param customerId  Id of the customer
     * @return APIResponse {@link APIResponse}
     */
    APIResponse getAddressesByCustomerId(long customerId);

    /**
     * <p>
     * Update address by customer Id
     * </p>
     * @param customerId  Id of the customer
     * @param addressDto  {@link AddressDto}
     * @return APIResponse {@link APIResponse}
     */
    APIResponse updateAddressByCustomerId(long customerId, AddressDto addressDto);


    /**
     * <p>
     * Delete address using address Id for the particular customer
     * </p>
     * @param customerId  Id of the customer
     * @param addressId  Id of the address
     * @return APIResponse {@link APIResponse}
     */
    APIResponse deleteAddressByCustomerId(long customerId, long addressId);
}
