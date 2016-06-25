/*
 * @#MonitClient  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MonitClient</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MonitClient {

    /**
     * Represent the url
     */
    private String url;

    /**
     * Represent the user
     */
    private String user;

    /**
     * Represent the password
     */
    private String password;

    /**
     * Constructor with parameters
     * @param url
     * @param user
     * @param password
     */
    public MonitClient(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Create a request with the authorization credentials
     *
     * @param uri
     * @return String
     * @throws IOException
     */
    private String requestAuth(String uri) throws IOException {
        URL url = new URL((this.url.endsWith("/") ? this.url : this.url + "/") + uri);
        String credentials = Base64.encodeBase64String((user + ":" + password).getBytes());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + credentials);
        InputStream content =  connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        String line;
        String ret = "";
        while ((line = in.readLine()) != null) {
            ret += line;
        }
        in.close();
        connection.disconnect();
        return ret;
    }

    /**
     * Get html returned by the initial page of monit after authenticate
     * and parse the html and convert into a map whit contain json objects
     * arrays by type
     *
     * @return Map<String, JsonArray>
     * @throws IOException
     */
    public Map<String, JsonArray> getComponents() throws IOException {

       Map<String, JsonArray> result = new HashMap<>();
       String pageContent = this.requestAuth("").replaceAll("&nbsp;", " ");
       Document parse = Jsoup.parse(pageContent);
       Elements tables = parse.select("table[id=header-row]");

       if(tables.size() > 0){

           for (Element table: tables) {

               Elements rows  = table.select("tbody>tr");
               Elements heads = rows.get(0).select("th");
               String   type  = rows.get(0).select("[class=first]").get(0).select("th").text();
               JsonArray elements = new JsonArray();

               for (Element row : rows) {

                   Elements columns = row.select("td");
                   JsonObject jsonObject = new JsonObject();

                   for (int j = 0; j < columns.size(); j++) {

                       String propertyName = !heads.get(j).text().equals(type) ? heads.get(j).text() : "Name";
                       jsonObject.addProperty(propertyName, columns.get(j).text());

                   }

                   if (jsonObject.has("Name")) {
                       elements.add(jsonObject);
                   }
               }

               result.put(type, elements);
           }

       }

       return result;
    }

}
