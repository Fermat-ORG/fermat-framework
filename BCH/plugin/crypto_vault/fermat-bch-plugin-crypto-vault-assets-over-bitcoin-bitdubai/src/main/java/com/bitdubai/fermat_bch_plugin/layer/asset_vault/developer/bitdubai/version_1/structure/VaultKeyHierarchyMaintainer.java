package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

/**
 * Created by rodrigo on 10/4/15.
 * Maintains the list of keys generated for each account. The list of keys are used to generate crypto address.
 * This agent takes care to validate we always have enought to pass them to the crypto network.
 */
class VaultKeyHierarchyMaintainer implements Agent {

    /**
     * The vault complete key hierarchy
     */
    private VaultKeyHierarchy vaultKeyHierarchy;

    /**
     * platform services variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     * @param vaultKeyHierarchy
     * @param pluginDatabaseSystem
     */
    public VaultKeyHierarchyMaintainer(VaultKeyHierarchy vaultKeyHierarchy, PluginDatabaseSystem pluginDatabaseSystem) {
        this.vaultKeyHierarchy = vaultKeyHierarchy;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void start() throws CantStartAgentException {

    }

    @Override
    public void stop() {

    }
}
