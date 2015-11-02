package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseInformation;

import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerPurchaseContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerPurchaseContractDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseContractDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerPurchaseContractDatabaseFactory customerBrokerPurchaseContractDatabaseFactory = new CustomerBrokerPurchaseContractDatabaseFactory(pluginDatabaseSystem);

                try {
                    database = customerBrokerPurchaseContractDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public CustomerBrokerPurchase createCustomerBrokerPurchase(
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
        ) throws CantCreateCustomerBrokerPurchaseException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = PurchaseContractTable.getEmptyRecord();
    
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
    
                PurchaseContractTable.insertRecord(recordToInsert);

                    return constructCustomerBrokerPurchaseContractFromRecord(recordToInsert);
            } catch (InvalidParameterException e) {
                throw new CantCreateCustomerBrokerPurchaseException("An exception happened",e,"","");
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerPurchaseException("An exception happened",e,"","");
            }
    
        }

        public DatabaseTableRecord getCustomerBrokerPurchaseContractTable(){
            DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_TABLE_NAME);
            return PurchaseContractTable.getEmptyRecord();
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

            databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
            databaseTableRecord.setLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
            databaseTableRecord.setLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

        }

        private CustomerBrokerPurchase constructCustomerBrokerPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
    
            UUID                contractId                              = record.getUUIDValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME);
            String              customerPublicKey                       = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String              brokerPublicKey                         = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
            CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
            float               referencePrice                          = record.getFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_PRICE_COLUMN_NAME);
            ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME));
            float               paymentAmount                           = record.getFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME);
            float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
            long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
            long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
            ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACT_PURCHASE_STATUS_COLUMN_NAME));
    
    
            return new CustomerBrokerPurchaseInformation(
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
