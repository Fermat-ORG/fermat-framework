package com.bitdubai.fermat_api.layer.osa_android.location_system;

import java.io.Serializable;

/**
 * <p>The public interfaces <code>Location</code> is a interface
 * that define the methods to get the geolocation data.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 30/04/15.
 */
public interface Location extends Serializable {

    Double getLatitude();

    Double getLongitude();

    Double getAltitude();

    // when we can't know how exact it is, the returning value is -1
    long getAccuracy();

    void setLatitude(Double latitude);

    void setLongitude(Double longitude);

    void setAccuracy(long accuracy);

    Double getAltitudeAccuracy();

    /**
     * @return the last update time of the coordinates.
     */
    Long getTime();

    /**
     * @return the source that you use to get the coordinates, gps, wifi, etc.
     */
    LocationSource getSource();

}
