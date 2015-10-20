package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleClausesDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleLogsDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiation;

import java.util.UUID;

/**
 * Created by angel on 20/10/15.
 */
public class CustomerBrokerSaleNegotiationDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

        public CustomerBrokerSaleNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

    /*
        Public methods
     */


        public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerSaleNegotiationDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

        public CustomerBrokerSale createCustomerBrokerSaleNegotiation(
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDataTime
        ) throws CantCreateCustomerBrokerSaleException {

            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = SaleNegotiationTable.getEmptyRecord();

                UUID negotiationId = UUID.randomUUID();

                loadRecordAsNew(
                        recordToInsert,
                        negotiationId,
                        publicKeyCustomer,
                        publicKeyBroker,
                        startDataTime
                );

                SaleNegotiationTable.insertRecord(recordToInsert);

                return newCustomerBrokerSaleNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, NegotiationStatus.OPEN.getCode());

            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerSaleException("An exception happened",e,"","");
            }

        }

        public void cancelNegotiation(CustomerBrokerSale negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = SaleNegotiationTable.getEmptyRecord();
    
                SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
    
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());
    
                SaleNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerSaleException("An exception happened",e,"","");
            }
    
        }

        public void closeNegotiation(CustomerBrokerSale negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = SaleNegotiationTable.getEmptyRecord();
    
                SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
    
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());
    
                SaleNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerSaleException("An exception happened",e,"","");
            }
    
        }


    /*
        Private methods
     */

        private void initializeClause(UUID pluginId) throws CantInitializeCustomerBrokerSaleClausesDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleClausesDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleClausesDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

        private void initializeLogs(UUID pluginId) throws CantInitializeCustomerBrokerSaleLogsDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME);
            } catch (DatabaseNotFoundException e) {

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleLogsDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerSaleLogsDaoException(CantInitializeCustomerBrokerSaleNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

        private void loadRecordAsNew(
                DatabaseTableRecord databaseTableRecord,
                UUID   negotiationId,
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDataTime
        ) {
            databaseTableRecord.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
            databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME, startDataTime);
            databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, NegotiationStatus.OPEN.getCode());
        }

        private CustomerBrokerSale newCustomerBrokerSaleNegotiation(
                UUID   negotiationId,
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDataTime,
                String statusNegotiation
        ){
            return new CustomerBrokerSaleNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, statusNegotiation);
        }
}
