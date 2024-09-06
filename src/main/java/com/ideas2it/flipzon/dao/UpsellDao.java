package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Upsell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpsellDao extends JpaRepository<Upsell, Long> {
    Upsell findByProduct(Product product);

    Upsell findByProductId(long id);
}
