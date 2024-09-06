package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.CrossSellRequestDto;
import com.ideas2it.flipzon.dto.CrossSellResponseDto;
import com.ideas2it.flipzon.model.CrossSell;

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
}
