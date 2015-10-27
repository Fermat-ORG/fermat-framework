package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantUpdateClausesException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiationImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

        public CustomerBrokerSaleNegotiation createCustomerBrokerSaleNegotiation(
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDataTime
        ) throws CantCreateCustomerBrokerSaleNegotiationException {

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

                return newCustomerBrokerSaleNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, NegotiationStatus.WAITING_FOR_CUSTOMER);

            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerSaleNegotiationException("An exception happened",e,"","");
            }

        }

        public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
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

        public void closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
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

        public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            identityTable.loadToMemory();
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<CustomerBrokerSaleNegotiation>();
    
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerSaleFromRecord(record));
            }
    
            return resultados;
        }

        public Collection<CustomerBrokerSaleNegotiation> getNegotiations(NegotiationStatus status) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
    
            identityTable.setStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
    
            identityTable.loadToMemory();
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<CustomerBrokerSaleNegotiation>();
    
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerSaleFromRecord(record));
            }
    
            return resultados;
        }

        public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            identityTable.setStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, customer.getPublicKey(), DatabaseFilterType.EQUAL);

            identityTable.loadToMemory();

            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();

            Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<CustomerBrokerSaleNegotiation>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerSaleFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
    
            identityTable.setStringFilter(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, broker.getPublicKey(), DatabaseFilterType.EQUAL);
    
            identityTable.loadToMemory();
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            Collection<CustomerBrokerSaleNegotiation> resultados = new ArrayList<CustomerBrokerSaleNegotiation>();
    
            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerSaleFromRecord(record));
            }
    
            return resultados;
        }

        /*
            Clause methods
         */

            public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException {

                try {
                    DatabaseTable SaleClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    SaleClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);

                    SaleClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = SaleClauseTable.getRecords();
                    SaleClauseTable.clearAllFilters();

                    Collection<Clause> resultados = new ArrayList<Clause>();

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

            public Clause addNewClause(UUID negotiationId, ClauseType type, String value, String proposedBy) throws CantAddNewClausesException {
                try {
                    DatabaseTable SaleClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    DatabaseTableRecord recordToInsert = SaleClauseTable.getEmptyRecord();
                    loadRecordAsNewClause(
                            recordToInsert,
                            negotiationId,
                            type,
                            value,
                            proposedBy
                    );
                    SaleClauseTable.insertRecord(recordToInsert);
                    return constructCustomerBrokerSaleClauseFromRecord(recordToInsert);
                } catch (CantInsertRecordException e) {
                    throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
                } catch (InvalidParameterException e) {
                    throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public Clause modifyClauseStatus(UUID negotiationId, Clause clause, ClauseStatus status) throws CantUpdateClausesException {
                try {
                    DatabaseTable SaleClauseTable = this.database.getTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    DatabaseTableRecord recordToUpdate   = SaleClauseTable.getEmptyRecord();

                    SaleClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                    SaleClauseTable.setUUIDFilter(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clause.getClauseId(), DatabaseFilterType.EQUAL);

                    recordToUpdate.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, status.getCode());

                    SaleClauseTable.updateRecord(recordToUpdate);

                    return null;
                } catch (CantUpdateRecordException e) {
                    throw new CantUpdateClausesException(CantUpdateClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public Clause modifyClause(UUID negotiationId, Clause clause, String value) throws CantUpdateClausesException {
                try {
                    modifyClauseStatus(negotiationId, clause, ClauseStatus.REJECTED);
                    return addNewClause(negotiationId, clause.getType(), value, clause.getProposedBy());
                } catch (CantAddNewClausesException e) {
                    throw new CantUpdateClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }
    
        /*
            Private methods
         */
    
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
                databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, NegotiationStatus.WAITING_FOR_CUSTOMER.getCode());
            }
    
            private CustomerBrokerSaleNegotiation newCustomerBrokerSaleNegotiation(
                    UUID   negotiationId,
                    String publicKeyCustomer,
                    String publicKeyBroker,
                    long startDataTime,
                    NegotiationStatus statusNegotiation
            ){
                return new CustomerBrokerSaleNegotiationImpl(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, statusNegotiation, this);
            }
    
            private CustomerBrokerSaleNegotiation constructCustomerBrokerSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
        
                UUID    negotiationId     = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME);
                String  publicKeyCustomer = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
                String  publicKeyBroker   = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
                long    startDataTime     = record.getLongValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);
                NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));
        
                return new CustomerBrokerSaleNegotiationImpl(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, statusNegotiation, this);
            }

        /*
            Clauses
         */

            private void loadRecordAsNewClause(
                    DatabaseTableRecord databaseTableRecord,
                    UUID   negotiationId,
                    ClauseType type,
                    String value,
                    String proposedBy
            ) {
        
                UUID clauseId = UUID.randomUUID();
        
                databaseTableRecord.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clauseId);
                databaseTableRecord.setUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME, type.getCode());
                databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME, value);
                databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.DRAFT.getCode());
                databaseTableRecord.setStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME, proposedBy);
                databaseTableRecord.setIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME, 0);
        
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
        
                UUID            clauseId            = record.getUUIDValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME);
                ClauseType      type                = ClauseType.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME));
                String          value               = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME);
                ClauseStatus    status              = ClauseStatus.getByCode(record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME));
                String          proposedBy          = record.getStringValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME);
                int             indexOrder          = record.getIntegerValue(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME);
        
                return newCustomerBrokerSaleClause(clauseId, type, value, status, proposedBy, (short) indexOrder);
            }
}
