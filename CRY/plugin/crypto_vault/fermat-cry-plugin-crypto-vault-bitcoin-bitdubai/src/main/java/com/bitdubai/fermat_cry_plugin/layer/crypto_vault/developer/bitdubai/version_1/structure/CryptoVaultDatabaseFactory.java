package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
class CryptoVaultDatabaseFactory implements DealsWithPluginDatabaseSystem{

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
             * First the incoming crypto table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
            table.addColumn(CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_NOTIFICATION_STS_COLUMN_NAME, DatabaseDataType.STRING, 40,false);

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
