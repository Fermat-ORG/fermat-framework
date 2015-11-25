package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public class CustomerBrokerNewNegotiationTransactionDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CustomerBrokerNewNegotiationTransactionDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //CREATE NEW NEGOTIATION TRANSACTION
    public CustomerBrokerNew createRegisterCustomerBrokerNewNegotiationTranasction(String publicKeyCustomer, String publicKeyBroker, UUID negotiationId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        UUID transactionId = UUID.randomUUID();
        Date time = new Date();
        long timestamp = time.getTime();
        NegotiationStatus status = NegotiationStatus.SENT_TO_BROKER;

        try {
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_COLUMN_NAME, status.getCode());
            record.setLongValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TIMESTAMP_COLUMN_NAME, timestamp);

            table.insertRecord(record);
        } catch (CantInsertRecordException e){
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException (e.getMessage(), e, "Customer Broker New Negotiation Transaction", "Cant create new Customer Broker New Negotiation Transaction, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException (e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction", "Cant create new Customer Broker New Negotiation Transaction, unknown failure.");
        }

        return new CustomerBrokerNewImpl(transactionId, negotiationId, publicKeyBroker, publicKeyCustomer, status, timestamp);
    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION
    public void updateStatusRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId, NegotiationStatus statusTransaction) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        try {
            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerNewNegotiationTransactionException("Cant Update Status Customer Broker New Negotiation Transaction, not exists.", "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            table.setUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_COLUMN_NAME, statusTransaction.getCode());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, unknown failure.");
        }
    }

    //GET NEW NEGOTIATION TRANSACTION
    public CustomerBrokerNew getRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        CustomerBrokerNew getTransaction = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");
            }
            table.setUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCustomerBrokerNewNegotiationTransactionException("The number of records is 0 ", null, "", "");

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerNewFromRecord(records);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }

        return getTransaction;
    }

    //GET LIST NEW NEGOTIATION TRANSACTION
    public List<CustomerBrokerNew> getAllRegisterCustomerBrokerNewNegotiationTranasction() throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        List<CustomerBrokerNew> getTransactions = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");
            }
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCustomerBrokerNewNegotiationTransactionException("The number of records is 0 ", null, "", "");

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerNewFromRecord(records));
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }

        return getTransactions;
    }

    //PRIVATE
    private boolean transactionExists(UUID transactionId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException {
        try {
            DatabaseTable table;
            table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new tablet exists", "Customer Broker New Negotiation Transaction", "");
            }
            table.setUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction Id Not Exists", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction Id Not Exists", "unknown failure.");
        }
    }

    private CustomerBrokerNew getCustomerBrokerNewFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

        UUID transactionId          = record.getUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME);
        UUID negotiationId          = record.getUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME);
        String publicKeyBroker      = record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String publicKeyCustomer    = record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        NegotiationStatus status    = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_COLUMN_NAME));
        long timestamp              = record.getLongValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TIMESTAMP_COLUMN_NAME);

        return new CustomerBrokerNewImpl(transactionId,negotiationId,publicKeyBroker,publicKeyCustomer,status,timestamp);
    }
}
