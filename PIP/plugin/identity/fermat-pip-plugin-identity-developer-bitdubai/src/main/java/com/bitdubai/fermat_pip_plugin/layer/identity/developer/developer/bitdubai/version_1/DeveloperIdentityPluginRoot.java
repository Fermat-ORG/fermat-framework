/*
 * @(#DeveloperIdentityPluginRoot.java 07/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1;


// Packages and classes to import of jdk 1.7
import java.util.*;

// Packages and classes to import of fermat api.
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.*;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.*;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.*;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.*;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerSlf4jSupport;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.exceptions.CantInitializeDeveloperIdentityDatabaseException;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.*;


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
public class DeveloperIdentityPluginRoot implements DealsWithDeviceUser, DealsWithErrors, DeveloperIdentityManager, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {


    // Private instance field declarations.
    // DealsWithDeviceUser Interface member variables.
    private DeviceUserManager deviceUserManager = null;

    // DealsWithErrors Interface member variables.
    private ErrorManager errorManager = null;


    // DealsWithLogger interface member variable.
    private LogManager logManager = null;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    // DealsWithPluginDatabaseSystem Interface member variables.
    private PluginDatabaseSystem pluginDatabaseSystem = null;

    // Plugin Interface member variables.
    private UUID pluginId = null;

    // ServiceStatus Interface member variables
    private ServiceStatus serviceStatus = null;


    // Private class fields declarations.
    // Logger object.
    private static final ILogger logger = new LoggerSlf4jSupport (DeveloperIdentityPluginRoot.class);

    private static final String EMPTY = "";


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
     *  @param logManager DealsWithLogger interface member variable.
     *  @param pluginDatabaseSystem DealsWithPluginDatabaseSystem Interface member variables.
     *  @param pluginId Plugin Interface member variables.
     *  @param serviceStatus ServiceStatus Interface member variables
     *
     * */
    public DeveloperIdentityPluginRoot (DeviceUserManager deviceUserManager, ErrorManager errorManager, LogManager logManager,
                                        PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ServiceStatus serviceStatus) {

        // Call to super class.
        super ();


        // Set internal values.
        this.deviceUserManager = deviceUserManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.serviceStatus = serviceStatus;
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

    /**
     * DealsWithDeviceUser Interface implementation.
     */
    @Override
    public void setDeviceUserManager (DeviceUserManager deviceUserManager) {
        if (deviceUserManager == null)
            throw new IllegalArgumentException();
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager (ErrorManager errorManager) {
        if (errorManager == null)
            throw new IllegalArgumentException();
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger Interface implementation.
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
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
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

    /**
     * no params.
     *
     * @return List<DeveloperIdentity> returns the list of developers linked to the current logged device user.
     * @throws CantGetUserDeveloperIdentitiesException
     */
    @Override
    public List<DeveloperIdentity> getDevelopersFromCurrentDeviceUser() throws CantGetUserDeveloperIdentitiesException {


        try {

            // Get developer list.
            logger.info (EMPTY, "Creating new developer for : " + deviceUserManager.getLoggedInDeviceUser ());


            DeveloperIdentityDao dao = new DeveloperIdentityDao (this.pluginDatabaseSystem, this.pluginId, new DeveloperIdentityDatabaseFactory (this.pluginDatabaseSystem));
            return dao.getDevelopersFromCurrentDeviceUser (deviceUserManager.getLoggedInDeviceUser ());

        } catch (CantGetLoggedInDeviceUserException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            logger.error (EMPTY, "Cant get developers from current device user, Cant get logged in device user failure.", ce);
            throw new CantGetUserDeveloperIdentitiesException (ce.getMessage(), ce, "Plugin Identity", "Cant get developers from current device user, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
            logger.error (EMPTY, "Cant get developers from current device user, unknown failure.", e);
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
        logger.info (EMPTY, "Creating new developer for : " + alias);
        DeveloperIdentityDao dao = new DeveloperIdentityDao (this.pluginDatabaseSystem, this.pluginId, new DeveloperIdentityDatabaseFactory (this.pluginDatabaseSystem));

        try {

            return dao.createNewDeveloper (alias, new ECCKeyPair (), deviceUserManager.getLoggedInDeviceUser ());

        } catch (CantGetLoggedInDeviceUserException ce) {

            // Failure CantGetLoggedInDeviceUserException.
            logger.error (EMPTY, "Cant create new developer, Cant get logged in device user failure.", ce);
            throw new CantCreateNewDeveloperException (ce.getMessage(), ce, "Plugin Identity", "create new developer, Cant get logged in device user failure..");

        } catch (Exception e) {

            // Failure unknown.
            logger.error (EMPTY, "Cant create new developer, unknown failure.", e);
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Plugin Identity", "Cant create new developer, unknown failure.");
        }
    }


    /**
     * LogManagerForDevelopers Interface implementation.
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.DeveloperIdentityPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDao");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDatabaseFactory");
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
}
