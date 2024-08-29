package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.CustomerDto;
import com.ideas2it.flipzon.model.Customer;

public class CustomerMapper {
    public static CustomerDto convertEntityToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getUser().getName())
                .build();
    }

    public static Customer convertDtoToEntity(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .build();
    }
}
