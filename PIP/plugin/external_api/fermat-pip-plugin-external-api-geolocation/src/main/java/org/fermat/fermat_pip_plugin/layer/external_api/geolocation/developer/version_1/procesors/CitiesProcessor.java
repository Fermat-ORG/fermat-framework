package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;

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
//TODO> change all of this, cannot be used, the xml file is too big, try with csv
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
            StringBuilder stringBuilder =new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String read;
            while((read = bufferedReader.readLine()) != null) {
                stringBuilder.append(read);
            }
            bufferedReader.close();
            inputStream.close();
            String fileData = bufferedReader.toString();
            if(fileData!=null||!fileData.isEmpty()){
                List<City> cities = new ArrayList<>();
                cities = (List<City>) XMLParser.parseXML(fileData, cities);
                List<City> filteredCities = new ArrayList<>();
                String countryCodeFromCity;
                for(City city : cities){
                    countryCodeFromCity = city.getCountryCode();
                    if(countryCodeFromCity.equals(countryCode)){
                        filteredCities.add(city);
                    }
                }
                return filteredCities;
            }
            throw new CantGetCitiesListException("The date from cities is null");
        } catch (IOException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Error reading the data stream");
        }

    }

}
