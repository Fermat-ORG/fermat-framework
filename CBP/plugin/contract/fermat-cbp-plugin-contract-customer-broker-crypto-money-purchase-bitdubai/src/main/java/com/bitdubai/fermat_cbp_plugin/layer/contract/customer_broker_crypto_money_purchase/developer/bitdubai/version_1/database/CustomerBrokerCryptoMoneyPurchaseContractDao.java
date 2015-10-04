package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantCreateCustomerBrokerCryptoMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantDeleteCustomerBrokerCryptoMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.exceptions.CantupdateCustomerBrokerCryptoMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_purchase.interfaces.CustomerBrokerCryptoMoneyPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoMoneyPurchaseContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_purchase.developer.bitdubai.version_1.structure.CustomerBrokerCryptoMoneyPurchaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerCryptoMoneyPurchaseContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerCryptoMoneyPurchaseContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerCryptoMoneyPurchaseContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerCryptoMoneyPurchaseContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerCryptoMoneyPurchaseContractDaoException(CantInitializeCustomerBrokerCryptoMoneyPurchaseContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerCryptoMoneyPurchase createCustomerBrokerCryptoMoneyPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            float referencePrice,
            ReferenceCurrency referenceCurrency,
            float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate
    ) throws CantCreateCustomerBrokerCryptoMoneyPurchaseException {

        try {
            DatabaseTable CryptoMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CryptoMoneyPurchaseContractTable.getEmptyRecord();

            UUID contractID = UUID.randomUUID();

            loadRecordAsNew(
                    recordToInsert,
                    contractID,
                    publicKeyCustomer,
                    publicKeyBroker,
                    merchandiseAmount,
                    merchandiseCurrency,
                    referencePrice,
                    referenceCurrency,
                    paymentAmount,
                    paymentCurrency,
                    paymentExpirationDate,
                    merchandiseDeliveryExpirationDate
            );

            CryptoMoneyPurchaseContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerCryptoMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerCryptoMoneyPurchaseContractTable(){
        DatabaseTable CryptoMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
        return CryptoMoneyPurchaseContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerCryptoMoneyPurchase(
            UUID contractId,
            String status
    ) throws CantupdateCustomerBrokerCryptoMoneyPurchaseException {

        try {
            DatabaseTable CryptoMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
            CryptoMoneyPurchaseContractTable.setUUIDFilter(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate = CryptoMoneyPurchaseContractTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_STATUS_COLUMN_NAME, status);

            CryptoMoneyPurchaseContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerCryptoMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerCryptoMoneyPurchase(
            UUID contractId
    ) throws CantDeleteCustomerBrokerCryptoMoneyPurchaseException {

        try {
            DatabaseTable CryptoMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = CryptoMoneyPurchaseContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);

            CryptoMoneyPurchaseContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerCryptoMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerCryptoMoneyPurchase> getAllCustomerBrokerCryptoMoneyPurchaseFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerCryptoMoneyPurchase> CryptoMoneyPurchaseContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            CryptoMoneyPurchaseContracts.add(constructCustomerBrokerCryptoMoneyPurchaseContractFromRecord(record));
        }

        return CryptoMoneyPurchaseContracts;
    }

    public CustomerBrokerCryptoMoneyPurchase getCustomerBrokerCryptoMoneyPurchaseForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerCryptoMoneyPurchase CryptoMoneyPurchaseContract = null;

        for (DatabaseTableRecord record : records) {
            CryptoMoneyPurchaseContract = constructCustomerBrokerCryptoMoneyPurchaseContractFromRecord(record);
        }

        return CryptoMoneyPurchaseContract;
    }

    /*
        Methods Private
     */

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID contractId,
                                 String publicKeyCustomer,
                                 String publicKeyBroker,
                                 float merchandiseAmount,
                                 CurrencyType merchandiseCurrency,
                                 float referencePrice,
                                 ReferenceCurrency referenceCurrency,
                                 float paymentAmount,
                                 CurrencyType paymentCurrency,
                                 long paymentExpirationDate,
                                 long merchandiseDeliveryExpirationDate) {

        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerCryptoMoneyPurchaseContract constructCustomerBrokerCryptoMoneyPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_PURCHASE_STATUS_COLUMN_NAME));


        return new CustomerBrokerCryptoMoneyPurchaseContract(
                contractId,
                customerPublicKey,
                brokerPublicKey,
                paymentCurrency,
                merchandiseCurrency,
                referencePrice,
                referenceCurrency,
                paymentAmount,
                merchandiseAmount,
                paymentExpirationDate,
                merchandiseDeliveryExpirationDate,
                status
        );
    }

}