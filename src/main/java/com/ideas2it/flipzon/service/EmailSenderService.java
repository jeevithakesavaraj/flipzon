package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.model.Customer;
import com.ideas2it.flipzon.model.Otp;
import com.ideas2it.flipzon.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.OtpDao;

import java.util.List;

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

    public List<Otp> getOtpAndMailId() {
        return otpDao.findAll();
    }

    public void deleteOtp(Otp otp) {
        otpDao.delete(otp);
    }
}
