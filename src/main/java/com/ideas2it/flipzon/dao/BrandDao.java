package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing brand data from the database.
 *
 * @author Gokul
 *
 */
@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);
}
