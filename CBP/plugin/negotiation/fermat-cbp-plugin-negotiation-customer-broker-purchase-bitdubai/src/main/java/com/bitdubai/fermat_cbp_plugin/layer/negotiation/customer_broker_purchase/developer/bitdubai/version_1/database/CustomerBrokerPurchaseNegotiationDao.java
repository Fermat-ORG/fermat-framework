package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
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
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationClauseManager;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationInformation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.NegotiationBankAccountPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.NegotiationPurchaseLocations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */

public class CustomerBrokerPurchaseNegotiationDao implements NegotiationClauseManager {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

    public CustomerBrokerPurchaseNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /*
        Public methods
     */

    public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
            CustomerBrokerPurchaseNegotiationDatabaseFactory customerBrokerPurchaseNegotiationDatabaseFactory = new CustomerBrokerPurchaseNegotiationDatabaseFactory(pluginDatabaseSystem);
            try {
                database = customerBrokerPurchaseNegotiationDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = PurchaseNegotiationTable.getEmptyRecord();

            String NearExpirationDatetime = "0";
            if(negotiation.getNearExpirationDatetime()){
                NearExpirationDatetime = "1";
            }

            recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, negotiation.getCustomerPublicKey());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getBrokerPublicKey());
            recordToInsert.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME, negotiation.getStartDate());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, negotiation.getStatus().getCode());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_MEMO_COLUMN_NAME, negotiation.getMemo());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CANCEL_REASON_COLUMN_NAME, negotiation.getCancelReason());
            recordToInsert.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_EXPIRATION_DATE_TIME_COLUMN_NAME, negotiation.getNegotiationExpirationDate());
            recordToInsert.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME, negotiation.getLastNegotiationUpdateDate());

            PurchaseNegotiationTable.insertRecord(recordToInsert);

            for(Clause clause : negotiation.getClauses()){
                addNewClause(negotiation.getNegotiationId(), clause);
            }

        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantAddNewClausesException e) {
            throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);
            PurchaseNegotiationClauseTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToDelete = PurchaseNegotiationClauseTable.getEmptyRecord();
            recordsToDelete.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            PurchaseNegotiationClauseTable.deleteRecord(recordsToDelete);

            for(Clause _clause : negotiation.getClauses()) {
                addNewClause(negotiation.getNegotiationId(), _clause);
            }

        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantAddNewClausesException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantDeleteRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            PurchaseNegotiationClauseTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
            String NearExpirationDatetime = "0";
            if(status){
                NearExpirationDatetime = "1";
            }
            recordsToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
            PurchaseNegotiationClauseTable.updateRecord(recordsToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
            PurchaseNegotiationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());
            PurchaseNegotiationTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
            PurchaseNegotiationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());
            PurchaseNegotiationTable.updateRecord(recordToUpdate);
            return true;
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
            PurchaseNegotiationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, NegotiationStatus.SENT_TO_BROKER.getCode());
            PurchaseNegotiationTable.updateRecord(recordToUpdate);
            sendToBrokerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void sendToBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_BROKER.getCode());
            for(Clause _clause : negotiation.getClauses()) {
                PurchaseNegotiationClauseTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                PurchaseNegotiationClauseTable.updateRecord(recordToUpdate);
            }
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
            PurchaseNegotiationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
            PurchaseNegotiationTable.updateRecord(recordToUpdate);
            waitForBrokerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {
            DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, ClauseStatus.WAITING_FOR_BROKER.getCode());
            for(Clause _clause : negotiation.getClauses()) {
                PurchaseNegotiationClauseTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                PurchaseNegotiationClauseTable.updateRecord(recordToUpdate);
            }
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException {
        DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
        PurchaseNegotiationTable.addFilterOrder(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        try {
            PurchaseNegotiationTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();
            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        }catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public CustomerBrokerPurchaseNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            PurchaseNegotiationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            PurchaseNegotiationTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();
            for (DatabaseTableRecord record : records) {
                return constructCustomerBrokerPurchaseFromRecord(record);
            }
            return null;
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
        try {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);
            PurchaseNegotiationTable.addFilterOrder(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            PurchaseNegotiationTable.addStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            PurchaseNegotiationTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();
            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }
            return resultados;
        } catch (InvalidParameterException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListPurchaseNegotiationsException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    /*
    *   Methods of Clauses
    * */

    @Override
    public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException {
        try {
            DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);
            PurchaseClauseTable.addFilterOrder(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            PurchaseClauseTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            PurchaseClauseTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
            PurchaseClauseTable.clearAllFilters();
            Collection<Clause> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseClauseFromRecord(record));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListClauseException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListClauseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException {
        try {
            DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert = PurchaseClauseTable.getEmptyRecord();
            recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_CLAUSE_ID_COLUMN_NAME, clause.getClauseId());
            recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TYPE_COLUMN_NAME, clause.getType().getCode());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_VALUE_COLUMN_NAME, clause.getValue());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, clause.getStatus().getCode());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_PROPOSED_BY_COLUMN_NAME, clause.getProposedBy());
            recordToInsert.setIntegerValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME, (int) clause.getIndexOrder());
            PurchaseClauseTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantAddNewClausesException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    /*
    *   Private Methods
    * */

    private CustomerBrokerPurchaseNegotiation newCustomerBrokerPurchaseNegotiation(
            UUID   negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            Long startDateTime,
            Long negotiationExpirationDate,
            NegotiationStatus statusNegotiation,
            Collection<Clause> clauses,
            Boolean nearExpirationDatetime,
            String cancelReason,
            String memo,
            Long   lastNegotiationUpdateDate
    ){
        return new CustomerBrokerPurchaseNegotiationInformation(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDateTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime,

                cancelReason,
                memo,
                lastNegotiationUpdateDate
        );
    }

    private CustomerBrokerPurchaseNegotiation constructCustomerBrokerPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
        UUID    negotiationId     = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
        String  publicKeyCustomer = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String  publicKeyBroker   = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        Long    startDataTime     = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME);
        Long    negotiationExpirationDate = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_EXPIRATION_DATE_TIME_COLUMN_NAME);
        String  nearExpirationDatetime = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        String  memo = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_MEMO_COLUMN_NAME);
        String  cancel = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CANCEL_REASON_COLUMN_NAME);
        Long    lastNegotiationUpdateDate = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME);

        Boolean _NearExpirationDatetime = true;
        if(nearExpirationDatetime.equals("0")){
            _NearExpirationDatetime = false;
        }

        NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME));

        return newCustomerBrokerPurchaseNegotiation(
            negotiationId,
            publicKeyCustomer,
            publicKeyBroker,
            startDataTime,
            negotiationExpirationDate,
            statusNegotiation,
            getClauses(negotiationId),
            _NearExpirationDatetime,
            memo,
            cancel,
            lastNegotiationUpdateDate
        );
    }

    private CustomerBrokerPurchaseClause newCustomerBrokerPurchaseClause(
            UUID            clauseId,
            ClauseType      type,
            String          value,
            ClauseStatus    status,
            String          proposedBy,
            short           indexOrder
    ){
        return new CustomerBrokerPurchaseClause(clauseId, type, value, status, proposedBy, indexOrder);
    }

    private CustomerBrokerPurchaseClause constructCustomerBrokerPurchaseClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
        UUID            clauseId            = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_CLAUSE_ID_COLUMN_NAME);
        ClauseType      type                = ClauseType.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TYPE_COLUMN_NAME));
        String          value               = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_VALUE_COLUMN_NAME);
        ClauseStatus    status              = ClauseStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME));
        String          proposedBy          = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_PROPOSED_BY_COLUMN_NAME);
        int             indexOrder          = record.getIntegerValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME);
        return newCustomerBrokerPurchaseClause(clauseId, type, value, status, proposedBy, (short) indexOrder);
    }

    /*
        Settings
     */

    public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {
        try {
            DatabaseTable PurchaseLocationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = PurchaseLocationTable.getEmptyRecord();
            recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME, UUID.randomUUID());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME, location);
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_URI_COLUMN_NAME, uri);
            PurchaseLocationTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantCreateLocationPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationPurchaseException {
        try {
            DatabaseTable PurchaseLocationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToUpdate   = PurchaseLocationTable.getEmptyRecord();
            PurchaseLocationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME, location.getLocationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME, location.getLocation());
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_URI_COLUMN_NAME, location.getURI());
            PurchaseLocationTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateLocationPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationPurchaseException {
        try {
            DatabaseTable PurchaseLocationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = PurchaseLocationTable.getEmptyRecord();
            PurchaseLocationTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME, location.getLocationId(), DatabaseFilterType.EQUAL);
            PurchaseLocationTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteLocationPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsPurchaseException {
        try {
            DatabaseTable PurchaseLocationsTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME);
            PurchaseLocationsTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseLocationsTable.getRecords();
            PurchaseLocationsTable.clearAllFilters();
            Collection<NegotiationLocations> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructLocationsPurchaseFromRecord(record));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListLocationsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListLocationsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private NegotiationLocations constructLocationsPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
        UUID    locationId  = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME);
        String  location    = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME);
        String  uri         = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_URI_COLUMN_NAME);
        return new NegotiationPurchaseLocations(locationId, location, uri);
    }

    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {
        try {
            DatabaseTable PurchaseBankTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToInsert = PurchaseBankTable.getEmptyRecord();
            recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME, bankAccount.getBankAccount());
            recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, bankAccount.getCurrencyType().getCode());
            PurchaseBankTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantCreateBankAccountPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {
        try {
            DatabaseTable PurchaseBankTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = PurchaseBankTable.getEmptyRecord();
            PurchaseBankTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME, bankAccount.getBankAccount());
            recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, bankAccount.getCurrencyType().getCode());
            PurchaseBankTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateBankAccountPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {
        try {
            DatabaseTable PurchaseBankTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);
            DatabaseTableRecord recordToDelete = PurchaseBankTable.getEmptyRecord();
            PurchaseBankTable.addUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME, bankAccount.getBankAccountId(), DatabaseFilterType.EQUAL);
            PurchaseBankTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteBankAccountPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsPurchaseException {
        try {
            DatabaseTable PurchaseBanksTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);
            PurchaseBanksTable.addStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, currency.getCode(), DatabaseFilterType.EQUAL);
            PurchaseBanksTable.loadToMemory();
            List<DatabaseTableRecord> records = PurchaseBanksTable.getRecords();
            PurchaseBanksTable.clearAllFilters();
            Collection<NegotiationBankAccount> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructBankPurchaseFromRecord(record));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListBankAccountsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListBankAccountsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsPurchaseException {

        DatabaseTable PurchaseBanksTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);

        String Query = "SELECT DISTINCT " +
            CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME +
            " FROM " +
            CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME;

        Collection<DatabaseTableRecord> records = null;
        try {
            records = PurchaseBanksTable.customQuery(Query, true);
            Collection<FiatCurrency> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(FiatCurrency.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME)));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListBankAccountsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListBankAccountsPurchaseException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private NegotiationBankAccount constructBankPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

        UUID            bankId  = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME);
        String          bank    = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME);
        FiatCurrency    type    = FiatCurrency.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME));

        return new NegotiationBankAccountPurchase(bankId, bank, type);
    }

}