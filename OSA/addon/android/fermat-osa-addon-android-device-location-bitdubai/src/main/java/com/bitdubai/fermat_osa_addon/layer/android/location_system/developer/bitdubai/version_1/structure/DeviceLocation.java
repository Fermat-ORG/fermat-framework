package com.bitdubai.fermat_osa_addon.layer.android.location_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;

/**
 * Created by Natalia on 30/04/2015.
 */
public class DeviceLocation implements Location {

    /**
     * Location interface member variables.
     */

    private double lat;
    private double lng;
    private long time;
    private double altitude;
    private LocationProvider provider;

    // Public constructor declarations.

    /**
     * <p>DeviceLocation implementation constructor
     *
     * @param lat double actual device latitude
     * @param lng double actual device longitude
     * @param time long actual device location time
     * @param altitude double actual device  altitude
     * @param provider enum LocationProvider actual location provider network
     */
   public DeviceLocation(double lat, double lng, long time, double altitude, LocationProvider provider){
      this.lat = lat;
       this.lng = lng;
       this.time = time;
       this.altitude = altitude;
       this.provider = provider;
   }


    /**
     * Location interfaces implementation.
     */

    /**
     *<p>This method gets de actual device latitude
     *
     * @return double latitude
     */
    @Override
    public double getLatitude(){
        return lat;
    }

    /**
     *<p>This method gets de actual device longitude
     *
     * @return double longitude
     */
    @Override
    public double getLongitude(){
        return lng;
    }

    /**
     *<p>This method gets de actual device altitude
     *
     * @return double altitude
     */
    @Override
    public double getAltitude(){
        return altitude;
    }

    /**
     *<p>This method gets de actual device location time
     *
     * @return long time
     */
    @Override
    public long getTime(){
        return time;
    }

    /**
     *<p>This method gets de actual location network provider
     *
     * @return LocationProvider enum
     */
    @Override
    public LocationProvider getProvider(){
        return this.provider;
    }


}
