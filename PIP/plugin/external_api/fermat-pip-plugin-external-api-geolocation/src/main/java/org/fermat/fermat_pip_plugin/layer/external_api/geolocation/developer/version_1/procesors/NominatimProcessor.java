package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.NominatimJsonAttNames;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.AddressRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.CountryDependencyRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.GeoRectangleRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.util.RemoteJSonProcessor;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/06/16.
 */
public class NominatimProcessor extends AbstractAPIProcessor {

    private static final String queryUrl = GeolocationConfiguration.NOMINATIM_URL_PLACE_DETAILS;
    private static final String reverseQueryUrl = GeolocationConfiguration.NOMINATIM_URL_REVERSE_GEOLOCATION;
    private static final String DEPENDENCY_TYPE0 = "state";
    private static final String DEPENDENCY_TYPE1 = "administrative";
    private static final int SOUTH_COORDINATE = 0;
    private static final int NORTH_COORDINATE = 1;
    private static final int WEST_COORDINATE = 2;
    private static final int EAST_COORDINATE = 3;
    private static final float MAX_LATITUDE = 90;
    private static final float MIN_LATITUDE = -90;
    private static final float MAX_LONGITUDE = 180;
    private static final float MIN_LONGITUDE = -180;

    public static CountryDependency setGeoRectangle(CountryDependency countryDependency)
            throws CantCreateGeoRectangleException {
        try{
            //First we get the dependency name.
            String dependencyName = countryDependency.getName();
            //Now, we're going to consult with nominatim API
            //System.out.println("GEOLOCATION URL: "+queryUrl+dependencyName);
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
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            float[] coordinates = getArrayFloatFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.GEO_RECTANGLE);
            float latitude = (float) getDoubleFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.LATITUDE);
            float longitude = (float) getDoubleFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.LONGITUDE);
            GeoRectangle geoRectangle = new GeoRectangleRecord(
                    coordinates[NORTH_COORDINATE],
                    coordinates[SOUTH_COORDINATE],
                    coordinates[WEST_COORDINATE],
                    coordinates[EAST_COORDINATE],
                    latitude,
                    longitude);
            return geoRectangle;
        }
        throw new CantCreateGeoRectangleException("Cannot find the GeoRectangle for "+dependencyName);
    }

    /**
     * This method creates a GeoRectangle by a given location.
     * @param location
     * @return
     * @throws CantCreateGeoRectangleException
     */
    public static GeoRectangle getGeoRectangleByLocation(String location)
            throws CantCreateGeoRectangleException {
        try{
            //we're going to consult with nominatim API
            JsonArray jsonArray = RemoteJSonProcessor.getJSonArray(queryUrl + location);
            JsonElement jsonElement = jsonArray.get(0);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            float[] coordinates = getArrayFloatFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.GEO_RECTANGLE);
            float latitude = (float) getDoubleFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.LATITUDE);
            float longitude = (float) getDoubleFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.LONGITUDE);
            GeoRectangle geoRectangle = new GeoRectangleRecord(
                    coordinates[NORTH_COORDINATE],
                    coordinates[SOUTH_COORDINATE],
                    coordinates[WEST_COORDINATE],
                    coordinates[EAST_COORDINATE],
                    latitude,
                    longitude);
            return geoRectangle;
        } catch (CantConnectWithExternalAPIException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting geoRectangle from Nominatim API",
                    "Cannot connect with nominatim API");
        } catch (CantGetJSonObjectException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting geoRectangle from Nominatim API",
                    "Cannot get Json Object");
        }

    }

    /**
     * This method returns an Address by a given coordinates.
     * @param latitude
     * @param longitude
     * @return
     * @throws CantCreateAddressException
     */
    public static Address getAddressByCoordinate(float latitude, float longitude)
            throws CantCreateAddressException{
        //I'll check, first, that this coordinates [0,0] are in the argument
        //This coordinates are not valid in Nominatim API.
        if(latitude==0&&longitude==0){
            throw new CantCreateAddressException(
                    "The coordinates [0,0] are not valid in Nominatim API");
        }
        try{
            JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(
                    reverseQueryUrl+"&lat="+latitude+"&lon="+longitude);
            String errorMessage = getStringFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.ERROR);
            if(errorMessage.equals(NominatimJsonAttNames.ERROR_MESSAGE)){
                //If the coordinates can be processed through the API, I cannot handle with this situation
                throw new CantCreateAddressException(
                        "The coordinates [lat: "+latitude+"-lon:"+longitude+"] are not allowed by Nominatim API," +
                        " please check the coordinates source");
            }
            JsonObject jsonAddress = getJsonObjectFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.ADDRESS);
            if(jsonAddress==null||jsonAddress.isJsonNull()){
                throw new CantCreateAddressException(
                        "The coordinates [lat: "+latitude+"-lon:"+longitude+"] are not allowed by Nominatim API," +
                                " please check the coordinates source");
            }
            String road = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.ROAD);
            String neighbourhood = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.NEIGHBOURHOOD);
            String city = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.CITY);
            if(city==null||city.isEmpty()||city.equalsIgnoreCase("null")){
                //If city is null, we can try to get the town from json response.
                city = getStringFromJsonObject(
                        jsonAddress,
                        NominatimJsonAttNames.TOWN);
            }
            String county = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.COUNTY);
            String state = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.STATE);
            String country = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.COUNTRY);
            String countryCode = getStringFromJsonObject(
                    jsonAddress,
                    NominatimJsonAttNames.COUNTRY_CODE)
                    .toUpperCase();
            float[] coordinates = getArrayFloatFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.GEO_RECTANGLE);
            GeoRectangle geoRectangle = new GeoRectangleRecord(
                    coordinates[NORTH_COORDINATE],
                    coordinates[SOUTH_COORDINATE],
                    coordinates[WEST_COORDINATE],
                    coordinates[EAST_COORDINATE],
                    latitude,
                    longitude);
            Address address = new AddressRecord(
                    road,
                    neighbourhood,
                    city,
                    county,
                    state,
                    country,
                    countryCode,
                    geoRectangle);
            return address;
        } catch (CantConnectWithExternalAPIException e) {
            throw new CantCreateAddressException(
                    e,
                    "Getting an address from Nominatim API",
                    "Cannot connect with Nominatim API");
        } catch (CantGetJSonObjectException e) {
            throw new CantCreateAddressException(
                    e,
                    "Getting an address from Nominatim API",
                    "Cannot get a Json Object");
        }

    }

    /**
     * This method returns a random GeoRectangle.
     * @return
     */
    public static GeoRectangle getRandomGeLocation() throws CantCreateGeoRectangleException {
        try{
            float latitude = generateRandomNumberWithLimits(MAX_LATITUDE, MIN_LATITUDE);
            float longitude = generateRandomNumberWithLimits(MAX_LONGITUDE, MIN_LATITUDE);
            //System.out.println(reverseQueryUrl + "&lat=" + latitude + "&lon=" + longitude);
            JsonObject jsonObject = RemoteJSonProcessor.getJSonObject(
                    reverseQueryUrl + "&lat=" + latitude + "&lon=" + longitude);
            String errorMessage = getStringFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.ERROR);
            if(errorMessage.equals(NominatimJsonAttNames.ERROR_MESSAGE)){
                //System.out.println("GEOLOCATION:lat"+latitude+"-lon:"+longitude+" are wrong");
                //If the coordinates can be processed through the API, I'll try again
                return getRandomGeLocation();
            }
            //The coordinate is valid, so, I'll generate the GeoRectangle.
            float[] coordinates = getArrayFloatFromJsonObject(
                    jsonObject,
                    NominatimJsonAttNames.GEO_RECTANGLE);
            GeoRectangle geoRectangle = new GeoRectangleRecord(
                    coordinates[NORTH_COORDINATE],
                    coordinates[SOUTH_COORDINATE],
                    coordinates[WEST_COORDINATE],
                    coordinates[EAST_COORDINATE],
                    latitude,
                    longitude);
            return geoRectangle;

        } catch (CantConnectWithExternalAPIException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting random geoRectangle from Nominatim API",
                    "Cannot connect with nominatim API");
        } catch (CantGetJSonObjectException e) {
            throw new CantCreateGeoRectangleException(
                    e,
                    "Getting random geoRectangle from Nominatim API",
                    "Cannot get a JsonObject");
        }

    }

    /**
     * This method generates a random float between given max and min defined floats.
     * @param max
     * @param min
     * @return
     */
    private static float generateRandomNumberWithLimits(float max, float min){
        float randomNumber = (float) Math.random();
        float resultNumber = randomNumber*(max-min)+min;
        return resultNumber;
    }

}
