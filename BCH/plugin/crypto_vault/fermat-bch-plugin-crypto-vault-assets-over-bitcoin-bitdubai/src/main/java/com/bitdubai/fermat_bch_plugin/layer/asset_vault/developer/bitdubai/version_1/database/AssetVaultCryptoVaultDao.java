package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetVaultCryptoVaultDatabaseException;

import java.util.UUID;

/**
 * Created by rodrigo on 9/21/15.
 */
public class AssetVaultCryptoVaultDao implements DealsWithPluginDatabaseSystem {
    Database database;
    UUID pluginId;

    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * Constructor
     * @param pluginId
     * @param pluginDatabaseSystem
     */
    public AssetVaultCryptoVaultDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    private void initializeDatabase() throws CantInitializeAssetVaultCryptoVaultDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetVaultCryptoVaultDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetVaultCryptoVaultDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetVaultCryptoVaultDatabaseFactory assetVaultCryptoVaultDatabaseFactory = new AssetVaultCryptoVaultDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetVaultCryptoVaultDatabaseFactory.createDatabase(pluginId, AssetVaultCryptoVaultDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetVaultCryptoVaultDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }

    }


    public int getAvailableKeyPosition(int accountNumber, int chainNumber){
        return 0;
    }

    public int setNewAvailableKeyPosition(int accountNumber, int chainNumber){
        return 0;
    }

    public int getChainNumber(int accountNumber, String chainPublicKey){
        return 0;
    }

    public boolean isValidChainNumber(int accountNumber, int chainNumber){
        return true;
    }


}



