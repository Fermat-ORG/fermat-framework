package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



public class HTTPJson {
    private BufferedReader bufferedReader;
    private InputStream inputStream;
    private JSONObject jsonObject;
    private String json = "";

    public HTTPJson() {

    }
    public JSONObject getJSONFromUrl(String url) {
        bufferedReader = null;
        inputStream = null;
        jsonObject = null;
        inputStream=getInputStream(url);
        bufferedReader = getBufferedReader(inputStream);
        jsonObject=getJsonObject(bufferedReader);
        return jsonObject;
    }

    public InputStream getInputStream(String url){
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    public BufferedReader getBufferedReader(InputStream is){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bufferedReader;
    }
    public JSONObject getJsonObject(BufferedReader reader){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
               stringBuilder.append(line + "\n");
                }
            inputStream.close();
            json = stringBuilder.toString();
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }
}