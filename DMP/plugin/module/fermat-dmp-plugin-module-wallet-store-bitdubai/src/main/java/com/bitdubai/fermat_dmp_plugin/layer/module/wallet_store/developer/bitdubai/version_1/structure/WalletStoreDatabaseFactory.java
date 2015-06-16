package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * Created by rodrigo on 28/04/15.
 */
class WalletStoreDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */
            System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
            cantCreateDatabaseException.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        /**
         * Next, I will add the needed tables.
         */
        try {

            DatabaseTableFactory table;

            /**
            * Then the wallet store table.
            */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreDatabaseConstants.WALLET_STORE_TABLE_NAME);

            table.addColumn(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, false);

            
            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }
        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            System.err.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}