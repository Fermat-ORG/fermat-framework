package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationClauseManager;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationPaymentCurrency;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreatePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeletePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListBankAccountsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListPaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiationInformation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.NegotiationBankAccountSale;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.NegotiationPaymentCurrencySale;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.NegotiationSaleLocations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */

public class CustomerBrokerSaleNegotiationDao implements NegotiationClauseManager {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerSaleNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerSaleNegotiationDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerSaleNegotiationDatabaseFactory customerBrokerSaleNegotiationDatabaseFactory = new CustomerBrokerSaleNegotiationDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = customerBrokerSaleNegotiationDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = SaleNegotiationTable.getEmptyRecord();

                Integer NearExpirationDatetime = 0;
                if(negotiation.getNearExpirationDatetime()){
                    NearExpirationDatetime = 1;
                }

                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, negotiation.getCustomerPublicKey());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getBrokerPublicKey());
                recordToInsert.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME, negotiation.getStartDate());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getStatus().getCode());
                recordToInsert.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);

                SaleNegotiationTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerSaleNegotiationException(CantCreateCustomerBrokerSaleNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordsToDelete = SaleNegotiationClauseTable.getEmptyRecord();
                recordsToDelete.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
                SaleNegotiationClauseTable.deleteRecord(recordsToDelete);

                for(Clause _clause : negotiation.getClauses()) {
                    addNewClause(negotiation.getNegotiationId(), _clause);
                }

            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantAddNewClausesException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantDeleteRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordsToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
                Integer NearExpirationDatetime = 0;
                if(status){
                    NearExpirationDatetime = 1;
                }
                recordsToUpdate.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
                SaleNegotiationClauseTable.updateRecord(recordsToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
                SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());
                SaleNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
                SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());
                SaleNegotiationTable.updateRecord(recordToUpdate);
                return true;
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
                SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.SENT_TO_BROKER.getCode());
                SaleNegotiationTable.updateRecord(recordToUpdate);
                sendToBrokerUpdateStatusClause(negotiation);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void sendToBrokerUpdateStatusClause(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_BROKER.getCode());
                for(Clause _clause : negotiation.getClauses()) {
                    SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                    SaleNegotiationClauseTable.updateRecord(recordToUpdate);
                }
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
                SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
                SaleNegotiationTable.updateRecord(recordToUpdate);
                waitForBrokerUpdateStatusClause(negotiation);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void waitForBrokerUpdateStatusClause(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, ClauseStatus.WAITING_FOR_BROKER.getCode());
                for(Clause _clause : negotiation.getClauses()) {
                    SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                    SaleNegotiationClauseTable.updateRecord(recordToUpdate);
                }
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            try {
                SaleNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleNegotiationTable.getRecords();
                SaleNegotiationTable.clearAllFilters();
                Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerSaleFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                SaleNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleNegotiationTable.getRecords();
                SaleNegotiationTable.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    return constructCustomerBrokerSaleFromRecord(record);
                }
                return null;
            } catch (InvalidParameterException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
            try {
                DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
                SaleNegotiationTable.addStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
                SaleNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleNegotiationTable.getRecords();
                SaleNegotiationTable.clearAllFilters();
                Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerSaleFromRecord(record));
                }
                return resultados;
            } catch (InvalidParameterException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListSaleNegotiationsException(CantGetListSaleNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
    *   Methods of Clauses
    * */

        @Override
        public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                PurchaseClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                PurchaseClauseTable.loadToMemory();
                List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                PurchaseClauseTable.clearAllFilters();
                Collection<Clause> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerSaleClauseFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public void addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                DatabaseTableRecord recordToInsert = PurchaseClauseTable.getEmptyRecord();
                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME, clause.getClauseId());
                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TYPE_COLUMN_NAME, clause.getType().getCode());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_VALUE_COLUMN_NAME, clause.getValue());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, clause.getStatus().getCode());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME, clause.getProposedBy());
                recordToInsert.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME, (int) clause.getIndexOrder());
                PurchaseClauseTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
    *   Private Methods
    * */

        private CustomerBrokerSaleNegotiation newCustomerBrokerSaleNegotiation(
                UUID   negotiationId,
                String publicKeyCustomer,
                String publicKeyBroker,
                Long startDateTime,
                Long negotiationExpirationDate,
                NegotiationStatus statusNegotiation,
                Collection<Clause> clauses,
                Boolean nearExpirationDatetime
        ){
            return new CustomerBrokerSaleNegotiationInformation(
                    negotiationId,
                    publicKeyCustomer,
                    publicKeyBroker,
                    startDateTime,
                    negotiationExpirationDate,
                    statusNegotiation,
                    clauses,
                    nearExpirationDatetime
            );
        }

        private CustomerBrokerSaleNegotiation constructCustomerBrokerSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
            UUID    negotiationId     = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME);
            String  publicKeyCustomer = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String  publicKeyBroker   = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
            Long    startDataTime     = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME);
            Long    negotiationExpirationDate = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_EXPIRATION_DATE_TIME_COLUMN_NAME);
            Integer nearExpirationDatetime = record.getIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);

            Boolean _NearExpirationDatetime = true;
            if(nearExpirationDatetime == 0){
                _NearExpirationDatetime = false;
            }


            NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));
            return newCustomerBrokerSaleNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, negotiationExpirationDate, statusNegotiation, getClauses(negotiationId), _NearExpirationDatetime);
        }

        private CustomerBrokerSaleClause newCustomerBrokerSaleClause(
                UUID            clauseId,
                ClauseType      type,
                String          value,
                ClauseStatus    status,
                String          proposedBy,
                short           indexOrder
        ){
            return new CustomerBrokerSaleClause(clauseId, type, value, status, proposedBy, indexOrder);
        }

        private CustomerBrokerSaleClause constructCustomerBrokerSaleClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
            UUID            clauseId            = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME);
            ClauseType      type                = ClauseType.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TYPE_COLUMN_NAME));
            String          value               = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_VALUE_COLUMN_NAME);
            ClauseStatus    status              = ClauseStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME));
            String          proposedBy          = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME);
            int             indexOrder          = record.getIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME);
            return newCustomerBrokerSaleClause(clauseId, type, value, status, proposedBy, (short) indexOrder);
        }

    /*
        Settings
     */

        public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {
            try {
                DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = SaleLocationTable.getEmptyRecord();

                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, UUID.randomUUID());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME, location);
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME, uri);

                SaleLocationTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateLocationSaleException(CantCreateLocationSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
            try {
                DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = SaleLocationTable.getEmptyRecord();

                SaleLocationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, location.getLocationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME, location.getLocation());
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME, location.getURI());

                SaleLocationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateLocationSaleException(CantUpdateLocationSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
            try {
                DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToDelete   = SaleLocationTable.getEmptyRecord();
                SaleLocationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, location.getLocationId(), DatabaseFilterType.EQUAL);
                SaleLocationTable.deleteRecord(recordToDelete);
            } catch (CantDeleteRecordException e) {
                throw new CantDeleteLocationSaleException(CantDeleteLocationSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException {
            try {
                DatabaseTable SaleLocationsTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
                SaleLocationsTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleLocationsTable.getRecords();
                SaleLocationsTable.clearAllFilters();

                Collection<NegotiationLocations> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructLocationsSaleFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListLocationsSaleException(CantGetListLocationsSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListLocationsSaleException(CantGetListLocationsSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private NegotiationLocations constructLocationsSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
            UUID    locationId  = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME);
            String  location    = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME);
            String  uri         = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME);
            return new NegotiationSaleLocations(locationId, location, uri);
        }





        public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException {
            try {
                DatabaseTable SaleBankTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToInsert = SaleBankTable.getEmptyRecord();
                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_COLUMN_NAME, bankAccount.getBankAccount());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, bankAccount.getCurrencyType().getCode());
                SaleBankTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateBankAccountSaleException(CantCreateBankAccountSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountSaleException {
            try {
                DatabaseTable SaleBankTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = SaleBankTable.getEmptyRecord();
                SaleBankTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_COLUMN_NAME, bankAccount.getBankAccount());
                recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, bankAccount.getCurrencyType().getCode());
                SaleBankTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateBankAccountSaleException(CantUpdateBankAccountSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException {
            try {
                DatabaseTable SaleBankTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToDelete = SaleBankTable.getEmptyRecord();
                SaleBankTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId(), DatabaseFilterType.EQUAL);
                SaleBankTable.deleteRecord(recordToDelete);
            } catch (CantDeleteRecordException e) {
                throw new CantDeleteBankAccountSaleException(CantDeleteBankAccountSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsSaleException {
            try {
                DatabaseTable SaleBanksTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);

                SaleBanksTable.addStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, currency.getCode(), DatabaseFilterType.EQUAL);

                SaleBanksTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleBanksTable.getRecords();
                SaleBanksTable.clearAllFilters();

                Collection<NegotiationBankAccount> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructBankSaleFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBankAccountsSaleException(CantGetListBankAccountsSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBankAccountsSaleException(CantGetListBankAccountsSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsSaleException {

            DatabaseTable PurchaseBanksTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_TABLE_NAME);

            String Query = "SELECT DISTINCT " +
                    CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME +
                    " FROM " +
                    CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_TABLE_NAME;

            Collection<DatabaseTableRecord> records = null;
            try {
                records = PurchaseBanksTable.customQuery(Query, true);
                Collection<FiatCurrency> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(FiatCurrency.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME)));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBankAccountsSaleException(CantGetListBankAccountsSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBankAccountsSaleException(CantGetListBankAccountsSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private NegotiationBankAccount constructBankSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

            UUID            bankId  = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_ID_COLUMN_NAME);
            String          bank    = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_COLUMN_NAME);
            FiatCurrency    type    = FiatCurrency.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.BANK_ACCOUNTS_BROKER_BANK_ACCOUNTS_TYPE_COLUMN_NAME));

            return new NegotiationBankAccountSale(bankId, bank, type);
        }

        public void createNewPaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantCreatePaymentCurrencySaleException {
            try {
                DatabaseTable SalePaymentTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToInsert = SalePaymentTable.getEmptyRecord();

                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_ID_COLUMN_NAME, paymentCurrency.getPaymentCurrencyId());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_TYPE_COLUMN_NAME, paymentCurrency.getPaymentCurrency().getCode());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_ID_COLUMN_NAME, paymentCurrency.getPaymentType().getCode());

                SalePaymentTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreatePaymentCurrencySaleException(CantCreatePaymentCurrencySaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void deletePaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantDeletePaymentCurrencySaleException {
            try {
                DatabaseTable SaleBankTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToDelete = SaleBankTable.getEmptyRecord();
                SaleBankTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_ID_COLUMN_NAME, paymentCurrency.getPaymentCurrencyId(), DatabaseFilterType.EQUAL);
                SaleBankTable.deleteRecord(recordToDelete);
            } catch (CantDeleteRecordException e) {
                throw new CantDeletePaymentCurrencySaleException(CantDeletePaymentCurrencySaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<NegotiationPaymentCurrency> getAllPaymentCurrencies() throws CantGetListPaymentCurrencySaleException {
            try {
                DatabaseTable SaleLocationsTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_TABLE_NAME);
                SaleLocationsTable.loadToMemory();
                List<DatabaseTableRecord> records = SaleLocationsTable.getRecords();
                SaleLocationsTable.clearAllFilters();

                Collection<NegotiationPaymentCurrency> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructPaymentCurrencySaleFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListPaymentCurrencySaleException(CantGetListPaymentCurrencySaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListPaymentCurrencySaleException(CantGetListPaymentCurrencySaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private NegotiationPaymentCurrency constructPaymentCurrencySaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

            UUID           paymentId   = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_ID_COLUMN_NAME);
            PaymentType    paymentType = PaymentType.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_TYPE_COLUMN_NAME));
            Currency paymentCurrency = null;

            if( paymentType == PaymentType.CRYPTO_MONEY ){
                paymentCurrency = CryptoCurrency.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_COLUMN_NAME));
            }else{
                paymentCurrency = FiatCurrency.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.PAYMENT_CURRENCIES_BROKER_PAYMENT_CURRENCIES_COLUMN_NAME));
            }
            return new NegotiationPaymentCurrencySale(paymentId, paymentCurrency, paymentType);
        }
}