package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Franklin Marcano on 17/06/2016.
 */
public interface Cities extends Serializable {
    /**
     * This method returns the Country name.
     *
     * @return
     */
    String getCountryName();

    /**
     * This method returns the country short name.
     *
     * @return
     */
    String getCountryShortName();

    /**
     * This method returns the country geo-rectangle.
     *
     * @return
     */
    GeoRectangle getGeoRectangle();

    /**
     * This method returns the city name
     *
     * @return
     */
    String getName();

    /**
     * This method returns the city latitude.
     *
     * @return
     */
    double getLatitude();

    /**
     * This method returns the city longitude.
     *
     * @return
     */
    double getLongitude();

    /**
     * This method returns the country code from a city.
     *
     * @return
     */
    String getCountryCode();
}
