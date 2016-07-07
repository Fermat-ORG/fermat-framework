package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 */
public class OutgoingExtraUserDatabaseFactory implements DealsWithPluginDatabaseSystem {

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

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        try {
            Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            DatabaseTableFactory outgoinExtraUserTable = databaseFactory.newTableFactory(ownerId, OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME);

            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME, DatabaseDataType.STRING, 130, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 34, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 34, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME, DatabaseDataType.STRING, 3, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, false);

            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_RUNNING_NETWORK_TYPE, DatabaseDataType.STRING, 10, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENCY_TYPE, DatabaseDataType.STRING, 10, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE, DatabaseDataType.LONG_INTEGER, 0, false);
            outgoinExtraUserTable.addColumn(OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_FEE_ORIGIN, DatabaseDataType.STRING, 6, false);


            databaseFactory.createTable(ownerId, outgoinExtraUserTable);

            return database;
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw cantCreateDatabaseException;
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId);
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException);
        } catch (Exception exception) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
