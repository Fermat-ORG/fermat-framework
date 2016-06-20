package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Franklin Marcano on 17/06/2016.
 */
public class CitiesImpl implements Cities, Serializable {

    //ATTRIBUTES
    private final String name;
    private final String countryCode;

    //CONSTRUCTOR
    public CitiesImpl (String name, String countrycode){
        this.name = name;
        this.countryCode = countrycode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String getCountryName() {
        return null;
    }

    @Override
    public String getCountryShortName() {
        return null;
    }

    @Override
    public GeoRectangle getGeoRectangle() {
        return null;
    }

    @Override
    public float getLatitude() {
        return 0;
    }

    @Override
    public float getLongitude() {
        return 0;
    }


}
