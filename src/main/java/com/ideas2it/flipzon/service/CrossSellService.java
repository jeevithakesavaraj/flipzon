package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.CrossSellRequestDto;
import com.ideas2it.flipzon.dto.CrossSellResponseDto;

public interface CrossSellService {
    /**
     * Add cross-sell products to the product
     *
     * @param crossSellRequestDto {@link CrossSellRequestDto}
     * @param productId           product Id
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto addCrossSellProduct(Long productId, CrossSellRequestDto crossSellRequestDto);

    /**
     * Remove cross-sell product to the product
     *
     * @param id : id of the cross-sell product
     * @param productId           product Id
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto removeCrossSellProduct(Long productId, Long id);

    /**
     * Get cross-sell products
     *
     * @param productId id of the product
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto getCrossSellProduct(long productId);
}
