package com.bitdubai.fermat_cer_api.layer.provider.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 * This class deals with fetching JSON Objects from URL addresses
 */
public class HttpHelper {

    public static String postHTTPContent(String address, Map<String, Object> postParams) throws Exception {

        String response;

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : postParams.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) new URL(address).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        response = readAll(in);

        return response;
    }


    /**
     * This method accepts an URL and fetches its content, returning it in a string
     **/
    public static String getHTTPContent(String receiveurl) {
        InputStream stream = null;
        BufferedReader reader;
        String content = "";

        try {
            //  stream = new URL(url).openStream();
            URL url = new URL(receiveurl);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            //  reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
            content = readAll(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * This method accepts a reader object which reads the entire stream and returns a string
     **/
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
