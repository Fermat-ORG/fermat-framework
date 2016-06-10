package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public class CityRecord implements City, Serializable {

    private final String name;
    private final float latitude;
    private final float longitude;
    private final String countryCode;

    public CityRecord(
            String name,
            float latitude,
            float longitude,
            String countryCode) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryCode = countryCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
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
}
