package com.bitdubai.fermat_tky_api.all_definitions.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyRequestMethod;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantConnectWithTokenlyException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.HTTPErrorResponseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public abstract class RemoteJSonProcessor {

    /**
     * This method returns a JSonElement from a requested URL.
     * @param requestURL
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonElement getJSonElement(
            String requestURL)
            throws CantGetJSonObjectException,
            CantConnectWithTokenlyException {
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
            CantConnectWithTokenlyException {
        try{
            URL url=new URL(requestURL);
            Scanner scanner = new Scanner(url.openStream());
            String jSonString = new String();
            while (scanner.hasNext())
                jSonString += scanner.nextLine();
            scanner.close();
            return jSonString;
        } catch (UnknownHostException e){
            throw new CantConnectWithTokenlyException(
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
            CantConnectWithTokenlyException {
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
            CantConnectWithTokenlyException {
        return getJSonElement(requestURL).getAsJsonArray();
    }

    /**
     * This method returns a JsonElement from a cURL request.
     * Here, we implemented a POST request to get responses from Tokenly public API.
     * To work, we need to pass as arguments:
     * @param requestUrl String in http format.
     * @param parameters a hashMap with key as the parameter name and value as the parameter value in request
     * @param urlParameters additional parameters to make the request.
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonElement getJsonElementByPOSTCURLRequest(
            String requestUrl,
            HashMap<String,String> parameters,
            String urlParameters)
            throws CantGetJSonObjectException,
            HTTPErrorResponseException {
        try{
            //Create URL object
            URL urlObject = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlObject.openConnection();
            //Add request headers - In this kind of request I'll use POST request
            httpsURLConnection.setRequestMethod(TokenlyRequestMethod.POST.getCode());
            if(parameters!=null){
                Iterator it = parameters.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry parameter = (Map.Entry)it.next();
                    httpsURLConnection.addRequestProperty(
                            parameter.getKey().toString(),
                            parameter.getValue().toString());
                }
            }
            //Send post request
            httpsURLConnection.setDoOutput(true);
            if(urlParameters!=null && !urlParameters.isEmpty()){
                DataOutputStream dataOutputStream = new DataOutputStream(
                        httpsURLConnection.getOutputStream());
                dataOutputStream.writeBytes(urlParameters);
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            //Get response code
            int responseCode = httpsURLConnection.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + requestUrl);
            System.out.println("Response Code : " + responseCode);
            if(responseCode!=200){
                String errorResponse = httpsURLConnection.getResponseMessage();
                HTTPErrorResponse httpErrorResponse = HTTPErrorResponse.getByCode(""+responseCode);
                throw new HTTPErrorResponseException(
                        responseCode,
                        errorResponse,
                        httpErrorResponse);
            }
            //Get the response String
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));
            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            bufferedReader.close();
            JsonParser jsonParser=new JsonParser();
            JsonElement jsonElement=jsonParser.parse(response.toString());
            return jsonElement;
        } catch (MalformedURLException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestUrl,
                    "Malformed URL");
        } catch (IOException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestUrl,
                    "There was an IOException");
        }
    }

    /**
     * This method returns a JsonElement from a cURL request.
     * Here, we implemented a GET request to get responses from Tokenly public API.
     * To work, we need to pass as arguments:
     * @param requestUrl String in http format.
     * @param parameters a hashMap with key as the parameter name and value as the parameter value in request
     * @param urlParameters additional parameters to make the request.
     * @return
     * @throws CantGetJSonObjectException
     */
    public static JsonElement getJsonElementByGETCURLRequest(
            String requestUrl,
            HashMap<String,String> parameters,
            String urlParameters)
            throws CantGetJSonObjectException, HTTPErrorResponseException {
        try{
            //Create URL object
            URL urlObject = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlObject.openConnection();
            //Add request headers
            httpsURLConnection.setRequestMethod(TokenlyRequestMethod.GET.getCode());
            if(parameters!=null){
                Iterator it = parameters.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry parameter = (Map.Entry)it.next();
                    httpsURLConnection.addRequestProperty(
                            parameter.getKey().toString(),
                            parameter.getValue().toString());
                }
            }
            if(!urlParameters.isEmpty()){
                DataOutputStream dataOutputStream = new DataOutputStream(
                        httpsURLConnection.getOutputStream());
                dataOutputStream.writeBytes(urlParameters);
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            //Get response code
            int responseCode = httpsURLConnection.getResponseCode();
            if(responseCode!=200){
                String errorResponse = httpsURLConnection.getResponseMessage();
                HTTPErrorResponse httpErrorResponse = HTTPErrorResponse.getByCode("" + responseCode);
                throw new HTTPErrorResponseException(
                        responseCode,
                        errorResponse,
                        httpErrorResponse);
            }
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));
            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            bufferedReader.close();
            JsonParser jsonParser=new JsonParser();
            JsonElement jsonElement=jsonParser.parse(response.toString());
            return jsonElement;
        } catch (MalformedURLException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestUrl,
                    "Malformed URL");
        } catch (ProtocolException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestUrl,
                    "Protocol Exception");
        } catch (IOException e) {
            throw new CantGetJSonObjectException(
                    e,
                    "Getting JSonObject from requested URL:"+requestUrl,
                    "There was an IOException");
        }
    }

}

