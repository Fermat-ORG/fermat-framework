package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.exceptions.CantCreateCountryException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class CountryRecord implements Country, Serializable {

    private final String countryName;
    private final String countryShortName;
    private final GeoRectangle geoRectangle;

    /**
     * Default constructor
     * For proper work the order in the coordinates array must be:
     * 0 - North
     * 1 - South
     * 2 - West
     * 3 - East
     * @param countryName
     * @param countryShortName
     */
    public CountryRecord(
            String countryName,
            String countryShortName,
            float[] coordinates) throws CantCreateCountryException {
        this.countryName = countryName;
        this.countryShortName = countryShortName;
        try{
            this.geoRectangle = new GeoRectangleRecord(coordinates);
        } catch (CantCreateGeoRectangleException e) {
            throw new CantCreateCountryException(
                    e,
                    "Creating Country Record",
                    "The array with coordinates is invalid");
        }

    }

    /**
     * This method returns the Country name.
     * @return
     */
    @Override
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method returns the country short name.
     * @return
     */
    @Override
    public String getCountryShortName() {
        return countryShortName;
    }

    /**
     * This method returns the Country geo-rectangles coordinates.
     * @return
     */
    public GeoRectangle getGeoRectangle(){
        return geoRectangle;
    }

    @Override
    public String toString() {
        return "CountryRecord{" +
                "countryName='" + countryName + '\'' +
                ", countryShortName='" + countryShortName + '\'' +
                ", geoRectangle=" + geoRectangle +
                '}';
    }
}
