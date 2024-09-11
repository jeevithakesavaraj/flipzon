package com.ideas2it.flipzon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Product;
/**
 * Repository interface for accessing product data from the database.
 *
 * @author Gokul
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Find the product by its id.
     *
     * @param id : id of the product
     * @return Product {@link Product}
     */
    Product findByIdAndIsDeletedFalse(long id);

    /**
     * Get the list of active products.
     *
     * @return List<Product> list of Products
     */
    List<Product> findByIsDeletedFalse();

    /**
     * Get the list of products in category wise.
     *
     * @return List<Product> list of Products
     */
    List<Product> findByCategoryIdAndIsDeletedFalse(Long id);
}
