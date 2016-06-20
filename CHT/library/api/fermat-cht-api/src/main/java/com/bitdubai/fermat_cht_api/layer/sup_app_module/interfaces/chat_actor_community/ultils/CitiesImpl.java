package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Franklin Marcano on 17/06/2016.
 * Modified by Roy on 20/06/2016
 */
public class CitiesImpl implements Cities, Serializable {

    //ATTRIBUTES
    private final String name;
    private final String countryCode;
    private final float latitude;
    private final float longitude;
    private final String countryName;
    private final String countryShortName;
    private final GeoRectangle mGeoRectangle;


    //CONSTRUCTOR
    public CitiesImpl (String name,
                       String countryCode,
                       float latitude,
                       float longitude,
                       String countryName,
                       String countryShortName,
                       GeoRectangle mGeoRectangle){
        this.name = name;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.countryShortName = countryShortName;
        this.mGeoRectangle = mGeoRectangle;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getLatitude(){
        return latitude;
    }

    @Override
    public float getLongitude(){
        return longitude;
    }

    @Override
    public String getCountryCode(){
        return countryCode;
    }

    @Override
    public String getCountryName(){
        return countryName;
    }

    @Override
    public String getCountryShortName(){
        return countryShortName;
    }

    @Override
    public GeoRectangle getGeoRectangle(){
        return mGeoRectangle;
    }

    @Override
    public String toString() {
        return "CityRecord{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public void setName (String newName){
        newName = name;
    }

    public void setLatitude (float newLatitude){
        newLatitude = latitude;
    }

    public void setLongitude (float newLongitude){
        newLongitude = longitude;
    }

    public void setCountryCode (String newCountryCode){
        newCountryCode = countryCode;
    }

    public void setCountryName (String newCountryName){
        newCountryName = countryName;
    }

    public void setCountryShortName (String newCountryShortName){
        newCountryShortName = countryShortName;
    }

    public void setGeoRectangle (GeoRectangle newGeoRectangle){
        newGeoRectangle = mGeoRectangle;
    }
}
