package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
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
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantGetListPurchaseClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */
public class CustomerBrokerPurchaseNegotiationDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

        public CustomerBrokerPurchaseNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

    /*
        Public methods
     */

        public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseNegotiationDaoException {
            try {
                this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_DATABASE_NAME);
            } catch (DatabaseNotFoundException e) {
                try {
                    CustomerBrokerPurchaseNegotiationDatabaseFactory databaseFactory = new CustomerBrokerPurchaseNegotiationDatabaseFactory(pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                } catch (CantCreateDatabaseException f) {
                    throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
                } catch (Exception z) {
                    throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
                }

            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeCustomerBrokerPurchaseNegotiationDaoException(CantInitializeCustomerBrokerPurchaseNegotiationDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }
        }

        public CustomerBrokerPurchase createCustomerBrokerPurchaseNegotiation(
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDateTime
        ) throws CantCreateCustomerBrokerPurchaseException {

            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = PurchaseNegotiationTable.getEmptyRecord();

                UUID negotiationId = UUID.randomUUID();

                loadRecordAsNew(
                        recordToInsert,
                        negotiationId,
                        publicKeyCustomer,
                        publicKeyBroker,
                        startDateTime
                );

                PurchaseNegotiationTable.insertRecord(recordToInsert);

                return newCustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDateTime, NegotiationStatus.OPEN);

            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerPurchaseException(CantCreateCustomerBrokerPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }

        }

        public void cancelNegotiation(CustomerBrokerPurchase negotiation) throws CantUpdateCustomerBrokerPurchaseException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());

                PurchaseNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerPurchaseException(CantUpdateCustomerBrokerPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }

        }

        public void closeNegotiation(CustomerBrokerPurchase negotiation) throws CantUpdateCustomerBrokerPurchaseException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());

                PurchaseNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerPurchaseException(CantUpdateCustomerBrokerPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }

        }

        public Collection<CustomerBrokerPurchase> getNegotiations() throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchase> resultados = new ArrayList<CustomerBrokerPurchase>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchase> getNegotiations(NegotiationStatus status) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchase> resultados = new ArrayList<CustomerBrokerPurchase>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchase> getNegotiationsByCustomer(ActorIdentity customer) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, customer.getPublicKey(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchase> resultados = new ArrayList<CustomerBrokerPurchase>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchase> getNegotiationsByBroker(ActorIdentity broker) throws CantLoadTableToMemoryException, InvalidParameterException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, broker.getPublicKey(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchase> resultados = new ArrayList<CustomerBrokerPurchase>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        /*
            Clause methods
         */

            public Collection<Clause> getClauses(UUID negotiationId){

                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);

                    PurchaseClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                    PurchaseClauseTable.clearAllFilters();

                    Collection<Clause> resultados = new ArrayList<Clause>();

                    for (DatabaseTableRecord record : records) {
                        resultados.add(constructCustomerBrokerPurchaseClauseFromRecord(record));
                    }

                    return resultados;

                } catch (CantLoadTableToMemoryException e) {
                    new CantGetListPurchaseClauseException(CantGetListPurchaseClauseException.DEFAULT_MESSAGE, e, "", "");
                } catch (InvalidParameterException e) {
                    new CantGetListPurchaseClauseException(CantGetListPurchaseClauseException.DEFAULT_MESSAGE, e, "", "");
                }

                return null;
            }

            public Clause addNewClause(UUID clauseId, ClauseType type, String value) {
                return null;
            }

            public Clause modifyClause(UUID negotiationId, Clause clause, String value) {
                return null;
            }

            public Clause modifyClauseStatus(UUID negotiationId, Clause clause, ClauseStatus status) {
                return null;
            }

            public ClauseType getNextClauseType(UUID negotiationId) {
                return null;
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
            databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME, startDataTime);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, NegotiationStatus.OPEN.getCode());
        }

        private CustomerBrokerPurchase newCustomerBrokerPurchaseNegotiation(
                UUID   negotiationId,
                String publicKeyCustomer,
                String publicKeyBroker,
                long startDateTime,
                NegotiationStatus statusNegotiation
        ){
            return new CustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDateTime, statusNegotiation, this);
        }

        private CustomerBrokerPurchase constructCustomerBrokerPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

            UUID    negotiationId     = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME);
            String  publicKeyCustomer = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String  publicKeyBroker   = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
            long    startDataTime     = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);
            NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));

            return newCustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, statusNegotiation);
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
}
