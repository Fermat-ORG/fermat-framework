package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure.database;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;

import java.util.UUID;

/**
 * Created by natalia on 04/08/15.
 */
public class WalletNavigationStructureMiddlewareDao {

    /**
     * WalletManagerMiddlewareDatabaseDao member variables
     */
    UUID pluginId;
    Database database;



    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;



    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public WalletNavigationStructureMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        database = openDatabase();
        database.closeDatabase();
    }


    private Database openDatabase() throws CantExecuteDatabaseOperationException {
      //  try {
         //   return pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);
       // } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
        //    throw  new CantExecuteDatabaseOperationException("",exception, "", "Error in database plugin.");
        //}
        return null;
    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable table = database.getTable(tableName);
        return table;
    }



}
