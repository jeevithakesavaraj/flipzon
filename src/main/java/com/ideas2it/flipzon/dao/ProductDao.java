package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing product data from the database.
 *
 * @author Gokul
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {


    Product findByIdAndIsDeletedFalse(long id);

    Product findByIdAndIsDeletedTrue(long id);

    List<Product> findByIsDeletedFalse();

}
