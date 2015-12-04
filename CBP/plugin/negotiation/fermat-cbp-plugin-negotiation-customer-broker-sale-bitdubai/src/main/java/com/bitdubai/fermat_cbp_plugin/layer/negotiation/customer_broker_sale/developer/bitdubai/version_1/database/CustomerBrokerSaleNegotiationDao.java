package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationClauseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiationInformation;

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

    /*
        Public methods
     */

    public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = SaleNegotiationTable.getEmptyRecord();

            recordToInsert.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, negotiation.getCustomerPublicKey());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getBrokerPublicKey());
            recordToInsert.setLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME, negotiation.getStartDate());
            recordToInsert.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getStatus().getCode());

            SaleNegotiationTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerSaleNegotiationException(CantCreateCustomerBrokerSaleNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME);
            SaleNegotiationClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
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

    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToUpdate = SaleNegotiationTable.getEmptyRecord();
            SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
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
            SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
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
            SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
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
                SaleNegotiationClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
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
            SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
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
                SaleNegotiationClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
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

    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByContractId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            SaleNegotiationTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
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

    public Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
        try {
            DatabaseTable SaleNegotiationTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME);
            SaleNegotiationTable.setStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
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
            PurchaseClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
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
            Collection<Clause> clauses
    ){
        return new CustomerBrokerSaleNegotiationInformation(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDateTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses
        );
    }

    private CustomerBrokerSaleNegotiation constructCustomerBrokerSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
        UUID    negotiationId     = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME);
        String  publicKeyCustomer = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String  publicKeyBroker   = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        Long    startDataTime     = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME);

        Long    negotiationExpirationDate = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_EXPIRATION_DATE_TIME_COLUMN_NAME);

        NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));
        return newCustomerBrokerSaleNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, negotiationExpirationDate, statusNegotiation, getClauses(negotiationId));
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
}
