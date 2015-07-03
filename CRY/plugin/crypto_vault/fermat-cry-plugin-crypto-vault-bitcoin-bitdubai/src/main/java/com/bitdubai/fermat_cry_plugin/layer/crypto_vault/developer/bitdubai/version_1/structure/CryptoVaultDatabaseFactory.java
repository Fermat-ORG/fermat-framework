package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public class CryptoVaultDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithErrors{

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            /**
             * I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
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
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 34, true);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, true);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, DatabaseDataType.STRING, 20, false);

        try {
            ((DatabaseFactory) database).createTable(table);
        } catch (CantCreateTableException cantCreateTableException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateTableException);
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
            table2.addColumn(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 34, true);


            try {
                ((DatabaseFactory) database).createTable(table2);
            } catch (CantCreateTableException cantCreateTableException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateTableException);
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

        /**
         * Next, I will add the needed tables.
         */


        DatabaseTableFactory table3;

        /**
         * Transaction Status table to hold the ocurrences of how many times I'm notifying there are new transactions but noone is getting them
         * we are  keeping track of this because it may be an error if no one consumes my transactions.
         */
        table3 = ((DatabaseFactory) database).newTableFactory(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS);
        table3.addColumn(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 34, true);
        table3.addColumn(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_ocurrences_COLUMN_NAME, DatabaseDataType.INTEGER, 4, false);


        try {
            ((DatabaseFactory) database).createTable(table3);
        } catch (CantCreateTableException cantCreateTableException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateTableException);
            System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
            cantCreateTableException.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
        }
    }
