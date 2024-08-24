package com.ideas2it.flipzon.mapper;


import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.model.Stock;

/**
 * Object to Data Transfer Object and Dto to Object Conversion
 */
public class StockMapper {

    /**
     * Convert the stock to the stockDto
     * @param stock {@link Stock}
     * @return StockDto {@link StockDto}
     */
    public static StockDto convertEntityToDto(Stock stock) {
        return StockDto.builder()
                .id(stock.getId())
                .price(stock.getPrice())
                .product(stock.getProduct())
                .initialQuantity(stock.getInitialQuantity())
                .currentQuantity(stock.getCurrentQuantity())
                .build();
    }

    /**
     * Convert the stockDto to the stock
     * @param stockDto {@link StockDto}
     * @return Stock {@link Stock}
     */
    public static Stock convertDtoToEntity(StockDto stockDto) {
        return Stock.builder()
                .id(stockDto.getId())
                .price(stockDto.getPrice())
                .initialQuantity(stockDto.getInitialQuantity())
                .currentQuantity(stockDto.getCurrentQuantity())
                .build();
    }
}

