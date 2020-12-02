package com.ian.helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    /**
     * default key for encrypt
     */
    private static final String KEY = "8jHKlTM*()%$@$@#";
    private static final String AES = "AES";
    private static final String SALT = "JE2(()kslf@#$%&*";

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

    /**
     * Encrypt AES
     *
     * @param value user input
     * @return hex string
     */
    public static String encryptAES(String value) {
        try {
            SecretKeySpec sks = new SecretKeySpec(SALT.getBytes(), AES);
            Cipher cipher = null;
            cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return byteArrayToHexString(encrypted);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypt AES
     *
     * @param userInput user input
     * @return decrypted string
     */
    public static String decryptAES(String userInput) {
        try {
            SecretKeySpec sks = new SecretKeySpec(SALT.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            byte[] decrypted = cipher.doFinal(hexStringToByteArray(userInput));
            return new String(decrypted);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
}
