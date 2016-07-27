package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * A GeoRectangle object contains a rectangle that evolves the location.
 * This rectangle can be drawn with 4 points: North, South, East and West.
 * Also, the GeoRectangle contains the latitude and longitude of the center of the rectangle.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/06/16.
 */
public interface GeoRectangle extends Serializable {

    /**
     * This method returns the North coordinate.
     * @return
     */
    float getNorth();

    /**
     * This method returns the South coordinate.
     * @return
     */
    float getSouth();

    /**
     * This method returns the West Coordinate.
     * @return
     */
    float getWest();

    /**
     * This method returns the East Coordinate
     * @return
     */
    float getEast();

    /**
     * This method returns the latitude.
     * @return
     */
    float getLatitude();

    /**
     * This method return the longitude.
     * @return
     */
    float getLongitude();

}
