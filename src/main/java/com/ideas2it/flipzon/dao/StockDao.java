package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Stock data from the database.
 *
 * @author Gokul
 */
@Repository
public interface StockDao extends JpaRepository<Stock, Long>{

    Stock findByIdAndIsDeletedFalse(long id);

    Stock findByIdAndIsDeletedTrue(long id);

    List<Stock> findByIsDeletedFalse();
}
