package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONObject;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 * This class deals with fetching JSON Objects from URL addresses
 */
public class HttpReader {

    /*
     * This method accepts an URL and fetches its content, returning it in a string
     */
    public static String getHTTPContent(String url)
    {
        InputStream stream = null;
        BufferedReader reader;
        String content = "";

        try {
            stream = new URL(url).openStream();
            reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
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
