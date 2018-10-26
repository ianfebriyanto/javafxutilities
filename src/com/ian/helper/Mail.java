/*
 * Helper JAVAFX application
 */

package com.ian.helper;

import java.io.*;
import java.net.Socket;

public class Mail {
    /**
     * host mail
     */
    private String host;

    /**
     * mail port
     */
    private int port;

    /**
     * from name / sender name
     */
    private String fromName;

    /**
     * from email / sender email
     */
    private String fromEmail;

    /**
     * to name / recipient name
     */
    private String toName;

    /**
     * to email / recipient email
     */
    private String toEmail;

    /**
     * email subject
     */
    private String subject;

    /**
     * email message
     */
    private String message;

    /**
     * create instance
     *
     * @return instance
     */
    public static Mail create() {
        return new Mail();
    }

    public Mail setHost(String host) {
        this.host = host;
        return this;
    }

    public Mail setPort(int port) {
        this.port = port;
        return this;
    }

    public Mail setFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    public Mail setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
        return this;
    }

    public Mail setToName(String toName) {
        this.toName = toName;
        return this;
    }

    public Mail setToEmail(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public Mail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Mail setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean sendMail() {
        try {
            Socket stream = new Socket(host, port);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(stream.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream.getInputStream()));

            //helo to host
            out.println("HELO " + host);
            System.out.println(in.readLine());

            // set identity
            out.println("MAIL From: " + fromEmail);
            System.out.println(in.readLine());

            // set recipient
            out.println("RCPT To: " + toEmail);
            System.out.println(in.readLine());

            // set email body
            out.println("DATA");
            System.out.println(in.readLine());
            out.println("MIME-Version: 1.0");
            out.println("Content-type: text/html; charset=iso-8859-1");
            out.println("Date: " + Date.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
            out.println("Received: (mail-generated by " + host + ")");
            out.println("From: " + fromName + " <" + fromEmail + ">");
            out.println("To: " + toName + " <" + toEmail + ">");
            out.println("Subject: " + subject);
            out.println("\r\n");
            out.println(message);

            // set end email body
            out.println(".");
            out.println("QUIT");
            System.out.println(in.readLine());

            // close socket and buffer
            out.close();
            in.close();
            stream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}