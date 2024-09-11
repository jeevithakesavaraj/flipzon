package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.AddressDao;
import com.ideas2it.flipzon.dto.AddressDto;
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
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

    @Override
    public AddressDto addAddress(long customerId, AddressDto addressDto) {
        if (customerService.isCustomerPresent(customerId)) {
            Customer customer = customerService.getCustomerById(customerId);
            Address address = AddressMapper.convertDtoToEntity(addressDto);
            address.setCustomer(customer);
            AddressDto savedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            LOGGER.info("Address is added for the customer {}: Address Id {}", customerId, savedAddressDto.getId());
            return savedAddressDto;
        }
        LOGGER.warn("No customer is found in this id : {}", customerId);
        throw new ResourceNotFoundException("No customer is found in this Id: ", customerId);
    }

    @Override
    public List<AddressDto> getAddressesByCustomerId(long customerId) {
        if (customerService.isCustomerPresent(customerId)) {
            List<Address> addresses = addressDao.findByCustomerIdAndIsDeletedFalse(customerId);
            List<AddressDto> addressDtos = new ArrayList<>();
            for (Address address : addresses) {
                addressDtos.add(AddressMapper.convertEntityToDto(address));
            }
            LOGGER.info("Getting the list of addresses in this customerId: {}", customerId);
            return addressDtos;
        }
        LOGGER.warn("No customer is found in this id for getting the addresses: {}", customerId);
        throw new ResourceNotFoundException("No customer is found in this Id: ", customerId);
    }

    @Override
    public AddressDto updateAddressByCustomerId(long customerId, AddressDto addressDto) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressDto.getId(), customerId)) {
            Address address = addressDao.findById(addressDto.getId()).get();
            address.setAddressLine(addressDto.getAddressLine());
            address.setCity(addressDto.getCity());
            address.setPinCode(addressDto.getPinCode());
            AddressDto updatedAddressDto = AddressMapper.convertEntityToDto(addressDao.save(address));
            LOGGER.info("Address is updated for the customer Id: {}", customerId);
            return updatedAddressDto;
        }
        LOGGER.warn("No Address is found for this customer in this address Id: {}", addressDto.getId());
        throw new ResourceNotFoundException("No Address is found for this customer in this address Id: {}", addressDto.getId());
    }

    @Override
    public void deleteAddressByCustomerId(long customerId, long addressId) {
        if (!addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(addressId, customerId)) {
            LOGGER.warn("No Address is found for this customer in this address Id for deleting: {}", addressId);
            throw new ResourceNotFoundException("No Address is found for this customer in this address Id for deleting: {}", addressId);

        }
        Address address = addressDao.findById(addressId).get();
        address.setDeleted(true);
        addressDao.save(address);
        LOGGER.info("Address is deleted in this Id: {}", addressId);
    }

    @Override
    public Address getAddressById(long id, long customerId) {
        if (addressDao.existsByIdAndCustomerIdAndIsDeletedFalse(id, customerId)) {
            LOGGER.info("getting the address by addressID: {}", id);
            return addressDao.findById(id).get();
        }
        LOGGER.warn("No address is found in this ID: ", id);
        throw new ResourceNotFoundException("Address ", id);
    }
}
