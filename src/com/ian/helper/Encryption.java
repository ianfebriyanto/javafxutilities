package com.ian.helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    /**
     * default key for encrypt
     */
    private static final String KEY = "8jHKlTM*()%$@$@#";

    /**
     * to encrypt string
     *
     * @param userInput user string
     * @return String
     */
    public static String encryptBlowfish(String userInput) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "BlowFish");
        try {
            Cipher cipher = Cipher.getInstance("BlowFish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(userInput.getBytes());
            return new String(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * to decrypt string
     *
     * @param userInput user string
     * @return String
     */
    public static String decryptBlowfish(String userInput) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "BlowFish");
        try {
            Cipher cipher = Cipher.getInstance("BlowFish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(userInput.getBytes());
            return new String(decrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * to generate md5 in java
     *
     * @param userInput user input
     * @return String
     */
    public static String md5(String userInput) {
        if (userInput != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(userInput.getBytes());
                return DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * to generate sha256 in java
     *
     * @param userInput user input
     * @return Sring
     */
    public static String sha256(String userInput) {
        if (userInput != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(userInput.getBytes()); //StandardCharsets.UTF_8
                return DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
