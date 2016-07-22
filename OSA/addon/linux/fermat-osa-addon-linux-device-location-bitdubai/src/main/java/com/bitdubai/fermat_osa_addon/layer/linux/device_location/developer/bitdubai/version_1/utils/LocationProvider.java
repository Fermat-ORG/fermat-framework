package com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.util.ip_address.CantGetCurrentIPAddressException;
import com.bitdubai.fermat_api.layer.all_definition.util.ip_address.IPAddressHelper;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.exceptions.CantAcquireLocationException;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.model.DeviceLocation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.LocationProvider</code>
 * is a helper class which contains methods related with location services.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class LocationProvider {

    private static final String GEOIP_NEKUDO_URL = "http://geoip.nekudo.com/api/";

    public static DeviceLocation acquireLocationThroughIP() throws CantAcquireLocationException {

        try {

            String ipAddress = IPAddressHelper.getCurrentIPAddress();

            return acquireLocationThroughIP(ipAddress);

        } catch (CantGetCurrentIPAddressException e) {

            throw new CantAcquireLocationException(
                    e,
                    "",
                    "There was a problem trying to get the current ip address."
            );
        } catch (CantAcquireLocationException e) {

            throw e;
        }
    }

    public static DeviceLocation acquireLocationThroughIP(final String ipAddress) throws CantAcquireLocationException {

        HttpURLConnection conn = null;

        try {

            conn = (HttpURLConnection) new URL(GEOIP_NEKUDO_URL + ipAddress).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            if (conn.getResponseCode() == 200) {

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(response);

                JsonObject location = (JsonObject) jsonObject.get("location");

                double latitude = location.get("latitude").getAsDouble();
                double longitude = location.get("longitude").getAsDouble();

                return new DeviceLocation(
                        latitude,
                        longitude,
                        null,
                        -1,
                        null,
                        System.currentTimeMillis(),
                        LocationSource.IP_CALCULATED
                );
            } else {
                throw new CantAcquireLocationException(
                        null,
                        "ipAddress: " + ipAddress,
                        "We couldn't find out the coordinates for the given ip address."
                );
            }
        } catch (IOException e) {

            throw new CantAcquireLocationException(
                    e,
                    "ipAddress: " + ipAddress,
                    "There was a problem trying to get the coordinates for the given ip address."
            );
        } finally {

            if (conn != null)
                conn.disconnect();
        }
    }
}
