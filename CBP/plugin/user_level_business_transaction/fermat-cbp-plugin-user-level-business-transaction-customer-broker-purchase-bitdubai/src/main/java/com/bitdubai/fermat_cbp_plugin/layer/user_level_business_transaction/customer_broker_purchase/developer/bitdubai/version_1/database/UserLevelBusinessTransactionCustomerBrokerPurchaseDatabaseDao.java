package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchaseEventRecord;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerPurchaseDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.utils.CustomerBrokerPurchaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by franklin on 11/12/15.
 */
public class UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao {
    Database database;
    UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory = new UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory(this.pluginDatabaseSystem);
            database = userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory.createDatabase(this.pluginId, UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_DATABASE_NAME);
        }
        return database;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    public void saveCustomerBrokerPurchaseEventRecord(CustomerBrokerPurchaseEventRecord customerBrokerPurchaseEventRecord) throws DatabaseOperationException, MissingCustomerBrokerPurchaseDataException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord customerBrokerPurchaseRecord = getCustomerBrokerPurchaseRecord(customerBrokerPurchaseEventRecord);

            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(customerBrokerPurchaseEventRecord.getEventId().toString());
            filter.setColumn(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, customerBrokerPurchaseRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, customerBrokerPurchaseRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Customer Broker Purchase Event Record in the database.", null);
        }
    }

    public List<CustomerBrokerPurchase> getCustomerBrokerPurchases(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        Database database = null;
        List<CustomerBrokerPurchase> customerBrokerPurchases = new ArrayList<>();
        try {
            database = openDatabase();

            for (DatabaseTableRecord record : getCustomerBrokerPurchaseRecordData(filter)) {
                final CustomerBrokerPurchase customerBrokerPurchase = getCustomerBrokerPurchase(record);
                customerBrokerPurchases.add(customerBrokerPurchase);
            }
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, new StringBuilder().append("error trying to get customers Broker Purchase from the database with filter: ").append(filter.toString()).toString(), null);
        }

        return customerBrokerPurchases;
    }


    public void saveCustomerBrokerPurchaseTransactionData(CustomerBrokerPurchase customerBrokerPurchase) throws DatabaseOperationException, MissingCustomerBrokerPurchaseDataException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
            DatabaseTableRecord customerBrokerPurchaseRecord = getCustomerBrokerPurchaseRecord(customerBrokerPurchase);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(customerBrokerPurchase.getTransactionId().toString());
            filter.setColumn(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_CONTRACT_TRANSACTION_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, customerBrokerPurchaseRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, customerBrokerPurchaseRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Customer Broker Purchase Transaction in the database.", null);
        }
    }

    private List<DatabaseTableRecord> getCustomerBrokerPurchaseRecordData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CustomerBrokerPurchase getCustomerBrokerPurchase(final DatabaseTableRecord record) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        return new CustomerBrokerPurchaseImpl(
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_TRANSACTION_ID_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_CONTRACT_TRANSACTION_ID_COLUMN_NAME),
                record.getLongValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_TIMESTAMP_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_PURCHASE_STATUS_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_CONTRACT_STATUS_COLUMN_NAME),
                TransactionStatus.getByCode(record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME)),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_CURRENCY_TYPE_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_TRANSACTION_TYPE_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.
                        CUSTOMER_BROKER_PURCHASE_MEMO_COLUMN_NAME));
    }

    private DatabaseTableRecord getCustomerBrokerPurchaseRecord(CustomerBrokerPurchase customerBrokerPurchase
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_ID_COLUMN_NAME, customerBrokerPurchase.getTransactionId());
        record.setLongValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TIMESTAMP_COLUMN_NAME, customerBrokerPurchase.getTimestamp());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_CONTRACT_TRANSACTION_ID_COLUMN_NAME, customerBrokerPurchase.getTransactionId());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_PURCHASE_STATUS_COLUMN_NAME, customerBrokerPurchase.getPurchaseStatus());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_CONTRACT_STATUS_COLUMN_NAME, customerBrokerPurchase.getContractStatus());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME, customerBrokerPurchase.getTransactionStatus().getCode());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_CURRENCY_TYPE_COLUMN_NAME, customerBrokerPurchase.getCurrencyType());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_TRANSACTION_TYPE_COLUMN_NAME, customerBrokerPurchase.getTransactionType());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_MEMO_COLUMN_NAME, customerBrokerPurchase.getMemo());


        return record;
    }

    private DatabaseTableRecord getCustomerBrokerPurchaseRecord(CustomerBrokerPurchaseEventRecord customerBrokerPurchaseEventRecord
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_ID_COLUMN_NAME, customerBrokerPurchaseEventRecord.getEventId());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_EVENT_COLUMN_NAME, customerBrokerPurchaseEventRecord.getEvent());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, customerBrokerPurchaseEventRecord.getSource());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_STATUS_COLUMN_NAME, customerBrokerPurchaseEventRecord.getStatus());
        record.setLongValue(UserLevelBusinessTransactionCustomerBrokerPurchaseConstants.CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, customerBrokerPurchaseEventRecord.getTimestamp());

        return record;
    }
}
