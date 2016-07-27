package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class RemoteJSonProcessor {

    /**
     * This method returns a JSonElement from a requested URL.
     * @param requestURL
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonElement getJSonElement(
            String requestURL)
            throws CantGetJSonObjectException,
            CantConnectWithExternalAPIException {
        try{
            String jSonString=getJSonString(requestURL);
            JsonParser jsonParser=new JsonParser();
            JsonElement jsonElement=jsonParser.parse(jSonString);
            return jsonElement;
        } catch (IOException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestURL,
                    "There was a IOException");
        }
    }

    /**
     * This method returns a String response from a requested URL.
     * @param requestURL
     * @return
     * @throws IOException
     */
    public static String getJSonString(
            String requestURL)
            throws IOException,
            CantConnectWithExternalAPIException {
        try{
            URL url=new URL(requestURL);
            URLConnection urlConnection = url.openConnection();
            //Request properties.
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept","*/*");
            //End of request properties

            //System.out.println("Geolocation plugin is trying to create an URLConnection to: "+urlConnection);
            //Scanner scanner = new Scanner(url.openStream());

            Scanner scanner = new Scanner(urlConnection.getInputStream());
            String jSonString = new String();
            while (scanner.hasNext())
                jSonString += scanner.nextLine();
            scanner.close();
            return jSonString;
        } catch (UnknownHostException e){
            throw new CantConnectWithExternalAPIException(
                    e,
                    "Getting a json string from remote URL",
                    "Cannot find "+requestURL);
        }

    }

    /**
     * This method returns a JSonElement from a requested URL.
     * @param requestURL
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonObject getJSonObject(
            String requestURL)
            throws CantGetJSonObjectException,
            CantConnectWithExternalAPIException {
        return getJSonElement(requestURL).getAsJsonObject();
    }

    /**
     * This method returns a JSonArray from a requested URL.
     * @param requestURL
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonArray getJSonArray(
            String requestURL)
            throws CantGetJSonObjectException,
            CantConnectWithExternalAPIException {
        return getJSonElement(requestURL).getAsJsonArray();
    }

}
