package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1;

import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.Country;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class CountryRecord implements Country, Serializable {

    private final String countryName;
    private final String countryShortName;

    /**
     * Default constructor
     * @param countryName
     * @param countryShortName
     */
    public CountryRecord(
            String countryName,
            String countryShortName) {
        this.countryName = countryName;
        this.countryShortName = countryShortName;
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
}
