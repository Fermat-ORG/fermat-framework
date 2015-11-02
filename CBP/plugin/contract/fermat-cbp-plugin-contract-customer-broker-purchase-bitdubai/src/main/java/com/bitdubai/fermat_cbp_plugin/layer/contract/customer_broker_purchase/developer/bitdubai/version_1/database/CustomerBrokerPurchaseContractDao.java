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
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;

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
    
                return null;
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerPurchaseException("An exception happened",e,"","");
            }
    
        }

}
