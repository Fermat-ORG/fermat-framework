package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.Country;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.exceptions.CantCreateCountryException;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.records.CountryRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config.GeonosJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config.NominatimConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.util.RemoteJSonProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class GeonosProcessor extends AbstractAPIProcessor {

    private static final String queryUrl = NominatimConfiguration.EXTERNAL_API_LIST_ALL_COUNTRIES;
    private static final int OK_API_CODE = 200;

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
        Set<Map.Entry<String, JsonElement>> resultsKeySet = jsonResults.entrySet();
        JsonElement jsonElement;
        String countryName;
        String countryShortName;
        HashMap<String, Country> countryList = new HashMap<>();
        Country country;
        float[] coordinates;
        for(Map.Entry<String, JsonElement> entry : resultsKeySet){
            countryShortName = entry.getKey();
            jsonElement = entry.getValue();
            countryName = getStringFromJsonElement(jsonElement, GeonosJsonAttNames.COUNTRY_NAME);
            coordinates = getArrayIntFromJsonObject(jsonObject, GeonosJsonAttNames.GEO_RECTANGLE);
            try{
                country = new CountryRecord(countryName,countryShortName,coordinates);
                countryList.put(countryShortName, country);
            } catch (CantCreateCountryException e) {
                //If we got an error from this country, In this version, we'll not include it in the HashMap
                System.out.print("Geonos-Processor: The country labelled as "+countryShortName+" " +
                        "is not valid from geonos api");
                continue;
            }

        }
        return countryList;
    }

}
