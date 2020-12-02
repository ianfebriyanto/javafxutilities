package com.ian.helper;

import java.io.*;

public class Log {
    /**
     * Default current by user dir
     */
    private final String USER_DIR = Util.getUserDir();

    /**
     * Default log dir
     */
    private final String LOG_DIR = USER_DIR + "/logs/";

    /**
     * default log name
     */
    private final String LOG_NAME = Date.getCurrentDate("yyyyMMdd");

    private String userDefinedDir = "";

    private String userDefinedLogName = "";

    /**
     * to get instance
     *
     * @return instance
     */
    public static Log create() {
        return new Log();
    }

    /**
     * set user defined direktori
     *
     * @param userDefinedDir set user dir
     * @return instance
     */
    public Log setDir(String userDefinedDir) {
        this.userDefinedDir = userDefinedDir;
        return this;
    }

    /**
     * set log name
     *
     * @param userDefinedLogName set user log name
     * @return instance
     */
    public Log setLogName(String userDefinedLogName) {
        this.userDefinedLogName = userDefinedLogName;
        return this;
    }

    /**
     * set message to log
     *
     * @param message message to log
     */
    public void setMessage(String message) {
        String logDir = LOG_DIR;
        String logName = LOG_NAME;

        if (!userDefinedDir.equals("")) {
            logDir += userDefinedDir + "/";
        }

        if (!userDefinedLogName.equals("")) {
            logName += "_" + userDefinedLogName + ".log";
        } else {
            logName += ".log";
        }

        String pathLogFile = logDir + logName;
        try {
            File file = new File(logDir);
            if (!file.isDirectory()) {
                file.mkdirs();

                File logFile = new File(pathLogFile);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
            }

            FileWriter fw = new FileWriter(pathLogFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(getFormatedLog(message));
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get formated log, by date
     *
     * @param message format log name
     * @return String
     */
    private String getFormatedLog(String message) {
        String date = Date.getCurrentDate("yyyy-MM-dd HH:mm:ss");
        return "[" + date + "] " + message;
    }
}

