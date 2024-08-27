package com.ideas2it.flipzon.dao;

import java.util.List;

import com.ideas2it.flipzon.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing product data from the database.
 *
 * @author Gokul
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Product findByIdAndIsDeletedFalse(long id);

    List<Product> findByIsDeletedFalse();

    List<Product> findByCategoryIdAndIsDeletedFalse(Long id);
}
