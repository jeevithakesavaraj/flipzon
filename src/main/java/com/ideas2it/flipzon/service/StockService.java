package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.StockDto;

/**
 * <p>
 * StockService defines the contract for the Stock-related operations.
 * This includes CRUD operations and additional business logic.
 * </p>
 *
 * @author Gokul
 */
public interface StockService {

    /**
     * Create the new Stock
     *
     * @param stockDto : new stock details
     * @return StockDto : stock details
     */
    StockDto addStock(StockDto stockDto);

    /**
     * Delete the stock by its id
     *
     * @param id : id of the stock
     */
    void deleteStock(long id);

    /**
     * view all stocks
     *
     * @return List<StockDto> : List of all stock details
     */
    List<StockDto> retrieveAllStock();

    /**
     * Update the Stock details by its id
     *
     * @param stockDto : new stock details
     * @return StockDto : stock details
     */
    StockDto updateStock(StockDto stockDto);

    /**
     * Get stock details by its id
     *
     * @param id : id of the stock
     */
    StockDto retrieveStockById(long id);
}
