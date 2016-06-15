package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public interface Country extends Serializable {

    /**
     * This method returns the Country name.
     * @return
     */
    String getCountryName();

    /**
     * This method returns the country short name.
     * @return
     */
    String getCountryShortName();

    /**
     * This method returns the country geo-rectangle.
     * @return
     */
    GeoRectangle getGeoRectangle();

}
