package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.Country;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.CountryRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config.GeonosJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config.NominatimConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.util.RemoteJSonProcessor;

import java.util.ArrayList;
import java.util.List;
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
    public static List<Country> getCountries()
            throws CantConnectWithExternalAPIException, CantGetJSonObjectException {
        //We request the list to the Geonos API
        JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(queryUrl);
        return getCountriesByJsonObject(jsonObject);
    }

    private static List<Country> getCountriesByJsonObject(JsonObject jsonObject)
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
        List<Country> countryList = new ArrayList<>();
        Country country;
        for(Map.Entry<String, JsonElement> entry : resultsKeySet){
            countryShortName = entry.getKey();
            jsonElement = entry.getValue();
            countryName = getStringFromJsonElement(jsonElement, GeonosJsonAttNames.COUNTRY_NAME);
            country = new CountryRecord(countryName,countryShortName);
            countryList.add(country);
        }
        return countryList;
    }

}
