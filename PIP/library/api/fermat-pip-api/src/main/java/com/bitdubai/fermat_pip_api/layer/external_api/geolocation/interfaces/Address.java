package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/06/16.
 */
public interface Address extends Serializable {

    /**
     * This method returns the address road
     * @return
     */
    String getRoad();

    /**
     * This method returns the address Neighbourhood
     * @return
     */
    String getNeighbourhood();

    /**
     * This method returns the address city.
     * @return
     */
    String getCity();

    /**
     * This method returns the address county
     * @return
     */
    String getCounty();

    /**
     * This method returns the address state
     * @return
     */
    String getState();

    /**
     * This method returns the address county
     * @return
     */
    String getCountry();

    /**
     * This method returns the Country Code.
     * @return
     */
    String getCountryCode();

    /**
     * This method returns the Address GeoRectangle (including the latitude and longitude)
     * @return
     */
    GeoRectangle getGeoRectangle();
}
