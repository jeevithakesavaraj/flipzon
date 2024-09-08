package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.CrossSellRequestDto;
import com.ideas2it.flipzon.dto.CrossSellResponseDto;

/**
 *
 */
public interface CrossSellService {
    /**
     * Add cross-sell products to the product
     *
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto addCrossSellProduct(CrossSellRequestDto crossSellRequestDto);

    /**
     * Remove cross-sell product to the product
     *
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto removeCrossSellProduct(CrossSellRequestDto crossSellRequestDto) ;

    /**
     * Get cross-sell products
     * @param productId id of the product
     * @return {@link CrossSellResponseDto}
     */
    CrossSellResponseDto getCrossSellProduct(long productId);
}
