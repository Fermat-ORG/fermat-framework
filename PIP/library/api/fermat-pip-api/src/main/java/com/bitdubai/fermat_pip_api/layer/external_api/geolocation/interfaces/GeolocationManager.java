package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public interface GeolocationManager<
        T extends Country,
        K extends CountryDependency,
        M extends City> extends FermatManager {

    /**
     * This method returns a list of Countries available in an external api
     * @return
     */
    HashMap<String, T> getCountryList()
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException;

    /**
     * This method returns the dependencies from a country available in an external api.
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    List<K> getCountryDependencies(String countryCode)
            throws CantGetCountryDependenciesListException,
            CantConnectWithExternalAPIException,
            CantCreateBackupFileException;

    /**
     * This method returns the cities by a given country Code
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    List<M> getCitiesByCountryCode(String countryCode) throws CantGetCitiesListException;

}
