package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public interface City extends Serializable{

    /**
     * This method returns the city name
     * @return
     */
    String getName();

    /**
     * This method returns the city latitude.
     * @return
     */
    float getLatitude();

    /**
     * This method returns the city longitude.
     * @return
     */
    float getLongitude();

    /**
     * This method returns the country code from a city.
     * @return
     */
    String getCountryCode();

}
