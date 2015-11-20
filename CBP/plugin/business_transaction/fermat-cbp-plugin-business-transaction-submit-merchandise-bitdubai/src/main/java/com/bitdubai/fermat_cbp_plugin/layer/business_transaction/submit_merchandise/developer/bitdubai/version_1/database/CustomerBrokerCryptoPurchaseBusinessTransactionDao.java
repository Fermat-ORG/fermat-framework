package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.interfaces.CustomerBrokerCryptoPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.structure.CustomerBrokerCryptoPurchaseBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.exceptions.CantInsertRecordCustomerBrokerCryptoPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.exceptions.CantUpdateStatusCustomerBrokerCryptoPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.exceptions.CustomerBrokerCryptoPurchaseBusinessTransactionInconsistentTableStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CustomerBrokerCryptoPurchaseBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CustomerBrokerCryptoPurchaseBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseFactory databaseFactory = new CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerCryptoPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCustomerBrokerCryptoPurchase(
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType
    ) throws CantInsertRecordCustomerBrokerCryptoPurchaseBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, contractId, publicKeyBroker, publicKeyCustomer, paymentTransactionId, paymentCurrency, merchandiseCurrency, merchandiseAmount, executionTransactionId, cryptoCurrencyType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCustomerBrokerCryptoPurchaseBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCustomerBrokerCryptoPurchaseBusinessTransactionException(CantInsertRecordCustomerBrokerCryptoPurchaseBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCustomerBrokerCryptoPurchase(CustomerBrokerCryptoPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerCryptoPurchaseBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCustomerBrokerCryptoPurchaseBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCustomerBrokerCryptoPurchaseBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CustomerBrokerCryptoPurchase> getAllCustomerBrokerCryptoPurchaseListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CustomerBrokerCryptoPurchase> CustomerBrokerCryptoPurchase = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CustomerBrokerCryptoPurchase.add(constructCustomerBrokerCryptoPurchaseFromRecord(record));
        }
        return CustomerBrokerCryptoPurchase;
    }


    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            BusinessTransactionStatus transactionStatus,
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, paymentTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_CRYPTO_CURRENCY_TYPE_COLUMN_NAME, cryptoCurrencyType.getCode());
    }

    private void setToState(CustomerBrokerCryptoPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CustomerBrokerCryptoPurchaseBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CustomerBrokerCryptoPurchaseBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CustomerBrokerCryptoPurchaseBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CustomerBrokerCryptoPurchase constructCustomerBrokerCryptoPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_TRANSACTION_ID_COLUMN_NAME);
        UUID                        contractId              = record.getUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                      CustomerPublicKey       = record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        UUID                        paymentTransactionId    = record.getUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        CurrencyType                paymentCurrency         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        CryptoCurrencyType          cryptoCurrencyType        = CryptoCurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_CRYPTO_CURRENCY_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CustomerBrokerCryptoPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_PURCHASE_STATUS_COLUMN_NAME));

        return new CustomerBrokerCryptoPurchaseBusinessTransactionImpl(
                transactionId,
                contractId,
                brokerPublicKey,
                CustomerPublicKey,
                paymentTransactionId,
                paymentCurrency,
                merchandiseCurrency,
                merchandiseAmount,
                executionTransactionId,
                cryptoCurrencyType,
                status
        );
    }
}
