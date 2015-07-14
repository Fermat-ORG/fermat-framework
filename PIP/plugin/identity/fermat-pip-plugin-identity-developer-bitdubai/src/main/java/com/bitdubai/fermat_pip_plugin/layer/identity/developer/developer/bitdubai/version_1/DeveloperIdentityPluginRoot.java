package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.DealsWithDeviceUser;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.DeviceUserManager;

import java.util.List;
import java.util.UUID;

/**
 * Manage Developer identities.
 * Keeps the registry of the different identities and its relation with the Device User.
 *
 * Listen the events DeviceUserLoggedIn and DeviceUserLoggedOut to know whom is logged in or not.
 *
 * Allows to create new Developers and automatically link them with the Current logged Device User.
 * Serves above layers listing the link between Device User and Developers.
 *
 * In a near future this plugins will sign messages (belongs private and public keys).
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityPluginRoot implements DealsWithDeviceUser, DealsWithErrors, DeveloperIdentityManager, DealsWithPluginDatabaseSystem, Service, Plugin {

    /**
     * DealsWithDeviceUser Interface member variables.
     */
    DeviceUserManager deviceUserManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

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
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
