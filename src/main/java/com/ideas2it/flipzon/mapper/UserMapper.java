package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.dto.DeliveryDto;
import com.ideas2it.flipzon.model.Address;
import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static User convertDtoToUserEntity (CustomerDto customerDto) {
        return User.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }

    public static Customer convertDtoToCustomerEntity (CustomerDto customerDto) {
        Set<Address> addresses = new HashSet<>();
        for (AddressDto addressDto : customerDto.getAddresses()){
            addresses.add(Address.builder()
                    .addressLine(addressDto.getAddressLine())
                    .city(addressDto.getCity())
                    .pinCode(addressDto.getPinCode())
                    .build());
        }
        return Customer.builder()
                .user(User.builder().id(customerDto.getId()).build())
                .addresses(addresses)
                .isDeleted(false)
                .build();
    }

    public static CustomerDto convertEntityToDto(Customer customer) {
        Set<AddressDto> addressDtos = new HashSet<>();
        for (Address address : customer.getAddresses()) {
            addressDtos.add(AddressDto.builder()
                            .id(address.getId())
                            .addressLine(address.getAddressLine())
                            .city(address.getCity())
                            .pinCode(address.getPinCode())
                    .build());

        }
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getUser().getName())
                .email(customer.getUser().getEmail())
                .phoneNumber(customer.getUser().getPhoneNumber())
                .addresses(addressDtos)
                .build();
    }

    public static DeliveryDto convertDtoToEntity(DeliveryDto deliveryDto) {
        return DeliveryDto.builder()
                .id(deliveryDto.getId())
                .idProof(deliveryDto.getIdProof())
                .build();
    }
}
