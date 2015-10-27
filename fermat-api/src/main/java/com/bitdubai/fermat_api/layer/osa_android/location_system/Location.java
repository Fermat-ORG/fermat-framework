package com.bitdubai.fermat_api.layer.osa_android.location_system;

/**
 *
 *  <p>The public interfaces <code>Location</code> is a interface
 *     that define the methods to get the geolocation data.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   30/04/15.
 * */
public interface Location {

    Double getLatitude();

    Double getLongitude();

    Double getAltitude();

    /**
     * @return the last update time of the coordinates.
     */
    Long getTime();

    /**
     * @return the provider that you use to get the coordinates, gps, wifi, etc.
     */
    LocationProvider getProvider();

}
