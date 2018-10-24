package com.ian.helper;

import java.text.SimpleDateFormat;

public class Date {
    /**
     * get default current date using default current format (yyyyMMdd hh:mm:ss)
     *
     * @return String
     */
    public static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        return getSimpleFormat(null).format(date);
    }

    /**
     * get current date using user defined format
     *
     * @param format user defined format
     * @return String
     */
    public static String getCurrentDate(String format) {
        java.util.Date date = new java.util.Date();
        return getSimpleFormat(format).format(date);
    }

    /**
     * parse time from unix timestamp
     *
     * @param unixTimestamp unix timestamp
     * @param format        user defined format
     * @return String
     */
    public static String parseFromUnixTimestamp(Long unixTimestamp, String format) {
        java.util.Date date = new java.util.Date(unixTimestamp * 1000L);
        return getSimpleFormat(format).format(date);
    }

    /**
     * get simple date format
     *
     * @param format user defined format
     * @return simple date format
     */
    private static SimpleDateFormat getSimpleFormat(String format) {
        format = format != null ? format : "yyyyMMdd hh:mm:ss";
        return new SimpleDateFormat(format);
    }
}
