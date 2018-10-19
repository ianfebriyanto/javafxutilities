package com.ian.helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    /**
     * default key for encrypt
     */
    private static final String KEY = "123*(#$%^@345@#66%&&$%2345%$dghdfFFW:{:]";

    /**
     * to encrypt string
     *
     * @param userInput user string
     * @return String
     */
    public static String encrypt(String userInput) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "BlowFish");
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
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
    public static String decrypt(String userInput) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "Blowfish");
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(userInput.getBytes());
            return new String(decrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
