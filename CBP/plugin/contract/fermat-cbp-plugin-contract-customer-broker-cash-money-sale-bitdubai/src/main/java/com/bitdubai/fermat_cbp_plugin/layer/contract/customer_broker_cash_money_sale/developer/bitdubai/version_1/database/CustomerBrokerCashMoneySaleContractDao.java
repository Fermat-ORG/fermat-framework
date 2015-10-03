package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantCreateCustomerBrokerCashMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantDeleteCustomerBrokerCashMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.exceptions.CantupdateCustomerBrokerCashMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_sale.interfaces.CustomerBrokerCashMoneySale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCashMoneySaleContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_sale.developer.bitdubai.version_1.structure.CustomerBrokerCashMoneySaleContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerCashMoneySaleContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerCashMoneySaleContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerCashMoneySaleContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerCashMoneySaleContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerCashMoneySaleContractDaoException(CantInitializeCustomerBrokerCashMoneySaleContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerCashMoneySale createCustomerBrokerCashMoneySale(
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
    ) throws CantCreateCustomerBrokerCashMoneySaleException {

        try {
            DatabaseTable CashMoneySaleContractTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CashMoneySaleContractTable.getEmptyRecord();

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

            CashMoneySaleContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerCashMoneySaleException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerCashMoneySaleContractTable(){
        DatabaseTable CashMoneySaleContractTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
        return CashMoneySaleContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerCashMoneySale(
            UUID contractId,
            DatabaseTableRecord recordToUpdate
    ) throws CantupdateCustomerBrokerCashMoneySaleException {

        try {
            DatabaseTable CashMoneySaleContractTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
            CashMoneySaleContractTable.setUUIDFilter(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            CashMoneySaleContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerCashMoneySaleException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerCashMoneySale(
            UUID contractId
    ) throws CantDeleteCustomerBrokerCashMoneySaleException {

        try {
            DatabaseTable CashMoneySaleContractTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = CashMoneySaleContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);

            CashMoneySaleContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerCashMoneySaleException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerCashMoneySale> getAllCustomerBrokerCashMoneySaleFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerCashMoneySale> CashMoneySaleContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            CashMoneySaleContracts.add(constructCustomerBrokerCashMoneySaleContractFromRecord(record));
        }

        return CashMoneySaleContracts;
    }

    public CustomerBrokerCashMoneySale getCustomerBrokerCashMoneySaleForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerCashMoneySale CashMoneySaleContract = null;

        for (DatabaseTableRecord record : records) {
            CashMoneySaleContract = constructCustomerBrokerCashMoneySaleContractFromRecord(record);
        }

        return CashMoneySaleContract;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerCashMoneySaleContract constructCustomerBrokerCashMoneySaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerCashMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_SALE_STATUS_COLUMN_NAME));


        return new CustomerBrokerCashMoneySaleContract(
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