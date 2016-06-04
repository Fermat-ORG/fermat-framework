package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class NominatimConfiguration {

    /**
     * This String represents the URL that returns the list of all countries registered in Geonos database.
     */
    public static final String EXTERNAL_API_LIST_ALL_COUNTRIES = "http://www.geognos.com/api/en/countries/info/all.json";

    /**
     * Represents the backup-file name.
     */
    public static final String COUNTRIES_BACKUP_FILE = "countries-backup";

    /**
     * Represents the backup-file path.
     */
    public static final String PATH_TO_COUNTRIES_FILE = "";

}
