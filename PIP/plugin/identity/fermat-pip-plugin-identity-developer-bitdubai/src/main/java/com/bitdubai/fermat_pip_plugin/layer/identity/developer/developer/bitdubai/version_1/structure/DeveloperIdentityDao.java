package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2.DeviceUser;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.exceptions.CantInitializeDeveloperIdentityDatabaseException;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityDao</code>
 * all methods implementation to access the data base<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityDao implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables
     */
    private UUID pluginId;


    /**
     * This method open or creates the database i'll be working with.
     *
     * @param ownerId plugin id
     * @param databaseName database name
     * @throws CantInitializeDeveloperIdentityDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeDeveloperIdentityDatabaseException {

    }

    /**
     * Method that create a new developer in the database.
     *
     * @param alias alias of developer
     * @param developerKeyPair new private and public key for the developer
     * @param deviceUser logged in device user
     * @throws CantCreateNewDeveloperException
     */
    public void createNewDeveloper(String alias, ECCKeyPair developerKeyPair, DeviceUser deviceUser) throws CantCreateNewDeveloperException {

    }

    /**
     * Method that list the developers related to the parametrized device user.
     *
     * @param deviceUser
     * @throws CantCreateNewDeveloperException
     */
    public void getDevelopersFromCurrentDeviceUser(DeviceUser deviceUser) throws CantGetUserDeveloperIdentitiesException {

    }


    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity Interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
