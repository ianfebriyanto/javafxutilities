/*
 * Helper JAVAFX application
 */

package com.ian.helper;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class WebService {
    private interface WebServiceImpl {
        /**
         * get result message String
         *
         * @param message response message
         */
        void onResponse(String message);

        /**
         * get http connection
         *
         * @param http http connection
         */
        void onResponse(HttpURLConnection http);
    }

    public static abstract class WebServiceResponse implements WebServiceImpl {

    }

    /**
     * http method
     */
    public enum HTTP_METHOD {
        GET,
        POST
    }

    /**
     * get istance
     *
     * @return instance
     */
    public static WebService load() {
        return new WebService();
    }

    /**
     * for http request
     *
     * @param uri      url to post
     * @param method   method post
     * @param data     data to post
     * @param response response data from request
     * @throws IOException exception
     */
    public void sendDataHttp(String uri, HTTP_METHOD method, HashMap<String, String> data, WebServiceResponse response) throws IOException {
        String fixedData = dataBuilder(data);
        if (method == HTTP_METHOD.GET) {
            uri += "?" + fixedData;
        }
        URL url = new URL(uri);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setDoOutput(true);

        switch (method) {
            case GET:
                http.setRequestMethod("GET");
                break;
            case POST:
                http.setRequestMethod("POST");
                byte[] out = fixedData.getBytes();
                http.setFixedLengthStreamingMode(out.length);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                try (OutputStream os = http.getOutputStream()) {
                    os.write(out);
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        http.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        response.onResponse(in.readLine());
        response.onResponse(http);
        in.close();
        http.disconnect();
    }

    /**
     * for https request
     *
     * @param uri      url to post
     * @param method   method post
     * @param data     data to post
     * @param response response data from request
     * @throws IOException exception
     */
    public void sendDataHttps(String uri, HTTP_METHOD method, HashMap<String, String> data, WebServiceResponse response) throws IOException {

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        String fixedData = dataBuilder(data);
        if (method == HTTP_METHOD.GET) {
            uri += "?" + fixedData;
        }
        URL url = new URL(uri);
        URLConnection con = url.openConnection();
        HttpsURLConnection https = (HttpsURLConnection) con;
        https.setHostnameVerifier((s, sslSession) -> true);
        https.setDoOutput(true);

        switch (method) {
            case GET:
                https.setRequestMethod("GET");
                break;
            case POST:
                https.setRequestMethod("POST");
                byte[] out = fixedData.getBytes();
                https.setFixedLengthStreamingMode(out.length);
                https.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                try (OutputStream os = https.getOutputStream()) {
                    os.write(out);
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        https.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(https.getInputStream()));
        response.onResponse(in.readLine());
        response.onResponse(https);
        in.close();
        https.disconnect();
    }

    /**
     * build string data
     *
     * @param data hash map
     * @return string data
     * @throws UnsupportedEncodingException exception
     */
    private String dataBuilder(HashMap<String, String> data) throws UnsupportedEncodingException {
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return sj.toString();
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
