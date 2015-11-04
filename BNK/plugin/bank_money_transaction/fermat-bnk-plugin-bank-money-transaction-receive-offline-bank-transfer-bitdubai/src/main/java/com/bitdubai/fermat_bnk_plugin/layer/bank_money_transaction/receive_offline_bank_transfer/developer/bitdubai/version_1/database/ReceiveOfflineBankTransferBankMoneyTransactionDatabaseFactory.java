package   com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.database.Receive Offline Bank TransferBankMoneyTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 01/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceiveOfflineBankTransferBankMoneyTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public ReceiveOfflineBankTransferBankMoneyTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Receive Offline Bank Transfer table.
             */
            table = databaseFactory.newTableFactory(ownerId, ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TABLE_NAME);

            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_TO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_PUBLIC_KEY_ACTOR_FROM_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_AMOUNT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_OPERATION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_TO_ACCOUNTTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_BANK_FROM_ACCOUNT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(ReceiveOfflineBankTransferBankMoneyTransactionDatabaseConstants.RECEIVE_OFFLINE_BANK_TRANSFER_FIRST_KEY_COLUMN);

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