package com.bitdubai.fermat_pip_api.layer.external_api.interfaces;

import java.io.Serializable;

/**
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

}
