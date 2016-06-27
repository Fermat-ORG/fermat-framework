package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/06/16.
 */
public interface ExtendedCity extends City, Serializable {

    /**
     * This method returns the country name.
     * @return
     */
    String getCountryName();

    /**
     * This method returns the country short name
     * @return
     */
    String getCountryShortName();

    /**
     * This method returns the country GeoRectangle.
     * @return
     */
    GeoRectangle getGeoRectangle();

}
