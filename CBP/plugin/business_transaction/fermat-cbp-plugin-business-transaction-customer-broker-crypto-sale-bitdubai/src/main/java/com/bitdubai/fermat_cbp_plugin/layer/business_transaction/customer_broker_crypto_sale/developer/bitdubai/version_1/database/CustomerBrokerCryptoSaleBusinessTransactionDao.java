package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.interfaces.CustomerBrokerCryptoSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.structure.CustomerBrokerCryptoSaleBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CantInsertRecordCustomerBrokerCryptoSaleBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CustomerBrokerCryptoSaleBusinessTransactionInconsistentTableStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CustomerBrokerCryptoSaleBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CustomerBrokerCryptoSaleBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCryptoSaleBusinessTransactionDatabaseFactory databaseFactory = new CustomerBrokerCryptoSaleBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCustomerBrokerCryptoSale(
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType
    ) throws CantInsertRecordCustomerBrokerCryptoSaleBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, contractId, publicKeyBroker, publicKeyCustomer, paymentTransactionId, paymentCurrency, merchandiseCurrency, merchandiseAmount, executionTransactionId, cryptoCurrencyType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCustomerBrokerCryptoSaleBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCustomerBrokerCryptoSaleBusinessTransactionException(CantInsertRecordCustomerBrokerCryptoSaleBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCustomerBrokerCryptoSale(CustomerBrokerCryptoSaleBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CustomerBrokerCryptoSale> getAllCustomerBrokerCryptoSaleListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CustomerBrokerCryptoSale> CustomerBrokerCryptoSale = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CustomerBrokerCryptoSale.add(constructCustomerBrokerCryptoSaleFromRecord(record));
        }
        return CustomerBrokerCryptoSale;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, paymentTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CRYPTO_CURRENCY_TYPE_COLUMN_NAME, cryptoCurrencyType.getCode());
    }

    private void setToState(CustomerBrokerCryptoSaleBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CustomerBrokerCryptoSaleBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CustomerBrokerCryptoSaleBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CustomerBrokerCryptoSaleBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CustomerBrokerCryptoSale constructCustomerBrokerCryptoSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TRANSACTION_ID_COLUMN_NAME);
        UUID                        contractId              = record.getUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CONTRACT_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                      CustomerPublicKey       = record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        UUID                        paymentTransactionId    = record.getUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        CurrencyType                paymentCurrency         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        CryptoCurrencyType          cryptoCurrencyType        = CryptoCurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CRYPTO_CURRENCY_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_STATUS_COLUMN_NAME));

        return new CustomerBrokerCryptoSaleBusinessTransactionImpl(
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
