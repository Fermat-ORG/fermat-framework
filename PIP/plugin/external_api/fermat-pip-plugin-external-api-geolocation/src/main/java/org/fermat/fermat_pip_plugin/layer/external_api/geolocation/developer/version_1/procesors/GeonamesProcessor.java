package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeonamesJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.exceptions.CannotGetCountryIdException;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util.RemoteJSonProcessor;

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
    public List<City> getContryDependenciesListByCountryCode(String countryCode){
        //We need to determinate the geonames country Id.
        int countryId = getGeonamesIdByCountryCode(countryCode);
    }

    private int getGeonamesIdByCountryCode(String countryCode)
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
