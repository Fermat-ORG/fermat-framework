package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.GeolocationPluginRoot;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.CitiesProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.GeonamesProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.GeonosProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.NominatimProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.records.ExtendedCityRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class GeolocationPluginManager implements GeolocationManager {

    /**
     * Represents the plugin root class
     */
    GeolocationPluginRoot geolocationPluginRoot;

    /**
     * Represents the plugin file system
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Represents plugin Id.
     */
    UUID pluginId;

    //Configuration variables
    /**
     * Represents the file life span of countries backup.
     */
    FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;

    /**
     * Represents the file privacy of countries backup.
     */
    FilePrivacy FILE_PRIVACY = FilePrivacy.PRIVATE;

    /**
     * Constructor with parameters
     * @param geolocationPluginRoot
     * @param pluginFileSystem
     */
    public GeolocationPluginManager(
            GeolocationPluginRoot geolocationPluginRoot,
            PluginFileSystem pluginFileSystem,
            UUID pluginId){
        this.geolocationPluginRoot = geolocationPluginRoot;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    @Override
    public HashMap<String, Country> getCountryList()
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException {

        HashMap<String, Country> countriesList = new HashMap<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countriesList = createCountriesBackupFile();
                return countriesList;
            }
            //The file exists we're gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCountriesData = backupFile.getContent();
            if(stringCountriesData==null||stringCountriesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            countriesList = (HashMap<String, Country>) XMLParser.parseXML(
                    stringCountriesData,
                    countriesList);
            return countriesList;
        } catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method returns a list of Countries available in an external api by a given filter
     * @return
     */
    @Override
    public HashMap getCountryListByFilter(String filter)
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException {
        HashMap<String, Country> countriesList = new HashMap<>();
        String pathFilter = filter.toLowerCase().replace(" ","-");
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.FILTERED_COUNTRIES_BACKUP_FILE+pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countriesList = createCountryListByFilterBackupFile(filter, pathFilter);
                return countriesList;
            }
            //The file exists we're gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.FILTERED_COUNTRIES_BACKUP_FILE + pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCountriesData = backupFile.getContent();
            if(stringCountriesData==null||stringCountriesData.isEmpty()){
                //I'll return an empty list
                return countriesList;
            }
            countriesList = (HashMap<String, Country>) XMLParser.parseXML(
                    stringCountriesData,
                    countriesList);
            return countriesList;
        } catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the filtered countries list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the the filtered countries list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates a backup file with the filtered country hashmap.
     * @param filter
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantCreateBackupFileException
     * @throws CantCreateCountriesListException
     */
    private HashMap createCountryListByFilterBackupFile(String filter, String pathFilter)
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException {
        try{
            filter = filter.toLowerCase();
            //Get the country hashMap
            HashMap<String, Country> countryHashMap = getCountryList();
            HashMap<String, Country> filteredCountryHashMap = new HashMap<>();
            Country country;
            String countryCode;
            String countryName;
            //We're going to create a filtered Map
            for(Map.Entry<String, Country> entry: countryHashMap.entrySet()){
                country = entry.getValue();
                countryName = country.getCountryName();
                if(countryName.toLowerCase().contains(filter)){
                    countryCode = entry.getKey();
                    filteredCountryHashMap.put(countryCode,country);
                }
            }
            //Parse the countries list to XML
            String countriesListXML = XMLParser.parseObject(filteredCountryHashMap);
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.FILTERED_COUNTRIES_BACKUP_FILE + pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(countriesListXML);
            backupFile.persistToMedia();
            return filteredCountryHashMap;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot create the backup file in the device");
        }

    }

    /**
     * This method creates invokes the geonos API and create a file with the countries list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private HashMap<String, Country> createCountriesBackupFile()
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException {
        //We ask for the country list in geonos API
        HashMap<String, Country> countriesList = GeonosProcessor.getCountries();
        //Parse the countries list to XML
        String countriesListXML = XMLParser.parseObject(countriesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(countriesListXML);
            backupFile.persistToMedia();
            return countriesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot create the backup file in the device");
        }
    }

    /**
     * This method returns the dependencies from a country available in an external api.
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    public List<CountryDependency> getCountryDependencies(String countryCode)
            throws CantGetCountryDependenciesListException,
            CantConnectWithExternalAPIException,
            CantCreateBackupFileException {
        List<CountryDependency> countryDependencies = new ArrayList<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_DEPENDENCIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countryDependencies = createDependenciesBackupFile(countryCode);
                return countryDependencies;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_DEPENDENCIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringDependenciesData = backupFile.getContent();
            if(stringDependenciesData==null||stringDependenciesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            countryDependencies = (List<CountryDependency>) XMLParser.parseXML(
                    stringDependenciesData,
                    countryDependencies);
            return countryDependencies;
        }  catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geoname API and create a file with the dependencies list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private List<CountryDependency> createDependenciesBackupFile(String countryCode)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCountryDependenciesListException {
        //We ask for the country list in geonames API
        List<CountryDependency> dependenciesList = GeonamesProcessor.
                getContryDependenciesListByCountryCode(countryCode);
        //Parse the dependencies list to XML
        String dependenciesListXML = XMLParser.parseObject(dependenciesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE + countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return dependenciesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with dependencies list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with dependencies list",
                    "Cannot create the backup file in the device");
        }
    }

    /**
     * This method returns the cities by a given country Code
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    public List<City> getCitiesByCountryCode(String countryCode)
            throws CantGetCitiesListException {
        List<City> citiesList = new ArrayList<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                citiesList = createCitiesBackupFile(countryCode);
                return citiesList;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE + countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCitiesData = backupFile.getContent();
            if(stringCitiesData==null||stringCitiesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            citiesList = (List<City>) XMLParser.parseXML(
                    stringCitiesData,
                    citiesList);
            return citiesList;
        }  catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Unexpected Exception");
        }
    }

    @Override
    public List<City> getCitiesByFilter(String filter) throws CantGetCitiesListException {
        List<City> citiesList = new ArrayList<>();
        try{
            String pathFilter = filter.toLowerCase().replace(" ", "-");
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.FILTERED_CITIES_BACKUP_FILE+pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                citiesList = createFilteredCitiesBackupFile(filter, pathFilter);
                return citiesList;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.FILTERED_CITIES_BACKUP_FILE + pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCitiesData = backupFile.getContent();
            if(stringCitiesData==null||stringCitiesData.isEmpty()){
                //I'll return an empty list
                return citiesList;
            }
            citiesList = (List<City>) XMLParser.parseXML(
                    stringCitiesData,
                    citiesList);
            return citiesList;
        }  catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geoname API and create a file with the dependencies list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private List<City> createFilteredCitiesBackupFile(String filter, String pathFilter)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCitiesListException {
        //We ask for the country list in geonames API
        List<City> citiesList = CitiesProcessor.
                getCitiesByFilter(filter);
        //Parse the dependencies list to XML
        String dependenciesListXML = XMLParser.parseObject(citiesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.FILTERED_CITIES_BACKUP_FILE + pathFilter,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return citiesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot create the backup file in the device");
        }
    }

    /**
     * This method returns the cities list filtered by a given filter.
     * @param filter
     * @return
     * @throws CantGetCitiesListException
     */
    @Override
    public List<ExtendedCity> getExtendedCitiesByFilter(String filter)
            throws CantGetCitiesListException {
        try{
            List<ExtendedCity> extendedCityList = new ArrayList<>();
            //First, we get the countries Map
            HashMap<String, Country> allCountryList = getCountryList();
            //Now, we get the filtered country list
            HashMap<String, Country> filteredCountryList = getCountryListByFilter(filter);
            //We get the filtered cities list
            List<City> filteredCityList = getCitiesByFilter(filter);
            //We're gonna include all this cities in extendedCityList
            ExtendedCity extendedCity;
            String countryCode;
            Country country;
            for(City city : filteredCityList){
                countryCode = city.getCountryCode();
                country = allCountryList.get(countryCode);
                System.out.println("GEOLOCATION:"+city+" - "+country);
                if(country==null){
                    //In theory, it cannot happen, but, we will continue
                    continue;
                }
                extendedCity = new ExtendedCityRecord(city,country);
                extendedCityList.add(extendedCity);
            }
            //Now, I'll include all the information got in filteredCountryList.
            List<City> cityListByCountryCode;
            for(Map.Entry<String, Country> entry: filteredCountryList.entrySet()){
                countryCode = entry.getKey();
                cityListByCountryCode = getCitiesByCountryCode(countryCode);
                for(City city : cityListByCountryCode){
                    countryCode = city.getCountryCode();
                    country = allCountryList.get(countryCode);
                    extendedCity = new ExtendedCityRecord(city,country);
                    extendedCityList.add(extendedCity);
                }
            }
            return extendedCityList;
        } catch (CantConnectWithExternalAPIException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot connect with external api");
        } catch (CantCreateCountriesListException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot create country list");
        } catch (CantCreateBackupFileException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot create backup file");
        }
    }

    /**
     * This method creates invokes the geoname API and create a file with the dependencies list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private List<City> createCitiesBackupFile(String countryCode)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCitiesListException {
        //We ask for the country list in geonames API
        List<City> citiesList = CitiesProcessor.
                getCitiesByCountryCode(countryCode);
        //Parse the dependencies list to XML
        String dependenciesListXML = XMLParser.parseObject(citiesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE + countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return citiesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot create the backup file in the device");
        }
    }

    public List<City> getCitiesByCountryCodeAndDependencyName(
            String countryName,
            String dependencyName)
            throws CantGetCitiesListException,
            CantCreateCountriesListException {
        List<City> citiesList = new ArrayList<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.DEPENDENCY_PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE + dependencyName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                citiesList = createCitiesBackupFile(
                        countryName,
                        dependencyName);
                return citiesList;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.DEPENDENCY_PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE + dependencyName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCitiesData = backupFile.getContent();
            if(stringCitiesData==null||stringCitiesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            citiesList = (List<City>) XMLParser.parseXML(
                    stringCitiesData,
                    citiesList);
            return citiesList;
        } catch (CantCreateFileException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot create backup file");
        } catch (CantConnectWithExternalAPIException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot connect with external API");
        } catch (CantCreateBackupFileException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot create backup file");
        } catch (CantGetJSonObjectException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot get a Json Object");
        } catch (FileNotFoundException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot find the file");
        } catch (CantLoadFileException e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot load the file");
        } catch (Exception e) {
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Unexpected Exception");
        }
    }

    private List<City> createCitiesBackupFile(
            String countryName,
            String dependencyName)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCitiesListException  {
        List<City> citiesList = new ArrayList<>();
        try{
            List<CountryDependency> countryDependencies = getCountryDependencies(countryName);
            for(CountryDependency countryDependency : countryDependencies){
                if(countryDependency.getName().equalsIgnoreCase(dependencyName)){
                    CountryDependency fullCountryDependency = NominatimProcessor.setGeoRectangle(
                            countryDependency);
                    System.out.println(fullCountryDependency);
                    citiesList=getCitiesByCountryDependency(fullCountryDependency, countryName);
                }
            }
            //Parse the dependencies list to XML
            String dependenciesListXML = XMLParser.parseObject(citiesList);
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.DEPENDENCY_PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE + dependencyName,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return citiesList;
        } catch (CantCreateGeoRectangleException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot create the dependency GeoRectangle");
        }  catch (CantGetCountryDependenciesListException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot get the country dependency");
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot create the backup file in the device");
        }

    }

    /**
     * This method returns a list of cities belong to a country dependency.
     * @param countryDependency
     * @param countryCode
     * @return
     * @throws CantGetCitiesListException
     */
    private List<City> getCitiesByCountryDependency(
            CountryDependency countryDependency,
            String countryCode) throws CantGetCitiesListException {
        //First, we get the cities list from the country code.
        List<City> citiesByCountryCode = getCitiesByCountryCode(countryCode);
        //Now, we check which cities is in the country dependency
        GeoRectangle geoRectangle = countryDependency.getGeoRectangle();
        List<City> returnedCitiesList = new ArrayList<>();
        for(City city : citiesByCountryCode){
            if(checkCoordinates(city,geoRectangle)){
                returnedCitiesList.add(city);
            }
        }
        return returnedCitiesList;

    }

    /**
     * This method checks if the cities coordinates belong to a country dependency.
     * @param city
     * @param geoRectangle
     * @return
     */
    private boolean checkCoordinates(City city, GeoRectangle geoRectangle){
        float cityLatitude = city.getLatitude();
        float cityLongitude = city.getLongitude();
        float north = geoRectangle.getNorth();
        float south = geoRectangle.getSouth();
        float west = geoRectangle.getWest();
        float east = geoRectangle.getEast();
        //System.out.println("GEOLOCATION GEO-R:"+geoRectangle);
        //System.out.println("GEOLOCATION CITI:"+city);
        //Check latitude
        boolean isLatitudeOk = north>=cityLatitude&&south<=cityLatitude;
        boolean isLongitudeOk = east>=cityLongitude&&west<=cityLongitude;
        return isLatitudeOk&&isLongitudeOk;
    }

    /**
     * This method returns the GeoRectangle owned by a given location.
     * A GeoRectangle object contains a rectangle that evolves the location.
     * This rectangle can be drawn with 4 points: North, South, East and West.
     * Also, the GeoRectangle contains the latitude and longitude of the center of the rectangle.
     * @param location
     * @return
     */
    public GeoRectangle getGeoRectangleByLocation(String location)
            throws CantCreateGeoRectangleException {
        return NominatimProcessor.getGeoRectangleByLocation(location);
    }

    /**
     * This method returns an address by a given latitude and longitude.
     * The address contains a GeoRectangle object.
     * @param latitude
     * @param longitude
     * @return
     * @throws CantCreateAddressException
     */
    public Address getAddressByCoordinate(float latitude, float longitude)
            throws CantCreateAddressException {
        return NominatimProcessor.getAddressByCoordinate(latitude, longitude);
    }

    /**
     * This method returns a random geo location represented in a GeoRectangle object.
     * @return
     */
    public GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException {
        return NominatimProcessor.getRandomGeLocation();
    }

    /**
     * This method returns an address by a given latitude and longitude.
     * The address contains a GeoRectangle object.
     * The coordinates cannot be [0,0], this coordinates returns a error from Nominatim API
     * @param latitude
     * @param longitude
     * @return
     * @throws CantCreateAddressException
     */
    public Address getAddressByCoordinate(double latitude, double longitude)
            throws CantCreateAddressException {
        float floatLatitude = castDoubleToCasting(latitude);
        float floatLongitude = castDoubleToCasting(longitude);
        return NominatimProcessor.getAddressByCoordinate(floatLatitude, floatLongitude);
    }

    private float castDoubleToCasting(double number){
        try{
            BigDecimal bigDecimal = new BigDecimal(number);
            return bigDecimal.floatValue();
        } catch (Exception e){
            //In theory, it cannot happens, but, it does, I'll report and return 0.
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            return BigDecimal.ZERO.floatValue();
        }

    }

}
