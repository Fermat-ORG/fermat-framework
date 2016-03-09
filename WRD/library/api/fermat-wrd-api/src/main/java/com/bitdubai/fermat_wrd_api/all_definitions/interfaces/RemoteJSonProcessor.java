package com.bitdubai.fermat_wrd_api.all_definitions.interfaces;

import com.bitdubai.fermat_wrd_api.layer.api.tokenly.exceptions.CantGetJSonObjectException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public abstract class RemoteJSonProcessor {

    /**
     * This method returns a JSonElement from a requested URL.
     * @param requestURL
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonElement getJsonElement(
            String requestURL)
            throws CantGetJSonObjectException {
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
            throws IOException {
        URL url=new URL(requestURL);
        Scanner scanner = new Scanner(url.openStream());
        String jSonString = new String();
        while (scanner.hasNext())
            jSonString += scanner.nextLine();
        scanner.close();
        return jSonString;
    }

}
