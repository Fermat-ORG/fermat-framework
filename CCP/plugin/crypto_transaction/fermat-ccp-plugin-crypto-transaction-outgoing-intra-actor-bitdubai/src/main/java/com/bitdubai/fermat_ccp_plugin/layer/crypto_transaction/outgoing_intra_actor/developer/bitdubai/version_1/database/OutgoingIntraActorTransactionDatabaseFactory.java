package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.Outgoing Intra UserTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingIntraActorTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public OutgoingIntraActorTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Outgoing Intra User table.
             */
            table = databaseFactory.newTableFactory(ownerId, OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME);

            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.TRUE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_REQUEST_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.TRUE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 34, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 34, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_OP_RETURN_COLUMN_NAME, DatabaseDataType.STRING, 250, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_REFERENCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_SAME_DEVICE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_RUNNING_NETWORK_TYPE, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_TYPE, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_FEE_ORIGIN, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addColumn(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_FEE, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
