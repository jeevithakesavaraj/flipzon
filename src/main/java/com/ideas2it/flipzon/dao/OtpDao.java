package com.ideas2it.flipzon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.flipzon.model.Otp;

@Repository
public interface OtpDao extends JpaRepository<Otp, Long> {

    /**
     * <p>
     * Get otp details by mailID
     * </p>
     * @param mailId  Mail Id of the user
     * @return Otp {@link Otp}
     */
    Otp findByMailId(String mailId);
}
