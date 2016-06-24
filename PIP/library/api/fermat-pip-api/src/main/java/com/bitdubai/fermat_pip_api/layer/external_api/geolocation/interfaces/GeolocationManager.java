package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
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
        M extends City,
        E extends ExtendedCity,
        G extends GeoRectangle,
        A extends Address> extends FermatManager {

    /**
     * This method returns a list of Countries available in an external api
     * @return
     */
    HashMap<String, T> getCountryList()
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException;

    /**
     * This method returns a list of Countries available in an external api by a given filter
     * @return
     */
    HashMap<String, T> getCountryListByFilter(String filter)
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

    /**
     * This method returns the cities list filtered by a given filter.
     * @param filter
     * @return
     * @throws CantGetCitiesListException
     */
    List<M> getCitiesByFilter(String filter) throws CantGetCitiesListException;

    /**
     * This method returns the extended cities list filtered by a given filter.
     * @param filter
     * @return
     * @throws CantGetCitiesListException
     */
    List<E> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException;

    /**
     * This method returns the cities list by a given country code and dependency name
     * @param countryCode
     * @return
     * @throws CantGetCitiesListException
     */
    List<M> getCitiesByCountryCodeAndDependencyName(String countryCode, String dependencyName)
            throws CantGetCitiesListException, CantCreateCountriesListException;

    /**
     * This method returns the GeoRectangle owned by a given location.
     * A GeoRectangle object contains a rectangle that evolves the location.
     * This rectangle can be drawn with 4 points: North, South, East and West.
     * Also, the GeoRectangle contains the latitude and longitude of the center of the rectangle.
     * @param location
     * @return
     */
    G getGeoRectangleByLocation(String location) throws CantCreateGeoRectangleException;

    /**
     * This method returns an address by a given latitude and longitude.
     * The address contains a GeoRectangle object.
     * @param latitude
     * @param longitude
     * @return
     * @throws CantCreateAddressException
     */
    A getAddressByCoordinate(float latitude, float longitude) throws CantCreateAddressException;

    /**
     * This method returns an address by a given latitude and longitude.
     * The address contains a GeoRectangle object.
     * @param latitude
     * @param longitude
     * @return
     * @throws CantCreateAddressException
     */
    A getAddressByCoordinate(double latitude, double longitude) throws CantCreateAddressException;

    /**
     * This method returns a random geo location represented in a GeoRectangle object.
     * @return
     * @throws CantCreateGeoRectangleException
     */
    G getRandomGeoLocation() throws CantCreateGeoRectangleException;

}
