package com.ideas2it.flipzon.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.OtpDao;
import com.ideas2it.flipzon.exception.AccessDeniedException;
import com.ideas2it.flipzon.model.Otp;

/**
 * <p>
 * This class is used to sends the mail to destined user once they successfully signed up
 * along with verification otp.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OtpDao otpDao;

    private static final Logger LOGGER = LogManager.getLogger();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
    private final String userName = System.getenv("MAIL_USERNAME");

    /**
     * <p>
     * Send otp mail
     * </p>
     * @param receiverMail receiver mailId who we have to send otp
     * @param subject   subject of the mail
     * @param body body which we have to send
     */
    public void sendEmail(String receiverMail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userName);
        message.setTo(receiverMail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        LOGGER.info("Email Sent successfully to Id :{}", receiverMail);
    }

    /**
     * <p>
     *  Add otp details
     * </p>
     * @param name  Name of the user
     * @param mailId MailId of the user
     * @param password Password of the user
     * @param phoneNumber phone number of the user
     * @param userOtp Otp for the user
     */
    public void addOtp(String name, String mailId, String password, String phoneNumber, String userOtp) {
        Otp otp = Otp.builder()
                .name(name)
                .mailId(mailId)
                .password(password)
                .phoneNumber(phoneNumber)
                .otp(userOtp)
                .build();
        otpDao.save(otp);
    }

    /**
     * <p>
     *  Add otp details
     * </p>
     * @param name  Name of the user
     * @param mailId MailId of the user
     * @param password Password of the user
     * @param phoneNumber phone number of the user
     * @param idProof  Id proof of the user
     * @param userOtp Otp for the user
     */
    public void addOtp(String name, String mailId, String password, String phoneNumber, String idProof, String userOtp) {
        Otp otp = Otp.builder()
                .name(name)
                .mailId(mailId)
                .password(password)
                .phoneNumber(phoneNumber)
                .idProof(idProof)
                .otp(userOtp)
                .build();
        otpDao.save(otp);
    }

    public Otp getOtpDetailsByMailId(String mailId) {
        Otp otp = otpDao.findByMailId(mailId);
        if (null == otp) {
            throw new AccessDeniedException("Invalid EmailId");
        }
        return otp;
    }

    public List<Otp> getOtps() {
        return otpDao.findAll();
    }

    public void deleteOtp(Otp otp) {
        otpDao.delete(otp);
    }
}
