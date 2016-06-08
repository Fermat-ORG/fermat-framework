package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public interface CountryDependency extends Serializable {

    /**
     * This method returns the dependency name.
     * @return
     */
    String getName();

    /**
     * This method returns the dependency toponym name
     * @return
     */
    String getToponymName();

    /**
     * This method returns the country code.
     * @return
     */
    String getCountryCode();

    /**
     * This method returns the country Id.
     * @return
     */
    int getCountryId();

    /**
     * This method return the dependency Id.
     * @return
     */
    int getDependencyId();

    /**
     * This method returns the dependency Geo Rectangle.
     * @return
     */
    GeoRectangle getGeoRectangle();

}
