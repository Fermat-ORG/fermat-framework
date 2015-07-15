package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.DealsWithDeviceUser;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityPluginRoot implements DealsWithDeviceUser, DealsWithErrors, DeveloperIdentityManager, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Service, Plugin {

    /**
     * DealsWithDeviceUser Interface member variables.
     */
    DeviceUserManager deviceUserManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables
     */
    private UUID pluginId;

    /**
     * ServiceStatus Interface member variables
     */
    ServiceStatus serviceStatus;

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
        // TODO getLoggedInDeviceUser TO GET THE Developers LINKED TO A DeviceUser THROW DeveloperIdentityDao
        return null;
    }

    /**
     * throw an alias creates a new developer (check before if the alias already exists)
     *
     * @param alias the alias that the user choose as developer identity
     * @return DeveloperIdentity with the public key of the new developer
     * @throws CantCreateNewDeveloperException
     */
    @Override
    public DeveloperIdentity createNewDeveloper(String alias) throws CantCreateNewDeveloperException {
        // TODO CREATE ECCKeyPair AND getLoggedInDeviceUser TO CREATE THE Developer THROW DeveloperIdentityDao
        return null;
    }


    /**
     * DealsWithDeviceUser Interface implementation.
     */
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        if (deviceUserManager == null)
            throw new IllegalArgumentException();
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
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
