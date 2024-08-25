package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    List<Customer> findByIsDeletedFalse();
}
