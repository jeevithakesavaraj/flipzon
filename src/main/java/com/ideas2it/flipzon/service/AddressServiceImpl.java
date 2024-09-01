package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private APIResponse apiResponse;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

    public APIResponse addAddress(long customerId, AddressDto addressDto) {
        if (customerService.isCustomerPresent(customerId)) {
            Customer customer = customerService.getCustomerById(customerId);
            Address address = AddressMapper.convertDtoToEntity(addressDto);
            address.setCustomer(customer);
            AddressDto savedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            apiResponse.setData(savedAddressDto);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setData("No customer is found :" + customerId);
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    public APIResponse getAddressesByCustomerId(long customerId) {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addressDao.findByCustomerIdAndIsDeletedFalse(customerId)) {
            addressDtos.add(AddressMapper.convertEntityToDto(address));
        }
        apiResponse.setData(addressDtos);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse updateAddressByCustomerId(long customerId, AddressDto addressDto) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressDto.getId(), customerId)) {
            Address address = addressDao.findById(addressDto.getId()).get();
            address.setAddressLine(addressDto.getAddressLine());
            address.setCity(addressDto.getCity());
            address.setPinCode(addressDto.getPinCode());
            AddressDto updatedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            apiResponse.setData(updatedAddressDto);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        }
        apiResponse.setData("No data found");
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    public APIResponse deleteAddressByCustomerId(long customerId, long addressId) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressId, customerId)) {
            Address address = addressDao.findById(addressId).get();
            address.setDeleted(true);
            addressDao.save(address);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(addressId+ "deleted successfully.");
            return apiResponse;
        }
        apiResponse.setData("No Address found for customer" + customerId);
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return apiResponse;
    }

    public Address getAddressById(long id) {
        return addressDao.findById(id).get();
    }
}
