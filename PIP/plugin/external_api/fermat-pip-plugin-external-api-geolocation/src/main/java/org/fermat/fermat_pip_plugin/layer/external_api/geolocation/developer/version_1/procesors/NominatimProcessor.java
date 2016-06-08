package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.NominatimJsonAttNames;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.exceptions.CantCreateGeoRectangleException;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.CountryDependencyRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.GeoRectangleRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util.RemoteJSonProcessor;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/06/16.
 */
public class NominatimProcessor extends AbstractAPIProcessor {

    private static final String queryUrl = GeolocationConfiguration.NOMINATIM_URL_PLACE_DETAILS;
    private static final String DEPENDENCY_TYPE0 = "state";
    private static final String DEPENDENCY_TYPE1 = "administrative";
    private static final int SOUTH_COORDINATE = 0;
    private static final int NORTH_COORDINATE = 1;
    private static final int WEST_COORDINATE = 2;
    private static final int EAST_COORDINATE = 3;

    public static CountryDependency setGeoRectangle(CountryDependency countryDependency)
            throws CantCreateGeoRectangleException {
        try{
            //First we get the dependency name.
            String dependencyName = countryDependency.getName();
            //Now, we're going to consult with nominatim API
            //System.out.println("NOMINATIM URL: "+queryUrl+dependencyName);
            JsonArray jsonArray = RemoteJSonProcessor.getJSonArray(queryUrl+dependencyName);
            GeoRectangle dependencyGeoRectangle = getGeoRectangleByJsonObject(
                    jsonArray,
                    dependencyName);
            CountryDependency fullCountryDependency = new CountryDependencyRecord(
                    countryDependency,
                    dependencyGeoRectangle);
            return fullCountryDependency;
        } catch (CantConnectWithExternalAPIException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting the Geo Rectangle from a Dependency",
                    "Cannot connect with Nominatim API");
        } catch (CantGetJSonObjectException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting the Geo Rectangle from a Dependency",
                    "Cannot get a JsonObject");
        }
    }

    /**
     * This method returns the GeoRectangle from a given Dependency name
     * @param jsonArray
     * @param dependencyName
     * @return
     * @throws CantCreateGeoRectangleException
     */
    private static GeoRectangle getGeoRectangleByJsonObject(
            JsonArray jsonArray,
            String dependencyName) throws CantCreateGeoRectangleException {
        String displayName;
        String type;
        //We check every array element to get the place we need
        for(JsonElement jsonElement : jsonArray){
            displayName = getStringFromJsonElement(jsonElement, NominatimJsonAttNames.DISPLAY_NAME);
            if(!displayName.contains(dependencyName)){
                continue;
            }
            type = getStringFromJsonElement(jsonElement, NominatimJsonAttNames.ELEMENT_TYPE);
            if(!(type.equalsIgnoreCase(DEPENDENCY_TYPE0)||type.equalsIgnoreCase(DEPENDENCY_TYPE1))){
                continue;
            }
            float[] coordinates = getArrayFloatFromJsonObject(
                    jsonElement.getAsJsonObject(),
                    NominatimJsonAttNames.GEO_RECTANGLE);
            GeoRectangle geoRectangle = new GeoRectangleRecord(
                    coordinates[NORTH_COORDINATE],
                    coordinates[SOUTH_COORDINATE],
                    coordinates[WEST_COORDINATE],
                    coordinates[EAST_COORDINATE]);
            return geoRectangle;
        }
        throw new CantCreateGeoRectangleException("Cannot find the GeoRectangle for "+dependencyName);
    }

}
