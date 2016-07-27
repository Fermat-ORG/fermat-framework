package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.CityRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/06/16.
 */
public class CitiesProcessor {

    private static final String CITIES_FILENAME = "cities_shorted.csv";
    private static final String CSV_SEPARATOR = ";";
    private static final int CITY_NAME_INDEX = 0;
    private static final int CITY_LATITUDE_INDEX = 1;
    private static final int CITY_LONGITUDE_INDEX = 2;
    private static final int CITY_COUNTRY_CODE_INDEX = 3;

    /**
     * This method returns the cities list by country code
     * @param countryCode
     * @return
     * @throws CantGetCitiesListException
     */
    public static List<City> getCitiesByCountryCode(String countryCode)
            throws CantGetCitiesListException {
        try{
            ClassLoader classLoader = CitiesProcessor.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(CITIES_FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String read;
            String[] arrayLine;
            String countryCodeFromCSV;
            City city;
            List<City> cities = new ArrayList<>();
            float latitude;
            float longitude;
            while((read = bufferedReader.readLine()) != null) {
                arrayLine = read.split(CSV_SEPARATOR);
                try{
                    countryCodeFromCSV = arrayLine[CITY_COUNTRY_CODE_INDEX];
                } catch (ArrayIndexOutOfBoundsException e){
                    //wrong city line, I'll ignored
                    continue;
                }
                if (countryCodeFromCSV.equals(countryCode)){
                    try{
                        latitude = Float.parseFloat(arrayLine[CITY_LATITUDE_INDEX]);
                        longitude = Float.parseFloat(arrayLine[CITY_LONGITUDE_INDEX]);
                    } catch (NumberFormatException e){
                        //If we got this exception, the data is corrupted, I'll skip this city.
                        continue;
                    }
                    city = new CityRecord(
                            arrayLine[CITY_NAME_INDEX],
                            latitude,
                            longitude,
                            countryCodeFromCSV);
                    cities.add(city);
                }
            }
            bufferedReader.close();
            inputStream.close();
            if(cities.isEmpty()){
                throw new CantGetCitiesListException("The date from cities is null");
            }
            return cities;
        } catch (IOException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Error reading the data stream");
        }

    }

    /**
     * This method returns a city list filtered by city name.
     * @param filter
     * @return
     */
    public static List<City> getCitiesByFilter(String filter) throws CantGetCitiesListException {
        filter = filter.toLowerCase();
        try{
            ClassLoader classLoader = CitiesProcessor.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(CITIES_FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String read;
            String[] arrayLine;
            String cityNameFromCSV;
            City city;
            List<City> cities = new ArrayList<>();
            float latitude;
            float longitude;
            while((read = bufferedReader.readLine()) != null) {
                arrayLine = read.split(CSV_SEPARATOR);
                try{
                    cityNameFromCSV = arrayLine[CITY_NAME_INDEX];
                } catch (ArrayIndexOutOfBoundsException e){
                    //wrong city line, I'll ignored
                    continue;
                }
                if (cityNameFromCSV.toLowerCase().contains(filter)){
                    try{
                        latitude = Float.parseFloat(arrayLine[CITY_LATITUDE_INDEX]);
                        longitude = Float.parseFloat(arrayLine[CITY_LONGITUDE_INDEX]);
                    } catch (NumberFormatException e){
                        //If we got this exception, the data is corrupted, I'll skip this city.
                        continue;
                    }
                    city = new CityRecord(
                            cityNameFromCSV,
                            latitude,
                            longitude,
                            arrayLine[CITY_COUNTRY_CODE_INDEX]);
                    cities.add(city);
                }
            }
            bufferedReader.close();
            inputStream.close();
            return cities;
        } catch (IOException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Error reading the data stream");
        }
    }

}
