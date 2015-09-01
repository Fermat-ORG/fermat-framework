package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

    PluginDatabaseSystem pluginDatabaseSystem;

    public AssetIssuingTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    //TODO: implement this method
    public Database createDatabase(){
        return null;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem=pluginDatabaseSystem;

    }


}
