package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantCreateCustomerBrokerCryptoMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantDeleteCustomerBrokerCryptoMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.exceptions.CantupdateCustomerBrokerCryptoMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_crypto_money_sale.interfaces.CustomerBrokerCryptoMoneySale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoMoneySaleContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_sale.developer.bitdubai.version_1.structure.CustomerBrokerCryptoMoneySaleContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerCryptoMoneySaleContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerCryptoMoneySaleContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerCryptoMoneySaleContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerCryptoMoneySaleContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerCryptoMoneySaleContractDaoException(CantInitializeCustomerBrokerCryptoMoneySaleContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerCryptoMoneySale createCustomerBrokerCryptoMoneySale(
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
    ) throws CantCreateCustomerBrokerCryptoMoneySaleException {

        try {
            DatabaseTable CryptoMoneySaleContractTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CryptoMoneySaleContractTable.getEmptyRecord();

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

            CryptoMoneySaleContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerCryptoMoneySaleException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerCryptoMoneySaleContractTable(){
        DatabaseTable CryptoMoneySaleContractTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
        return CryptoMoneySaleContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerCryptoMoneySale(
            UUID contractId,
            DatabaseTableRecord recordToUpdate
    ) throws CantupdateCustomerBrokerCryptoMoneySaleException {

        try {
            DatabaseTable CryptoMoneySaleContractTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
            CryptoMoneySaleContractTable.setUUIDFilter(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            CryptoMoneySaleContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerCryptoMoneySaleException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerCryptoMoneySale(
            UUID contractId
    ) throws CantDeleteCustomerBrokerCryptoMoneySaleException {

        try {
            DatabaseTable CryptoMoneySaleContractTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = CryptoMoneySaleContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);

            CryptoMoneySaleContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerCryptoMoneySaleException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerCryptoMoneySale> getAllCustomerBrokerCryptoMoneySaleFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerCryptoMoneySale> CryptoMoneySaleContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            CryptoMoneySaleContracts.add(constructCustomerBrokerCryptoMoneySaleContractFromRecord(record));
        }

        return CryptoMoneySaleContracts;
    }

    public CustomerBrokerCryptoMoneySale getCustomerBrokerCryptoMoneySaleForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerCryptoMoneySale CryptoMoneySaleContract = null;

        for (DatabaseTableRecord record : records) {
            CryptoMoneySaleContract = constructCustomerBrokerCryptoMoneySaleContractFromRecord(record);
        }

        return CryptoMoneySaleContract;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerCryptoMoneySaleContract constructCustomerBrokerCryptoMoneySaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerCryptoMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CRYPTO_MONEY_SALE_STATUS_COLUMN_NAME));


        return new CustomerBrokerCryptoMoneySaleContract(
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