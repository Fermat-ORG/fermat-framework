package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.LocationAdapter</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class LocationAdapter extends TypeAdapter<Location> {

    @Override
    public Location read(final JsonReader in) throws IOException {
        final DeviceLocation location = new DeviceLocation();

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "lat":
                    location.setLatitude(Double.valueOf(in.nextString()));
                    break;
                case "lng":
                    location.setLongitude(Double.valueOf(in.nextString()));
                    break;
            }
        }
        in.endObject();

        return location;
    }

    @Override
    public void write(final JsonWriter out, final Location location) throws IOException {
        out.beginObject();
        out.name("lat").value(location != null ? location.getLatitude() : 0.0);
        out.name("lng").value(location != null ? location.getLongitude() : 0.0);
        out.endObject();
    }
}