package com.ideas2it.flipzon.mapper;

import com.ideas2it.flipzon.dto.AddressDto;
import com.ideas2it.flipzon.model.Address;

/**
 * <p>
 * Address Mapper is for conversion of Dto to Entity and Entity to Dto
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class AddressMapper {

    /**
     * <p>
     * Convert Address Entity to Address Dto
     * </p>
     * @param address  {@link Address}
     * @return addressDto {@link AddressDto}
     */
    public static AddressDto convertEntityToDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .addressLine(address.getAddressLine())
                .city(address.getCity())
                .pinCode(address.getPinCode())
                .build();
    }

    /**
     * <p>
     * Convert Address Dto to Address Entity
     * </p>
     * @param addressDto {@link AddressDto}
     * @return address {@link Address}
     */
    public static Address convertDtoToEntity(AddressDto addressDto) {
        return Address.builder()
                .addressLine(addressDto.getAddressLine())
                .city(addressDto.getCity())
                .pinCode(addressDto.getPinCode())
                .build();
    }
}
