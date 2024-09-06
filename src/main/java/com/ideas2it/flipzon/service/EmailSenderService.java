package com.ideas2it.flipzon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
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

}
