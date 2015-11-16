/*
 * @(#DeveloperIdentityPluginRoot.java 07/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1;


// Packages and classes to import of jdk 1.7

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.database.DeveloperIdentityDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.database.DeveloperIdentityDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.developerUtils.DeveloperIdentityDeveloperDataBaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.exceptions.CantInitializeDeveloperIdentityDatabaseException;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

// Packages and classes to import of fermat api.


/**
 * Manage Developer identities.
 * Keeps the registry of the different identities and its relation with the Device User.
 * <p/>
 * Allows to create new Developers and automatically link them with the Current logged Device User.
 * Serves above layers listing the link between Device User and Developers.
 * <p/>
 * In a near future this plugins will sign messages (belongs private and public keys).
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * Updated by Raul Pena   - (raul.pena@gmail.com)  on 16/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityPluginRoot implements DealsWithDeviceUser, DealsWithErrors, DeveloperIdentityManager,DatabaseManagerForDevelopers, DealsWithLogger,DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,LogManagerForDevelopers, Service, Plugin {


    // Private instance field declarations.
    // DealsWithDeviceUser Interface member variables.
    private DeviceUserManager deviceUserManager = null;

    // DealsWithErrors Interface member variables.
    private ErrorManager errorManager = null;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    // DealsWithlogManager interface member variable.
    private LogManager logManager = null;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    // DealsWithPluginDatabaseSystem Interface member variables.
    private PluginDatabaseSystem pluginDatabaseSystem = null;

    // Plugin Interface member variables.
    private UUID pluginId = null;

    // ServiceStatus Interface member variables
    private ServiceStatus serviceStatus = null;

    // Dao object.
    private DeveloperIdentityDao dao = null;


    // Private class fields declarations.

    // Default string value.
    private static final String _DEFAUL_STRING = "";


    // Public constructor declarations.
    /**
     *
     *  <p>Constructor without parameters.
     *
     * */
    public DeveloperIdentityPluginRoot () {

        // Call to super class.
        super ();
    }

    /**
     *
     *  <p>Constructor with parameters.
     *
     *  @param deviceUserManager DealsWithDeviceUser Interface member variables.
     *  @param errorManager DealsWithErrors Interface member variables.
     *  @param logManager DealsWithlogManager interface member variable.
     *  @param pluginDatabaseSystem DealsWithPluginDatabaseSystem Interface member variables.
     *  @param pluginId Plugin Interface member variables.
     *  @param serviceStatus ServiceStatus Interface member variables
     *  @param dao
     *
     * */
    public DeveloperIdentityPluginRoot (DeviceUserManager deviceUserManager, ErrorManager errorManager, LogManager logManager,
                                        PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ServiceStatus serviceStatus,
                                        DeveloperIdentityDao dao) {

        // Call to super class.
        super ();


        // Set internal values.
        this.deviceUserManager = deviceUserManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.serviceStatus = serviceStatus;
        this.dao = dao;
    }


    // Public instance methods declarations. (Get/Set)
    public void setPluginId (UUID pluginId) {

        // Set the value.
        this.pluginId = pluginId;
    }

    public void setServiceStatus (ServiceStatus serviceStatus) {

        // Set the value.
        this.serviceStatus = serviceStatus;
    }

    public void setDao (DeveloperIdentityDao dao) {

        // Set the value.
        this.dao = dao;
    }


    /**
     * DealsWithDeviceUser Interface implementation.
     */
    @Override
    public void setDeviceUserManager (DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager (ErrorManager errorManager) {
            this.errorManager = errorManager;
    }

    /**
     * DealsWithlogManager Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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

                this.dao = new DeveloperIdentityDao (this.pluginFileSystem,this.pluginDatabaseSystem, new DeveloperIdentityDatabaseFactory(this.pluginDatabaseSystem), this.pluginId,this.logManager);
                this.dao.initializeDatabase (this.pluginId);

            } else {
                 this.dao.initializeDatabase (this.pluginId);
            }

        } catch (CantInitializeDeveloperIdentityDatabaseException e) {
            /*
             * Catch the failure.
             * */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry failed to start", e, Plugins.BITDUBAI_DEVELOPER_IDENTITY.getKey(), "");

        } catch (Exception e) {
        /*
         * Catch the failure.
         * */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Registry failed to start", e, Plugins.BITDUBAI_DEVELOPER_IDENTITY.getKey(), "");


        }
        finally {
                logManager.log(DeveloperIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()),  "Plugin started...", _DEFAUL_STRING, _DEFAUL_STRING);

        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }



    /**
     * DeveloperIdentityManager Interface implementation.
     */

    /*
     * no params.
     *
     * @return List<DeveloperIdentity> returns the list of developers linked to the current logged device user.
     * @throws CantGetUserDeveloperIdentitiesException
     */
    @Override
    public List<DeveloperIdentity> getDevelopersFromCurrentDeviceUser () throws CantGetUserDeveloperIdentitiesException {


        // Get developers from current device user.
        try {

            // Check values.
            if (this.dao == null) {
              throw new CantGetUserDeveloperIdentitiesException("Cant get developers from current device user, Dao object is null.", "Plugin Identity", "Cant get developers from current device user, Dao object is null.");
            }

            // Get developer list.
           logManager.log (DeveloperIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()), "Getting developers from current device user for : " + deviceUserManager.getLoggedInDeviceUser(), _DEFAUL_STRING, _DEFAUL_STRING);
            return this.dao.getDevelopersFromCurrentDeviceUser (deviceUserManager.getLoggedInDeviceUser());

        } catch (CantGetUserDeveloperIdentitiesException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            throw new CantGetUserDeveloperIdentitiesException (ce.getMessage(), ce, "Plugin Identity", "Cant get developers from current device user, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
            throw new CantGetUserDeveloperIdentitiesException (e.getMessage(), e, "Plugin Identity", "Cant get developers from current device user, unknown failure.");
        }
    }

    /**
     * throw an alias creates a new developer (check before if the alias already exists)
     *
     *
     * @return DeveloperIdentity with the public key of the new developer
     * @throws CantCreateNewDeveloperException
     */
    @Override
    public DeveloperIdentity createNewDeveloper (String alias) throws CantCreateNewDeveloperException {


        // Create a new developer.
        try {

            // Check values.
            if (this.dao == null) {

                   throw new CantGetUserDeveloperIdentitiesException ("Cant create new developer, Dao object is null.", "Plugin Identity", "Cant create new developer, Dao object is null.");
            }

            logManager.log (DeveloperIdentityPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating new developer for : " + alias, _DEFAUL_STRING, _DEFAUL_STRING);
            return this.dao.createNewDeveloper(alias, new ECCKeyPair(), deviceUserManager.getLoggedInDeviceUser());

        } catch (CantGetUserDeveloperIdentitiesException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            throw new CantCreateNewDeveloperException (ce.getMessage(), ce, "Plugin Identity", "create new developer, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
           throw new CantCreateNewDeveloperException (e.getMessage(), e, "Plugin Identity", "Cant create new developer, unknown failure.");
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return DeveloperIdentityPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
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
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.developer.developer.bitdubai.version_1.DeveloperIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDao");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.developer.developer.bitdubai.version_1.database.DeveloperIdentityDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.identity.developer.developer.bitdubai.version_1.database.DeveloperIdentityDatabaseFactory");
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
            if (DeveloperIdentityPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                DeveloperIdentityPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                DeveloperIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                DeveloperIdentityPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * DealsWithPlatformFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {


        DeveloperIdentityDeveloperDataBaseFactory dbFactory = new DeveloperIdentityDeveloperDataBaseFactory(this.pluginId.toString(), DeveloperIdentityDatabaseConstants.DEVELOPER_DB_NAME);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperIdentityDeveloperDataBaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, DeveloperIdentityDatabaseConstants.DEVELOPER_DB_NAME);
            return DeveloperIdentityDeveloperDataBaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database",cantOpenDatabaseException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }
}
