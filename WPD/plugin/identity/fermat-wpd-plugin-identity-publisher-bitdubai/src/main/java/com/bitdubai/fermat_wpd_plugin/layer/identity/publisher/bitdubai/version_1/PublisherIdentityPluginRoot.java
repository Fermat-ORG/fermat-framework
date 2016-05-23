package com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantCreateNewPublisherException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantGetUserPublisherIdentitiesException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentityManager;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.database.PublisherIdentityDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.database.PublisherIdentityDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.developerUtils.PublisherIdentityDeveloperDataBaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.exceptions.CantInitializePublisherIdentityDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.structure.PublisherIdentityDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Manage Publisher identities.
 * Keeps the registry of the different identities and its relation with the Device User.
 * <p/>
 * Allows to create new Developers and automatically link them with the Current logged Device User.
 * Serves above layers listing the link between Device User and Developers.
 * <p/>
 * In a near future this plugins will sign messages (belongs private and public keys).
 * <p/>
 * Created by someone.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PublisherIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers,
        PublisherIdentityManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM       , layer = Layers.USER           , addon = Addons.DEVICE_USER        )
    private DeviceUserManager deviceUserManager;


    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();



    private PublisherIdentityDao dao = null;

    private static final String _DEFAUL_STRING = "";


    // Public constructor declarations.

    /**
     * <p>Constructor without parameters.
     */
    public PublisherIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {


        this.serviceStatus = ServiceStatus.STARTED;

        // Initializing the dao object.
        try {

            if (this.dao == null) {

                this.dao = new PublisherIdentityDao(this.pluginFileSystem, this.pluginDatabaseSystem, new PublisherIdentityDatabaseFactory(this.pluginDatabaseSystem), this.pluginId, this.logManager);
                this.dao.initializeDatabase(this.pluginId);

            } else {
                this.dao.initializeDatabase(this.pluginId);
            }

        } catch (CantInitializePublisherIdentityDatabaseException e) {
            /*
             * Catch the failure.
             * */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry failed to start", e, Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY.getCode(), "");

        } catch (Exception e) {
        /*
         * Catch the failure.
         * */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry failed to start", e, Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY.getCode(), "");


        } finally {
            logManager.log(PublisherIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()), "Plugin started...", _DEFAUL_STRING, _DEFAUL_STRING);

        }
    }


    /**
     * PublisherIdentityManager Interface implementation.
     */

    /*
     * no params.
     *
     * @return List<PublisherIdentity> returns the list of publisher linked to the current logged device user.
     * @throws CantGetUserPublisherIdentitiesException
     */
    @Override
    public List<PublisherIdentity> getPublishersFromCurrentDeviceUser() throws CantGetUserPublisherIdentitiesException {


        // Get developers from current device user.
        try {

            // Check values.
            if (this.dao == null) {
                throw new CantGetUserPublisherIdentitiesException("Cant get developers from current device user, Dao object is null.", "Plugin Identity", "Cant get developers from current device user, Dao object is null.");
            }

            // Get developer list.
            logManager.log(PublisherIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()), "Getting developers from current device user for : " + deviceUserManager.getLoggedInDeviceUser(), _DEFAUL_STRING, _DEFAUL_STRING);
            return this.dao.getPublishersFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());

        } catch (CantGetUserPublisherIdentitiesException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            throw new CantGetUserPublisherIdentitiesException(ce.getMessage(), ce, "Plugin Identity", "Cant get developers from current device user, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
            throw new CantGetUserPublisherIdentitiesException(e.getMessage(), e, "Plugin Identity", "Cant get developers from current device user, unknown failure.");
        }
    }

    /**
     * throw an alias creates a new publisher (check before if the alias already exists)
     *
     * @return PublisherIdentity with the public key of the new publisher
     * @throws CantCreateNewPublisherException
     */
    @Override
    public PublisherIdentity createNewPublisher(String alias) throws CantCreateNewPublisherException {


        // Create a new developer.
        try {

            // Check values.
            if (this.dao == null) {

                throw new CantGetUserPublisherIdentitiesException("Cant create new developer, Dao object is null.", "Plugin Identity", "Cant create new developer, Dao object is null.");
            }

            logManager.log(PublisherIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating new developer for : " + alias, _DEFAUL_STRING, _DEFAUL_STRING);
            return this.dao.createNewDeveloper(alias, new ECCKeyPair(), deviceUserManager.getLoggedInDeviceUser());

        } catch (CantGetUserPublisherIdentitiesException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            throw new CantCreateNewPublisherException(ce.getMessage(), ce, "Plugin Identity", "create new developer, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
            throw new CantCreateNewPublisherException(e.getMessage(), e, "Plugin Identity", "Cant create new developer, unknown failure.");
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return PublisherIdentityPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.PublisherIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.structure.PublisherIdentityDao");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.database.PublisherIdentityDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.database.PublisherIdentityDatabaseFactory");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (PublisherIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                PublisherIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                PublisherIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                PublisherIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        PublisherIdentityDeveloperDataBaseFactory dbFactory = new PublisherIdentityDeveloperDataBaseFactory(this.pluginId.toString(), PublisherIdentityDatabaseConstants.PUBLISHER_DB_NAME);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return PublisherIdentityDeveloperDataBaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, PublisherIdentityDatabaseConstants.PUBLISHER_DB_NAME);
            return PublisherIdentityDeveloperDataBaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database", cantOpenDatabaseException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_PUBLISHER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }
}
