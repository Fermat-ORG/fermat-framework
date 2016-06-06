package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public class CountryDependencyRecord implements CountryDependency, Serializable {

    private final String name;
    private final String toponymName;
    private final String countryCode;
    private final int countryId;
    private final int dependencyId;

    /**
     * Default constructor
     * @param name
     * @param toponymName
     * @param countryCode
     * @param countryId
     * @param dependencyId
     */
    public CountryDependencyRecord(
            String name,
            String toponymName,
            String countryCode,
            int countryId,
            int dependencyId) {
        this.name = name;
        this.toponymName = toponymName;
        this.countryCode = countryCode;
        this.countryId = countryId;
        this.dependencyId = dependencyId;
    }

    /**
     * This method returns the dependency name.
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method returns the dependency Toponym name.
     * @return
     */
    @Override
    public String getToponymName() {
        return toponymName;
    }

    /**
     * This method returns the country code.
     * @return
     */
    @Override
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * This method return the country geonames Id.
     * @return
     */
    @Override
    public int getCountryId() {
        return countryId;
    }

    /**
     * This method return the dependency Id.
     * @return
     */
    @Override
    public int getDependencyId() {
        return dependencyId;
    }
}
