package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.exceptions.CantCreateCountryException;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.CountryRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeonosJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util.RemoteJSonProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class GeonosProcessor extends AbstractAPIProcessor {

    private static final String queryUrl = GeolocationConfiguration.EXTERNAL_API_LIST_ALL_COUNTRIES;
    private static final int OK_API_CODE = 200;
    private static final int NORTH_COORDINATE = 0;
    private static final int SOUTH_COORDINATE = 1;
    private static final int WEST_COORDINATE = 2;
    private static final int EAST_COORDINATE = 3;

    /**
     * This method returns all the countries from an external api
     * @return
     */
    public static HashMap<String, Country> getCountries()
            throws CantConnectWithExternalAPIException, CantGetJSonObjectException {
        //We request the list to the Geonos API
        JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(queryUrl);
        return getCountriesByJsonObject(jsonObject);
    }

    private static HashMap<String, Country> getCountriesByJsonObject(JsonObject jsonObject)
            throws CantConnectWithExternalAPIException {

        //Status code from API
        int statusCode = (int) getLongFromJsonObject(jsonObject, GeonosJsonAttNames.STATUS_CODE);
        if(statusCode!=OK_API_CODE){
            //We got an error from the API, I'll inform about that
            String statusMessage = getStringFromJsonObject(jsonObject, GeonosJsonAttNames.STATUS_MESSAGE);
            throw new CantConnectWithExternalAPIException("Message from API:\n"+
                    statusMessage+"\n"+
                    "Error Code:"+statusCode);
        }
        JsonObject jsonResults = getJsonObjectFromJsonObject(jsonObject, GeonosJsonAttNames.RESULTS);
        JsonObject jsonCountry;
        JsonObject jsonGeoRectangle;
        Set<Map.Entry<String, JsonElement>> resultsKeySet = jsonResults.entrySet();
        JsonElement jsonElement;
        String countryName;
        String countryShortName;
        HashMap<String, Country> countryList = new HashMap<>();
        Country country;
        float[] coordinates = new float[4];
        for(Map.Entry<String, JsonElement> entry : resultsKeySet){
            countryShortName = entry.getKey();
            jsonElement = entry.getValue();
            countryName = getStringFromJsonElement(jsonElement, GeonosJsonAttNames.COUNTRY_NAME);
            jsonCountry = getJsonObjectFromJsonObject(jsonResults, countryShortName);
            jsonGeoRectangle = getJsonObjectFromJsonObject(jsonCountry, GeonosJsonAttNames.GEO_RECTANGLE);
            coordinates[NORTH_COORDINATE] = (float) getDoubleFromJsonObject(
                    jsonGeoRectangle,
                    GeonosJsonAttNames.NORTH);
            coordinates[SOUTH_COORDINATE] = (float) getDoubleFromJsonObject(
                    jsonGeoRectangle,
                    GeonosJsonAttNames.SOUTH);
            coordinates[WEST_COORDINATE] = (float) getDoubleFromJsonObject(
                    jsonGeoRectangle,
                    GeonosJsonAttNames.WEST);
            coordinates[EAST_COORDINATE] = (float) getDoubleFromJsonObject(
                    jsonGeoRectangle,
                    GeonosJsonAttNames.EAST);
            try{
                country = new CountryRecord(countryName,countryShortName,coordinates);
                countryList.put(countryShortName, country);
            } catch (CantCreateCountryException e) {
                //If we got an error from this country, In this version, we'll not include it in the HashMap
                System.out.println("Geonos-Processor: The country labelled as " + countryShortName + " " +
                        "is not valid from geonos api");
                continue;
            }

        }
        return countryList;
    }

}
