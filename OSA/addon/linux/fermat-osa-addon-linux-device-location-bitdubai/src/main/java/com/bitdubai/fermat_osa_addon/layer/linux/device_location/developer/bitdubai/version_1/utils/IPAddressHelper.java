package com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.exceptions.CantGetCurrentIPAddressException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.IPAddressHelper</code>
 * is a helper class which contains methods related with ip address.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class IPAddressHelper {

    private static final String PUBLIC_ADDRESS_URL = "http://ipinfo.io/ip";

    public static String getCurrentIPAddress() throws CantGetCurrentIPAddressException {

        HttpURLConnection conn = null;
        try {

            conn = (HttpURLConnection) new URL(PUBLIC_ADDRESS_URL).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            if (conn.getResponseCode() == 200)
                return response.trim();
            else
                throw new CantGetCurrentIPAddressException(
                        null,
                        "",
                        "We couldn't find out the ip address."
                );


        } catch (IOException ioException) {

            throw new CantGetCurrentIPAddressException(
                    ioException,
                    "",
                    "There was an error trying to get the ip address from the site: "+PUBLIC_ADDRESS_URL
            );
        } finally {

            if (conn != null)
                conn.disconnect();
        }

    }
}
