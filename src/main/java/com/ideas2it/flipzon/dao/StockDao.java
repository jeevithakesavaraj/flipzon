package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Stock;

/**
 * Repository interface for accessing Stock data from the database.
 *
 * @author Gokul
 */
@Repository
public interface StockDao extends JpaRepository<Stock, Long>{

    /**
     * Find the stock details  by product id.
     *
     * @param id : id of the product
     * @return Stock {@link Stock}
     */
    Stock findByProductIdAndIsDeletedFalse(long id);
    /**
     * Find the stock details  by product id.
     *
     * @param id : id of the product
     * @return Stock {@link Stock}
     */
    Stock findByProductId(long id);

    /**
     * Get the list of active Stocks.
     *
     * @return List<Stock> list of Stocks
     */
    List<Stock> findByIsDeletedFalse();
}
