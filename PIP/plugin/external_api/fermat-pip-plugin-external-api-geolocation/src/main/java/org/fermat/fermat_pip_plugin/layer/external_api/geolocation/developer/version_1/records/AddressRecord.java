package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/06/16.
 */
public class AddressRecord implements Address, Serializable {

    private final String road;
    private final String neighbourhood;
    private final String city;
    private final String county;
    private final String state;
    private final String country;
    private final String countryCode;
    private final GeoRectangle geoRectangle;

    public AddressRecord(
            String road,
            String neighbourhood,
            String city,
            String county,
            String state,
            String country,
            String countryCode,
            GeoRectangle geoRectangle) {
        this.road = road;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.county = county;
        this.state = state;
        this.country = country;
        this.countryCode = countryCode;
        this.geoRectangle = geoRectangle;
    }

    /**
     * This method returns the address road
     * @return
     */
    @Override
    public String getRoad() {
        return road;
    }

    /**
     * This method returns the address neighbourhood
     * @return
     */
    @Override
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * This method returns the address city
     * @return
     */
    @Override
    public String getCity() {
        return city;
    }

    /**
     * This method returns the address county
     * @return
     */
    @Override
    public String getCounty() {
        return county;
    }

    /**
     * This method returns the address state.
     * @return
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * This method returns the address country
     * @return
     */
    @Override
    public String getCountry() {
        return country;
    }

    /**
     * This method returns the Address Country code
     * @return
     */
    @Override
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * This method returns the address geoRectangle
     */
    @Override
    public GeoRectangle getGeoRectangle() {
        return geoRectangle;
    }

    @Override
    public String toString() {
        return "AddressRecord{" +
                "road='" + road + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", geoRectangle=" + geoRectangle +
                '}';
    }
}
