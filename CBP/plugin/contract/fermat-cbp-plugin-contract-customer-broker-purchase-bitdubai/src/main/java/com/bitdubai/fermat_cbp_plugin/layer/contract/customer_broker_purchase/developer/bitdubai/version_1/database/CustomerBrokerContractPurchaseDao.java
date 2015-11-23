package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantDeleteCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerContractPurchaseDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerContractPurchaseInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerContractPurchaseDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

    public CustomerBrokerContractPurchaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /*
        Public methods
     */

    public void initializeDatabase() throws CantInitializeCustomerBrokerContractPurchaseDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerContractPurchaseDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
            CustomerBrokerContractPurchaseDatabaseFactory CustomerBrokerContractPurchaseDatabaseFactory = new CustomerBrokerContractPurchaseDatabaseFactory(pluginDatabaseSystem);

            try {
                database = CustomerBrokerContractPurchaseDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeCustomerBrokerContractPurchaseDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(
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
    ) throws CantCreateCustomerBrokerContractPurchaseException {

        try {
            DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert = PurchaseTable.getEmptyRecord();

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

            PurchaseTable.insertRecord(recordToInsert);

            return constructCustomerBrokerContractPurchaseFromRecord(recordToInsert);
        } catch (InvalidParameterException e) {
            throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
        }

    }

    public DatabaseTableRecord getCustomerBrokerContractPurchaseTable() {
        DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
        return PurchaseTable.getEmptyRecord();
    }

    public void updateCustomerBrokerContractPurchase(
            UUID contractId,
            ContractStatus status
    ) throws CantupdateCustomerBrokerContractPurchaseException {

        try {
            DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
            PurchaseTable.setUUIDFilter(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate = PurchaseTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_STATUS_COLUMN_NAME, status.getCode());

            PurchaseTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
        }

    }

    public void deleteCustomerBrokerContractPurchase(
            UUID contractId
    ) throws CantDeleteCustomerBrokerContractPurchaseException {

        try {
            DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToDelete = PurchaseTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);

            PurchaseTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
        }

    }

    public List<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchaseFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
        try {
            identityTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
        }

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerContractPurchase> Purchases = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            try {
                Purchases.add(constructCustomerBrokerContractPurchaseFromRecord(record));
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        return Purchases;
    }

    public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(UUID ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        try {
            identityTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
        }

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerContractPurchase Purchase = null;

        for (DatabaseTableRecord record : records) {
            try {
                Purchase = constructCustomerBrokerContractPurchaseFromRecord(record);
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        return Purchase;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerContractPurchase constructCustomerBrokerContractPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID contractId = record.getUUIDValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String customerPublicKey = record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String brokerPublicKey = record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType paymentCurrency = CurrencyType.getByCode(record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType merchandiseCurrency = CurrencyType.getByCode(record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float referencePrice = record.getFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency referenceCurrency = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME));
        float paymentAmount = record.getFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME);
        float merchandiseAmount = record.getFloatValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long paymentExpirationDate = record.getLongValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long merchandiseDeliveryExpirationDate = record.getLongValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus status = ContractStatus.getByCode(record.getStringValue(CustomerBrokerContractPurchaseDatabaseConstants.CONTRACT_PURCHASE_STATUS_COLUMN_NAME));


        return new CustomerBrokerContractPurchaseInformation(
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
