package com.ian.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    /**
     * get default current date using default current format (yyyyMMdd hh:mm:ss)
     *
     * @return String
     */
    public static String getCurrentDate() {
        Date date = new Date();
        return getSimpleFormat(null).format(date);
    }

    /**
     * get current date using user defined format
     *
     * @param format user defined format
     * @return String
     */
    public static String getCurrentDate(String format) {
        Date date = new Date();
        return getSimpleFormat(format).format(date);
    }

    /**
     * get current user directory
     *
     * @return String
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * get simple date format
     *
     * @param format user defined format
     * @return simple date format
     */
    private static SimpleDateFormat getSimpleFormat(String format) {
        format = !format.equals("") ? format : "yyyyMMdd hh:mm:ss";
        return new SimpleDateFormat(format);
    }
}