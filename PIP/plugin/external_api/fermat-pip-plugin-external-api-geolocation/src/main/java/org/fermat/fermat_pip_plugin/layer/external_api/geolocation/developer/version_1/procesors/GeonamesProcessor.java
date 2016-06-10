package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeonamesJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.exceptions.CannotGetCountryIdException;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.CountryDependencyRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util.RemoteJSonProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public class GeonamesProcessor extends AbstractAPIProcessor {

    /**
     * This method returns a cities list from a country by a given country code.
     * @param countryCode
     * @return
     */
    public static List<CountryDependency> getContryDependenciesListByCountryCode(
            String countryCode)
            throws CantGetCountryDependenciesListException {
        try{
            //We need to determinate the geonames country Id.
            int countryId = getGeonamesIdByCountryCode(countryCode);
            //Now, with the country Id we can get the country dependencies from a country
            List<CountryDependency> countryDependencies = getCountryDependenciesListByCountryId(
                    countryId,
                    countryCode);
            return countryDependencies;
        } catch (CantConnectWithExternalAPIException e) {
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies from a country",
                    "Cannot connect with geonames API");
        } catch (CantGetJSonObjectException e) {
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies from a country",
                    "Cannot get data from a Json Object");
        } catch (CannotGetCountryIdException e) {
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies from a country",
                    "Cannot get the contry Id");
        }
    }

    /**
     * This method creates a dependencies list by a given country Id and country code.
     * @param countryId
     * @param countryCode
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     */
    private static List<CountryDependency> getCountryDependenciesListByCountryId(
            int countryId,
            String countryCode)
            throws CantConnectWithExternalAPIException, CantGetJSonObjectException {
        //We request the country dependencies to Geonames API
        String queryUrl = GeolocationConfiguration.GEONAME_URL_COUNTRY_DEPENDENCIES+countryId+"&username="+GeolocationConfiguration.GEONAMES_USERNAME;
        JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(queryUrl);
        JsonArray jsonArray = jsonObject.getAsJsonArray(
                GeonamesJsonAttNames.COUNTRY_GEONAMES_DEPENDENCIES_ARRAY);
        List<CountryDependency> dependencyList = new ArrayList<>();
        CountryDependency countryDependency;
        for(JsonElement dependencyJson : jsonArray){
            countryDependency=getCountryDependency(
                    dependencyJson,
                    countryCode,
                    countryId);
            if(countryDependency!=null){
                dependencyList.add(countryDependency);
            }
        }
        return dependencyList;
    }

    /**
     * This method returns a CountryDependency object from a given JsonElement.
     * @param jsonElement
     * @param countryCode
     * @param countryId
     * @return
     */
    private static CountryDependency getCountryDependency(
            JsonElement jsonElement,
            String countryCode,
            int countryId){
        int dependencyId = (int) getLongFromJsonElement(
                jsonElement,
                GeonamesJsonAttNames.COUNTRY_GEONAMES_DEPENDENCY_ID);
        String toponymName = getStringFromJsonElement(
                jsonElement,
                GeonamesJsonAttNames.COUNTRY_GEONAMES_DEPENDENCY_TOPONYM_NAME);
        String name = getStringFromJsonElement(
                jsonElement,
                GeonamesJsonAttNames.COUNTRY_GEONAMES_DEPENDENCY_NAME);
        CountryDependency countryDependency = new CountryDependencyRecord(
                name,
                toponymName,
                countryCode,
                countryId,
                dependencyId);
        return countryDependency;
    }

    /**
     * This method returns a geonames Id by a country code.
     * @param countryCode
     * @return
     * @throws CannotGetCountryIdException
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     */
    private static int getGeonamesIdByCountryCode(String countryCode)
            throws CannotGetCountryIdException,
            CantConnectWithExternalAPIException,
            CantGetJSonObjectException {
        int geonamesCountryId;
        //We request the country information to Geonames API
        String queryUrl = GeolocationConfiguration.GEONAMES_URL_COUNTRY+countryCode+"&username="+GeolocationConfiguration.GEONAMES_USERNAME;
        JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(queryUrl);
        JsonArray countryArray = jsonObject.getAsJsonArray(
                GeonamesJsonAttNames.COUNTRY_GEONAMES_COUNTRY_ARRAY);
        String jsonCountryCode;
        for(JsonElement countryInfo :countryArray){
            jsonCountryCode = getStringFromJsonElement(
                    countryInfo,
                    GeonamesJsonAttNames.GEONAMES_COUNTRY_CODE);
            //Check if the code from Json is the same given as an argument
            if(jsonCountryCode.equals(countryCode)){
                geonamesCountryId = (int) getLongFromJsonElement(
                        countryInfo,
                        GeonamesJsonAttNames.GEONAMES_COUNTRY_ID);
                return geonamesCountryId;
            }
        }
        //We cannot find this country Code in geonames API
        throw new CannotGetCountryIdException(
                "The country code "+countryCode+" cannot be found in Geonames API");
    }

}
