package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationClauseManager;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiationInformation;
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
            CustomerBrokerSaleNegotiationDatabaseFactory customerBrokerSaleNegotiationDatabaseFactory = new CustomerBrokerSaleNegotiationDatabaseFactory(pluginDatabaseSystem);
            try {
                database = customerBrokerSaleNegotiationDatabaseFactory.createDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert = SaleNegotiationTable.getEmptyRecord();

            String NearExpirationDatetime = "0";
            if (negotiation.getNearExpirationDatetime()) {
                NearExpirationDatetime = "1";
            }

            recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, negotiation.getCustomerPublicKey());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getBrokerPublicKey());
            recordToInsert.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME, negotiation.getStartDate());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, negotiation.getStatus().getCode());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_MEMO_COLUMN_NAME, negotiation.getMemo());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CANCEL_REASON_COLUMN_NAME, negotiation.getCancelReason());
            recordToInsert.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_EXPIRATION_DATE_TIME_COLUMN_NAME, negotiation.getNegotiationExpirationDate());
            recordToInsert.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME, negotiation.getLastNegotiationUpdateDate());

            SaleNegotiationTable.insertRecord(recordToInsert);

            for (Clause clause : negotiation.getClauses()) {
                addNewClause(negotiation.getNegotiationId(), clause);
            }

        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerSaleNegotiationException(CantInsertRecordException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantAddNewClausesException e) {
            throw new CantCreateCustomerBrokerSaleNegotiationException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantCreateCustomerBrokerSaleNegotiationException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToDelete = SaleNegotiationClauseTable.getEmptyRecord();
            recordsToDelete.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            SaleNegotiationClauseTable.deleteRecord(recordsToDelete);

            for (Clause _clause : negotiation.getClauses()) {
                System.out.print(new StringBuilder().append("\n - ").append(_clause.getType()).append("").append(_clause.getValue()).append("\n").toString());
                addNewClause(negotiation.getNegotiationId(), _clause);
            }

            //Add Yordin Alayn 19.02.16
            updateNegotiationInfo(negotiation.getNegotiationId(), negotiation.getMemo(), negotiation.getStatus());

        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantAddNewClausesException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantDeleteRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantDeleteRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    //Add Yordin Alayn 19.02.16
    public void updateNegotiationInfo(UUID negotiationId, String memo, NegotiationStatus status) throws CantUpdateCustomerBrokerSaleException {
        try {

            memo = memo.replace("'", "''");
            DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);

            PurchaseNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();

            recordsToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_MEMO_COLUMN_NAME, memo);
            recordsToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, status.getCode());
            PurchaseNegotiationClauseTable.updateRecord(recordsToUpdate);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
            Integer NearExpirationDatetime = 0;
            if (status) {
                NearExpirationDatetime = 1;
            }
            recordsToUpdate.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
            SaleNegotiationClauseTable.updateRecord(recordsToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();

            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CANCEL_REASON_COLUMN_NAME, negotiation.getCancelReason());

            SaleNegotiationTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
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
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public boolean closeNegotiation(UUID negotiationId) throws CantUpdateCustomerBrokerSaleException {
        try {
            System.out.print(new StringBuilder().append("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL").append("\n - NEGOTIATION SALE DAO closeNegotiation(").append(negotiationId).append(")\n").toString());
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());
            SaleNegotiationTable.updateRecord(recordToUpdate);
            return true;
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.SENT_TO_CUSTOMER.getCode());
            SaleNegotiationTable.updateRecord(recordToUpdate);
            sendToCustomerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void sendToCustomerUpdateStatusClause(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_CUSTOMER.getCode());
            for (Clause _clause : negotiation.getClauses()) {
                SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                SaleNegotiationClauseTable.updateRecord(recordToUpdate);
            }
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_CUSTOMER.getCode());
            SaleNegotiationTable.updateRecord(recordToUpdate);
            waitForCustomerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
            SaleNegotiationTable.updateRecord(recordToUpdate);
            waitForCustomerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForClosing(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_CLOSING.getCode());
            SaleNegotiationTable.updateRecord(recordToUpdate);
            waitForCustomerUpdateStatusClause(negotiation);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void waitForCustomerUpdateStatusClause(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, ClauseStatus.WAITING_FOR_CUSTOMER.getCode());
            for (Clause _clause : negotiation.getClauses()) {
                SaleNegotiationClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                SaleNegotiationClauseTable.updateRecord(recordToUpdate);
            }
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerSaleException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
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
            throw new CantGetListSaleNegotiationsException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
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
            throw new CantGetListSaleNegotiationsException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
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
            throw new CantGetListSaleNegotiationsException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListSaleNegotiationsException {
        try {
            DatabaseTable table = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);

            String Query = null;

            if (actorType == ActorType.BROKER) {
                Query = "SELECT * FROM " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME +
                        " WHERE " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME +
                        " = '" +
                        NegotiationStatus.SENT_TO_BROKER.getCode() +
                        "' OR " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME +
                        " = '" +
                        NegotiationStatus.WAITING_FOR_BROKER.getCode() +
                        "' ORDER BY " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME +
                        " DESC";
            }

            if (actorType == ActorType.CUSTOMER) {
                Query = "SELECT * FROM " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME +
                        " WHERE " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME +
                        " = '" +
                        NegotiationStatus.SENT_TO_CUSTOMER.getCode() +
                        "' OR " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME +
                        " = '" +
                        NegotiationStatus.WAITING_FOR_CUSTOMER.getCode() +
                        "' OR " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME +
                        " = '" +
                        NegotiationStatus.WAITING_FOR_CLOSING.getCode() +
                        "' ORDER BY " +
                        CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME +
                        " DESC";
            }

            if (Query != null) {
                Collection<DatabaseTableRecord> res = table.customQuery(Query, true);
                Collection<CustomerBrokerSaleNegotiation> negs = new ArrayList<>();
                for (DatabaseTableRecord record : res) {
                    negs.add(constructCustomerBrokerSaleFromRecordByQuery(record));
                }

                return negs;
            } else {
                throw new CantGetListSaleNegotiationsException("INVALID PARAMETER");
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListSaleNegotiationsException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    /*
    *   Methods of Clauses
    * */

    @Override
    public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException {
        try {
            DatabaseTable SaleClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            SaleClauseTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            SaleClauseTable.loadToMemory();
            List<DatabaseTableRecord> records = SaleClauseTable.getRecords();
            SaleClauseTable.clearAllFilters();
            Collection<Clause> resultados = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerSaleClauseFromRecord(record));
            }
            return resultados;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListClauseException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListClauseException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException {
        try {

            if (!clauseExists(clause.getClauseId())) {

                DatabaseTable SaleClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
                DatabaseTableRecord recordToInsert = SaleClauseTable.getEmptyRecord();
                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME, clause.getClauseId());
                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TYPE_COLUMN_NAME, clause.getType().getCode());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_VALUE_COLUMN_NAME, clause.getValue());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, clause.getStatus().getCode());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME, clause.getProposedBy());
                recordToInsert.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME, (int) clause.getIndexOrder());
                SaleClauseTable.insertRecord(recordToInsert);

            }

        } catch (CantInsertRecordException e) {
            throw new CantAddNewClausesException(CantInsertRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    /*
    *   Private Methods
    * */

    private CustomerBrokerSaleNegotiation newCustomerBrokerSaleNegotiation(
            UUID negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            Long startDateTime,
            Long negotiationExpirationDate,
            NegotiationStatus statusNegotiation,
            Collection<Clause> clauses,
            Boolean nearExpirationDatetime,
            String cancelReason,
            String memo,
            Long lastNegotiationUpdateDate
    ) {
        return new CustomerBrokerSaleNegotiationInformation(
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

    private CustomerBrokerSaleNegotiation constructCustomerBrokerSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
        UUID negotiationId = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME);
        String publicKeyCustomer = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyBroker = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        Long startDataTime = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME);
        Long negotiationExpirationDate = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_EXPIRATION_DATE_TIME_COLUMN_NAME);
        String nearExpirationDatetime = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        String memo = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_MEMO_COLUMN_NAME);
        String cancel = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CANCEL_REASON_COLUMN_NAME);
        Long lastNegotiationUpdateDate = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME);

        Boolean _NearExpirationDatetime = true;
        if (nearExpirationDatetime.equals("0")) {
            _NearExpirationDatetime = false;
        }

        NegotiationStatus statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME));

        return newCustomerBrokerSaleNegotiation(
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

    private CustomerBrokerSaleNegotiation constructCustomerBrokerSaleFromRecordByQuery(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {

        UUID negotiationId = record.getUUIDValue("Column0");
        String publicKeyCustomer = record.getStringValue("Column1");
        String publicKeyBroker = record.getStringValue("Column2");
        Long startDataTime = record.getLongValue("Column3");
        Long negotiationExpirationDate = record.getLongValue("Column4");
        NegotiationStatus statusNegotiation = NegotiationStatus.getByCode(record.getStringValue("Column5"));
        String nearExpirationDatetime = record.getStringValue("Column6");
        String memo = record.getStringValue("Column7");
        String cancel = record.getStringValue("Column8");
        Long lastNegotiationUpdateDate = record.getLongValue("Column9");

        Boolean _NearExpirationDatetime = true;
        if (nearExpirationDatetime.equals("0")) {
            _NearExpirationDatetime = false;
        }

        return newCustomerBrokerSaleNegotiation(
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

    private CustomerBrokerSaleClause newCustomerBrokerSaleClause(
            UUID clauseId,
            ClauseType type,
            String value,
            ClauseStatus status,
            String proposedBy,
            short indexOrder
    ) {
        return new CustomerBrokerSaleClause(clauseId, type, value, status, proposedBy, indexOrder);
    }

    private CustomerBrokerSaleClause constructCustomerBrokerSaleClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
        UUID clauseId = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME);
        ClauseType type = ClauseType.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TYPE_COLUMN_NAME));
        String value = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_VALUE_COLUMN_NAME);
        ClauseStatus status = ClauseStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME));
        String proposedBy = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME);
        int indexOrder = record.getIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME);
        return newCustomerBrokerSaleClause(clauseId, type, value, status, proposedBy, (short) indexOrder);
    }

    /*
        Settings
     */

    public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {
        try {
            DatabaseTable SaleLocationsTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
            SaleLocationsTable.addStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME, location, DatabaseFilterType.EQUAL);
            SaleLocationsTable.loadToMemory();

            if (SaleLocationsTable.getRecords().size() == 0) {

                DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
                DatabaseTableRecord recordToInsert = SaleLocationTable.getEmptyRecord();

                recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, UUID.randomUUID());
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME, location);
                recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME, uri);

                SaleLocationTable.insertRecord(recordToInsert);

            }
        } catch (CantInsertRecordException e) {
            throw new CantCreateLocationSaleException(CantInsertRecordException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantCreateLocationSaleException(CantInsertRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
        try {
            DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleLocationTable.getEmptyRecord();

            SaleLocationTable.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, location.getLocationId(), DatabaseFilterType.EQUAL);

            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME, location.getLocation());
            recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME, location.getURI());

            SaleLocationTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateLocationSaleException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
        try {
            DatabaseTable SaleLocationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME);
            DatabaseTableRecord recordToDelete = SaleLocationTable.getEmptyRecord();
            recordToDelete.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME, location.getLocationId());
            SaleLocationTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteLocationSaleException(CantDeleteRecordException.DEFAULT_MESSAGE, e, "", "");
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
            throw new CantGetListLocationsSaleException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListLocationsSaleException(InvalidParameterException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private NegotiationLocations constructLocationsSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
        UUID locationId = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME);
        String location = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME);
        String uri = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME);
        return new NegotiationSaleLocations(locationId, location, uri);
    }


    private boolean clauseExists(UUID clauseId) throws CantAddNewClausesException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            if (table == null)
                throw new CantAddNewClausesException("Cant check if customer broker purchase tablet exists");

            table.addUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME, clauseId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantAddNewClausesException(em.getMessage(), em, "Customer Broker Purchase Negotiation Clause Id Not Exists", new StringBuilder().append("Cant load ").append(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantAddNewClausesException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Purchase Negotiation Clause Id Not Exists", "unknown failure.");
        }

    }
}