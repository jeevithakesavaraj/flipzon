package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.model.Product;

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
     * @param stockDto : {@link StockDto}
     * @param product : {@link Product}
     * @return  {@link StockDto}
     */
    StockDto addStock(StockDto stockDto, Product product) ;

    /**
     * Delete the stock by its id
     *
     * @param id : id of the stock
     * @return boolean
     */
    boolean deleteStock(Long id);

    /**
     * view all stocks
     *
     * @return List<StockDto> : List of all stock details
     */
    List<StockDto> retrieveAllStock();

    /**
     * Update the Stock details by product id
     *
     * @param stockDto : new stock details
     * @return {@link StockDto}
     */
    StockDto updateStock(StockDto stockDto);
    /**
     * Refill the Stock details by product id
     *
     * @param stockDto : new stock details
     * @return {@link StockDto}
     */
    StockDto updateNewStock(StockDto stockDto);

    /**
     * Get stock details by its id
     *
     * @param id : id of the stock
     * @return {@link StockDto}
     */
    StockDto retrieveStockByProductId(Long id);

    /**
     * Reduce the product quantity as per the order placed quantity
     * @param productId : id of the product
     * @param quantity : product Quantity
     */
    void reduceStockByOrder(Long productId, int quantity);

    /**
     * Add the product quantity as per the order cancelled quantity
     * @param productId : id of the product
     * @param quantity : product Quantity
     */
    void updateStockByCancelledOrder(Long productId, int quantity);
}
