package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.dto.UpsellDto;

public interface UpsellService {
    /**
     * Add upsell products to the product
     * @param productId product id
     * @param upsellDto {@link UpsellDto}
     * @return {@link UpsellResponseDto}
     */
    UpsellResponseDto addUpsell(Long productId, UpsellDto upsellDto);

    /**
     * Remove upsell products to the product
     * @param productId product id
     * @param id id of the upsell product
     * @return {@link UpsellResponseDto}
     */
    UpsellResponseDto deleteUpsell(Long productId, Long id);

    UpsellResponseDto getUpSellProduct(long productId);
}