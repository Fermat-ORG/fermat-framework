package com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.UUID;

/**
 * Luis Torres (lutor1106@gmail.com") 16/03/16
 */
public class AssetDirectSellDAO {

    //VARIABLE DECLARATION
    private Database database;

    //CONSTRUCTORS
    public AssetDirectSellDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantOpenDatabaseException, DatabaseNotFoundException {
        database = pluginDatabaseSystem.openDatabase(pluginId, AssetDirectSellDatabaseConstants.ASSET_DIRECT_SELL_DATABASE);
    }

    //PUBLIC METHODS



    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}