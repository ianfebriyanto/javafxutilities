/*
 * Helper JAVAFX application
 */

package com.ian.helper;

import java.io.*;

public class Logging {
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

    private FileWriter fw;

    private BufferedWriter bw;

    private PrintWriter out;

    /**
     * to get instance
     *
     * @return instance
     */
    public static Logging getInstance() {
        return new Logging();
    }

    /**
     * set user defined direktori
     *
     * @param userDefinedDir set user dir
     * @return instance
     */
    public Logging setDir(String userDefinedDir) {
        this.userDefinedDir = userDefinedDir;
        return this;
    }

    /**
     * set log name
     *
     * @param userDefinedLogName set user log name
     * @return instance
     */
    public Logging setLogName(String userDefinedLogName) {
        this.userDefinedLogName = userDefinedLogName;
        return this;
    }

    /**
     * get writer
     *
     * @return
     */
    public PrintWriter getWriter() {
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

            fw = new FileWriter(pathLogFile, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * set log message
     *
     * @param out     printer write
     * @param message log message
     */
    public void setMessage(PrintWriter out, String message) {
        out.println(getFormatedLog(message));
    }

    /**
     * close write
     */
    public void closeWriter() {
        try {
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
