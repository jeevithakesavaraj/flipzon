package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistDao extends JpaRepository<Wishlist, Long> {
}
