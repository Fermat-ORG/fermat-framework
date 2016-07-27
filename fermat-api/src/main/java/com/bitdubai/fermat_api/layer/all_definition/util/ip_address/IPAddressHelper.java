package com.bitdubai.fermat_api.layer.all_definition.util.ip_address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.util.ip_address.IPAddressHelper</code>
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

    private static String ipAddress;

    public static String getCurrentIPAddress() throws CantGetCurrentIPAddressException {

        if (ipAddress != null)
            return ipAddress;

        HttpURLConnection conn = null;
        try {

            conn = (HttpURLConnection) new URL(PUBLIC_ADDRESS_URL).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            if (conn.getResponseCode() == 200) {
                ipAddress = response.trim();
                return ipAddress;
            } else
                throw new CantGetCurrentIPAddressException(
                        null,
                        "",
                        "We couldn't find out the ip address."
                );


        } catch (IOException ioException) {

            throw new CantGetCurrentIPAddressException(
                    ioException,
                    "",
                    new StringBuilder().append("There was an error trying to get the ip address from the site: ").append(PUBLIC_ADDRESS_URL).toString()
            );
        } finally {

            if (conn != null)
                conn.disconnect();
        }

    }
}
