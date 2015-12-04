package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public class CryptoVaultDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithErrors {

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
     *
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        try {
            Database database  = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            DatabaseTableFactory cryptoTransactionsTable = databaseFactory.newTableFactory(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
            cryptoTransactionsTable.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 34, true);
            cryptoTransactionsTable.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, true);
            cryptoTransactionsTable.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
            cryptoTransactionsTable.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
            cryptoTransactionsTable.addColumn(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);

            DatabaseTableFactory fermatTransactionsTable = databaseFactory.newTableFactory(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_NAME);
            fermatTransactionsTable.addColumn(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, DatabaseDataType.STRING, 34, true);

            /**
             * Transaction Status table to hold the ocurrences of how many times I'm notifying there are new transactions but noone is getting them
             * we are  keeping track of this because it may be an error if no one consumes my transactions.
             */
            DatabaseTableFactory transactionProtocolStatusTable = databaseFactory.newTableFactory(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_NAME);
            transactionProtocolStatusTable.addColumn(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 34, true);
            transactionProtocolStatusTable.addColumn(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_OCURRENCES_COLUMN_NAME, DatabaseDataType.INTEGER, 4, false);

            databaseFactory.createTable(cryptoTransactionsTable);
            databaseFactory.createTable(fermatTransactionsTable);
            databaseFactory.createTable(transactionProtocolStatusTable);

            return database;

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw cantCreateDatabaseException;
        }catch(Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
