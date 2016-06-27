package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class GeolocationConfiguration {

    /**
     * This String represents the URL that returns the list of all countries registered in Geonos database.
     */
    public static final String EXTERNAL_API_LIST_ALL_COUNTRIES = "http://www.geognos.com/api/en/countries/info/all.json";

    /**
     * Represents the backup-file name.
     */
    public static final String COUNTRIES_BACKUP_FILE = "countries-backup";

    /**
     * Represents the backup-file name.
     */
    public static final String FILTERED_COUNTRIES_BACKUP_FILE = "filtered-countries-backup";

    /**
     * Represents the backup-file path.
     */
    public static final String PATH_TO_COUNTRIES_FILE = "";

    /**
     * Represents the backup-file name.
     */
    public static final String DEPENDENCIES_BACKUP_FILE = "dependencies-backup";

    /**
     * Represents the backup-file path.
     */
    public static final String PATH_TO_DEPENDENCIES_FILE = "";

    /**
     * Represents the backup-file name.
     */
    public static final String CITIES_BACKUP_FILE = "cities-backup";

    /**
     * Represents the backup-file name.
     */
    public static final String FILTERED_CITIES_BACKUP_FILE = "filtered-cities-backup";

    /**
     * Represents the backup-file name.
     */
    public static final String EXTENDED_CITIES_BACKUP_FILE = "extended-cities-backup";

    /**
     * Represents the backup-file path.
     */
    public static final String PATH_TO_CITIES_FILE = "";

    /**
     * Represents the backup-file name.
     */
    public static final String DEPENDENCY_CITIES_BACKUP_FILE = "dependency-cities-backup";

    /**
     * Represents the backup-file path.
     */
    public static final String DEPENDENCY_PATH_TO_CITIES_FILE = "";

    /**
     * Represents the geonames username.
     */
    public static final String GEONAMES_USERNAME = "fermatgeotest";

    /**
     * Represents the geonames URL to get the country information.
     */
    public static final String GEONAMES_URL_COUNTRY = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=en&style=full&country=";

    /**
     * Represents the geonames URL to get the country dependencies.
     */
    public static final String GEONAME_URL_COUNTRY_DEPENDENCIES = "http://api.geonames.org/childrenJSON?geonameId=";

    /**
     * Represents the Nominatim URL to get the details from a place.
     */
    public static final String NOMINATIM_URL_PLACE_DETAILS = "http://nominatim.openstreetmap.org/search?format=json&addressdetails=0&q=";

    /**
     * Represents the Nominatim URL to get the details from a reverse geolocation.
     */
    public static final String NOMINATIM_URL_REVERSE_GEOLOCATION = "http://nominatim.openstreetmap.org/reverse?format=json&zoom=18&addressdetails=1";

}
