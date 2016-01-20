/*
 * @#DistanceCalculator.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.util.DistanceCalculator</code> has the
 * method that calculates the distance between two points (given the latitude/longitude of those points). It is being used to calculate the distance between two
 * locations
 *
 *  Definitions:
 *    South latitudes are negative, east longitudes are positive
 *
 *  Passed to function:
 *    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)
 *    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)
 *    unit = the unit you desire for results
 *           where: 'M' is statute miles (default)
 *                  'K' is kilometers
 *                  'N' is nautical miles
 *
 * <p/>
 *
 * Created by http://www.geodatasource.com
 * Update by Roberto Requena - (rart3001@gmail.com) on 17/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DistanceCalculator {

    /**
     * Represent the unit on miles
     */
    public static String MILES = "M";

    /**
     * Represent the unit on kilometers
     */
    public static String KILOMETERS = "K";

    /**
     * Represent the unit on nautical miles
     */
    public static String NAUTICAL_MILES = "N";

    public static void main (String[] args) throws java.lang.Exception
    {

        Location pointOne = new Location() {
            @Override
            public Double getLatitude() {
                return 32.9697;
            }

            @Override
            public Double getLongitude() {
                return -96.80322;
            }

            @Override
            public Double getAltitude() {
                return 0.0;
            }

            @Override
            public Long getTime() {
                return new Long(0);
            }

            @Override
            public LocationProvider getProvider() {
                return null;
            }
        };


        Location pointTwo = new Location() {
            @Override
            public Double getLatitude() {
                return 29.46786;
            }

            @Override
            public Double getLongitude() {
                return -98.53506;
            }

            @Override
            public Double getAltitude() {
                return 0.0;
            }

            @Override
            public Long getTime() {
                return new Long(0);
            }

            @Override
            public LocationProvider getProvider() {
                return null;
            }
        };

        System.out.println(distance(pointOne, pointTwo, DistanceCalculator.MILES) + " Miles\n");
        System.out.println(distance(pointOne, pointTwo, DistanceCalculator.KILOMETERS) + " Kilometers\n");
        System.out.println(distance(pointOne, pointTwo, DistanceCalculator.NAUTICAL_MILES) + " Nautical Miles\n");
    }

    /**
     * This method calculate the distance between two location points
     *
     * @param pointOne
     * @param pointTwo
     * @param unit
     * @return distance
     */
    public static double distance(Location pointOne, Location pointTwo, String unit) {

        double theta = pointOne.getLongitude() - pointTwo.getLongitude();
        double dist = Math.sin(deg2rad(pointOne.getLatitude())) * Math.sin(deg2rad(pointTwo.getLatitude())) + Math.cos(deg2rad(pointOne.getLatitude())) * Math.cos(deg2rad(pointTwo.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == DistanceCalculator.KILOMETERS) {
            dist = dist * 1.609344;
        } else if (unit == DistanceCalculator.NAUTICAL_MILES) {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /**
     * Converts decimal degrees to radians
     * @param deg
     * @return double
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * converts radians to decimal degrees
     * @param rad
     * @return double
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
