package com.ideas2it.flipzon.util;

import java.util.Random;
/**
 * <p>
 * This class is used to generates otp for customers when they place the orders.
 * </p>
 *
 * @author Jeevithakesavaraj
 */
public class OtpGenerator {

    /**
     * <p>
     * Generates OTP
     * </p>
     *
     * @return six digit number
     */
    public static char[] generateOtp() {
        String numbers = "0123456789";
        int len = 6;
        Random random = new Random();
        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

}
