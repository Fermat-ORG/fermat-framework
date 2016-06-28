package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Franklin Marcano on 17/06/2016.
 * Modified by Roy on 20/06/2016
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 23/06/16.
 */
public class CitiesImpl implements Cities, Serializable {

    //ATTRIBUTES
    private final String name;
    private final String countryCode;
    private final double latitude;
    private final double longitude;
    private final String countryName;
    private final String countryShortName;
    private final GeoRectangle mGeoRectangle;


    //CONSTRUCTOR
    public CitiesImpl (String name,
                       String countryCode,
                       double latitude,
                       double longitude,
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
    public double getLatitude(){
        return latitude;
    }

    @Override
    public double getLongitude(){
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

    public void setLatitude (double newLatitude){
        newLatitude = latitude;
    }

    public void setLongitude (double newLongitude){
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
