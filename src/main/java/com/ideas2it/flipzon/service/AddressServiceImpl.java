package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.AddressDao;
import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.common.APIResponse;
import com.ideas2it.flipzon.mapper.AddressMapper;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Customer;

/**
 * <p>
 * Address service is for maintaining and managing address details
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger LOGGER = LogManager.getLogger(AddressServiceImpl.class);
    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

    @Override
    public APIResponse addAddress(long customerId, AddressDto addressDto) {
        if (customerService.isCustomerPresent(customerId)) {
            Customer customer = customerService.getCustomerById(customerId);
            Address address = AddressMapper.convertDtoToEntity(addressDto);
            address.setCustomer(customer);
            AddressDto savedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            LOGGER.info("Address is added for the customer {}: Address Id {}", customerId, savedAddressDto.getId());
            apiResponse.setData(savedAddressDto);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        LOGGER.warn("No customer is found in this id : {}", customerId);
        apiResponse.setData("No customer is found :" + customerId);
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public APIResponse getAddressesByCustomerId(long customerId) {
        if (customerService.isCustomerPresent(customerId)) {
            List<Address> addresses = addressDao.findByCustomerIdAndIsDeletedFalse(customerId);
            List<AddressDto> addressDtos = new ArrayList<>();
            for (Address address : addresses) {
                addressDtos.add(AddressMapper.convertEntityToDto(address));
            }
            LOGGER.info("Getting the list of addresses in this customerId: {}", customerId);
            apiResponse.setData(addressDtos);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        LOGGER.warn("No customer is found in this id for getting the addresses: {}", customerId);
        apiResponse.setData("No customer is found :" + customerId);
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public APIResponse updateAddressByCustomerId(long customerId, AddressDto addressDto) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressDto.getId(), customerId)) {
            Address address = addressDao.findById(addressDto.getId()).get();
            address.setAddressLine(addressDto.getAddressLine());
            address.setCity(addressDto.getCity());
            address.setPinCode(addressDto.getPinCode());
            AddressDto updatedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            LOGGER.info("Address is updated for the customer Id: {}", customerId);
            apiResponse.setData(updatedAddressDto);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        LOGGER.warn("No Address is found for this customer in this address Id: {}", addressDto.getId());
        apiResponse.setData("No data found");
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public APIResponse deleteAddressByCustomerId(long customerId, long addressId) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressId, customerId)) {
            Address address = addressDao.findById(addressId).get();
            address.setDeleted(true);
            addressDao.save(address);
            LOGGER.info("Address is deleted in this Id: {}", addressId);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(addressId+ "deleted successfully.");
            return apiResponse;
        }
        LOGGER.warn("No Address is found for this customer in this address Id for deleting: {}", addressId);
        apiResponse.setData("No Address found for customer" + customerId);
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    @Override
    public Address getAddressById(long id) {
        LOGGER.info("getting the address by addressID: {}", id);
        return addressDao.findById(id).get();
    }
}
