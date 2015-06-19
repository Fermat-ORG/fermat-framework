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
public class CryptoVaultDatabaseFactory implements DealsWithPluginDatabaseSystem{

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

    public Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

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
        DatabaseTableFactory table;

        /**
         * First the incoming crypto table.
         */
        table = ((DatabaseFactory) database).newTableFactory(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, DatabaseDataType.STRING, 40, false);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, DatabaseDataType.STRING, 40, false);

        try {
            ((DatabaseFactory) database).createTable(table);
        } catch (CantCreateTableException cantCreateTableException) {
            System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
            cantCreateTableException.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        /**
         * Next, I will add the needed tables.
         */


            DatabaseTableFactory table2;

            /**
             * First the incoming crypto table.
             */
            table2 = ((DatabaseFactory) database).newTableFactory(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_NAME);
            table2.addColumn(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);


            try {
                ((DatabaseFactory) database).createTable(table);
            } catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

        return database;
        }
    }
