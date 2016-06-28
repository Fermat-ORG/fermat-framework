package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/06/16.
 */
public class ExtendedCityRecord implements ExtendedCity,Serializable {

    private final String name;
    private final float latitude;
    private final float longitude;
    private final String countryCode;
    private final String countryName;
    private final String countryShortName;
    private final GeoRectangle geoRectangle;

    public ExtendedCityRecord(City city, Country country){
        this.name = city.getName();
        this.latitude = city.getLatitude();
        this.longitude = city.getLongitude();
        this.countryCode = city.getCountryCode();
        this.countryName = country.getCountryName();
        this.countryShortName = country.getCountryShortName();
        this.geoRectangle = country.getGeoRectangle();
    }

    /**
     * This method returns the city name
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method returns the city latitude.
     * @return
     */
    @Override
    public float getLatitude() {
        return latitude;
    }

    /**
     * This method returns the city longitude.
     * @return
     */
    @Override
    public float getLongitude() {
        return longitude;
    }

    /**
     * This method returns the country code from a city.
     * @return
     */
    @Override
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * This method returns the country name.
     * @return
     */
    @Override
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method returns the country short name
     * @return
     */
    @Override
    public String getCountryShortName() {
        return countryShortName;
    }

    /**
     * This method returns the country GeoRectangle.
     * @return
     */
    @Override
    public GeoRectangle getGeoRectangle() {
        return geoRectangle;
    }

    @Override
    public String toString() {
        return "ExtendedCityRecord{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryShortName='" + countryShortName + '\'' +
                ", geoRectangle=" + geoRectangle +
                '}';
    }
}
