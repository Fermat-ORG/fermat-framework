package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.NominatimManager;

import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.NominatimPluginRoot;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.config.NominatimConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.procesors.GeonosProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class NominatimPluginManager implements NominatimManager {

    /**
     * Represents the plugin root class
     */
    NominatimPluginRoot nominatimPluginRoot;

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
    FilePrivacy FILE_PRIVACY = FilePrivacy.PUBLIC;


    /**
     * Constructor with parameters
     * @param nominatimPluginRoot
     * @param pluginFileSystem
     */
    public NominatimPluginManager(
            NominatimPluginRoot nominatimPluginRoot,
            PluginFileSystem pluginFileSystem,
            UUID pluginId){
        this.nominatimPluginRoot = nominatimPluginRoot;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    @Override
    public HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException {

        HashMap<String, Country> countriesList;
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    NominatimConfiguration.PATH_TO_COUNTRIES_FILE,
                    NominatimConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countriesList = createBackupFile();
                return countriesList;
            }

            return null;//only for compilation
        } catch (CantGetJSonObjectException e) {
            nominatimPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            nominatimPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geonos API and create a file with the countries list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private HashMap<String, Country> createBackupFile()
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
                    NominatimConfiguration.PATH_TO_COUNTRIES_FILE,
                    NominatimConfiguration.COUNTRIES_BACKUP_FILE,
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
}
