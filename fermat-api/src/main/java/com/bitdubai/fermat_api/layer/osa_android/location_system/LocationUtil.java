package com.bitdubai.fermat_api.layer.osa_android.location_system;

import java.io.Serializable;
import java.util.Random;

/**
*
 * */
public class LocationUtil implements Serializable {


    public static Location ramdomLocation(){
        final Random random = new Random();
        final double latitude = random.nextDouble();
        final double longitude = random.nextDouble();
        final double altitude = random.nextDouble();
        final long accurancy = random.nextInt();
        final long time = System.nanoTime();

        return new Location() {

            @Override
            public void setAccuracy(long accuracy) {

            }

            @Override
            public Double getLatitude() {
                return latitude;
            }

            @Override
            public Double getLongitude() {
                return longitude;
            }

            @Override
            public Double getAltitude() {
                return altitude;
            }

            @Override
            public long getAccuracy() {
                return accurancy;
            }

            @Override
            public void setLatitude(Double latitude) {

            }

            @Override
            public void setLongitude(Double longitude) {

            }

            @Override
            public Double getAltitudeAccuracy() {
                return null;
            }

            @Override
            public Long getTime() {
                return time;
            }

            @Override
            public LocationSource getSource() {
                return LocationSource.GPS;
            }
        };
    }

}
