package com.ian.helper;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Setting {
    /**
     * default user dir
     */
    private final static String DEFAULT_DIR = System.getProperty("user.dir");

    /**
     * default user setting name
     */
    private static String settingLocalName = "setting.properties";

    /**
     * default user local path and name
     */
    private static String settingLocalPath = DEFAULT_DIR + "/" + settingLocalName;

    /**
     * get instance and set setting name by user default
     *
     * @param settingName setting name by user
     * @return instance
     */
    public static Setting create(String settingName) {
        return new Setting(settingName);
    }

    /**
     * create setting by user on instance
     *
     * @param settingName setting name by user
     */
    private Setting(String settingName) {
        if (settingName != null) settingLocalName = settingName;

        File file = new File(settingLocalPath);
        if (!file.exists()) {
            try {
                boolean createNew = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load setting by name
     *
     * @param settingName setting name by user
     * @return properties file
     */
    public static Properties loadSetting(String settingName) {
        if (settingName != null) settingLocalPath = DEFAULT_DIR + "/" + settingName;

        Properties properties = new Properties();
        InputStream mInputStream = null;
        try {
            mInputStream = new FileInputStream(settingLocalPath);
            properties.load(mInputStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mInputStream != null) {
                try {
                    mInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Write setting by name
     *
     * @param mListSetting hash map that contain setting, key and value type is string
     * @param settingName  setting name by user
     */
    public static void writeSetting(HashMap<String, String> mListSetting, String settingName) {
        if (settingName != null) settingLocalPath = DEFAULT_DIR + "/" + settingName;
        OutputStream mOutputStream = null;
        try {
            mOutputStream = new FileOutputStream(settingLocalPath);
            Properties properties = new Properties();
            for (String key : mListSetting.keySet()) {
                properties.setProperty(key, mListSetting.get(key));
            }
            properties.store(mOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mOutputStream != null) {
                try {
                    mOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
