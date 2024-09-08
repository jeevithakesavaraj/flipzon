package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Crosssell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossSellDao extends JpaRepository<Crosssell, Long> {
    Crosssell findByProductId(Long id);

}
