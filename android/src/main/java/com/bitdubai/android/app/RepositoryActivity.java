package com.bitdubai.android.app;

import android.app.Activity;
import android.os.Bundle;

import com.bitdubai.smartwallet.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by toshiba on 04/03/2015.
 */
public class RepositoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.runtime_app_activity_runtime);
            connect();
        }
        catch (Exception e) {
            System.err.println("CantStartPlatformException: " + e.getMessage());

        }

    }
    public static void connect()
            throws MalformedURLException, IOException {
        URL url = new URL("https://github.com/bitDubai/fermat-wallet-resource-package-age-kids-all-bitdubai");
        URLConnection con = url.openConnection();

        Authenticator au = new Authenticator() {
            @Override
            protected PasswordAuthentication
            getPasswordAuthentication() {
                return new PasswordAuthentication
                        ("natalia_veronica_c@hotmail.com", "nattyco2013".toCharArray());
            }
        };
        Authenticator.setDefault(au);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String linea;
        while ((linea = in.readLine()) != null) {
            System.out.println(linea);
        }
    }

}