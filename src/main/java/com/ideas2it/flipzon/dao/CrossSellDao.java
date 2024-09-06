package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.CrossSell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossSellDao extends JpaRepository<CrossSell, Long> {
    CrossSell findByProductId(Long id);

}
