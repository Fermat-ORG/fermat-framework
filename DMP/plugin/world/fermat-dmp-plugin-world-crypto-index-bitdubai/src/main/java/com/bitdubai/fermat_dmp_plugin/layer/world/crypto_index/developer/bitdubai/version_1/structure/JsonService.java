package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by francisco on 13/08/15.
 */
public class JsonService {

    private JSONObject json=null;

       public String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public JSONObject getJSONFromUrl(String url) throws JSONException {
            InputStream is;
            json=null;
            try {
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                json = new JSONObject(jsonText);
            } catch (IOException e) {
                e.printStackTrace();
            }

                return json;
        }



    }

