package com.ideas2it.flipzon.dao;

import com.ideas2it.flipzon.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpDao extends JpaRepository<Otp, Long> {
}
