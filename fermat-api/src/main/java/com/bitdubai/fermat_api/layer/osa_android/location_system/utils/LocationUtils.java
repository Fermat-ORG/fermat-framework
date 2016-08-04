package com.bitdubai.fermat_api.layer.osa_android.location_system.utils;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.Random;

/**
 * The class <code>com.bitdubai.fermat_api.layer.osa_android.location_system.utils.LocationUtils</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/2016.
 */
public class LocationUtils {

    public static Location getRandomLocation(final Location location,
                                             final long radius) {

        if (location == null)
            return null;

        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(location.getLongitude());

        double foundLongitude = new_x + location.getLatitude();
        double foundLatitude = y + location.getLongitude();

        if (location.getAccuracy() != -1)
            location.setAccuracy(radius);

        location.setLatitude(foundLatitude);
        location.setLongitude(foundLongitude);

        return location;
    }
}
