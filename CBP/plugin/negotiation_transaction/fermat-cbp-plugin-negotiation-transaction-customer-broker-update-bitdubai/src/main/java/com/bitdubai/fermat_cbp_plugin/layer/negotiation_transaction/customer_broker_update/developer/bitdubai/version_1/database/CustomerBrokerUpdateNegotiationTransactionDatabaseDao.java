package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateNegotiationTransactionDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private Database database;

    public CustomerBrokerUpdateNegotiationTransactionDatabaseDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final Database database) {
        this.pluginDatabaseSystem   = pluginDatabaseSystem;
        this.pluginId               = pluginId;
        this.database               = database;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerUpdateNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //CREATE NEW NEGOTIATION TRANSACTION
    public void createCustomerBrokerUpdateNegotiationTransaction(UUID transactionId, Negotiation negotiation, NegotiationType negotiationType, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException{


        Date time = new Date();
        long timestamp = time.getTime();
        String negotiationXML = XMLParser.parseObject(negotiation);

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME, negotiation.getBrokerPublicKey());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, negotiation.getCustomerPublicKey());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME, negotiationType.getCode());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME, negotiationXML);
            record.setLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME, timestamp);

            table.insertRecord(record);

            if(statusTransaction.getCode() == NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode()) {
                System.out.print("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO. CONFIRM negotiationType: " + negotiationType.getCode() + " transactionId: " + transactionId + " ****\n");
            }else{
                System.out.print("\n\n**** 4) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO. NEGOTIATION negotiationType: " + negotiationType.getCode() + "transactionId: " + transactionId + " ****\n");
            }

        } catch (CantInsertRecordException e){
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException (e.getMessage(), e, "Customer Broker Update Negotiation Transaction", "Cant create new Customer Broker Update Negotiation Transaction, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException (e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction", "Cant create new Customer Broker Update Negotiation Transaction, unknown failure.");
        }

    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION
    public void updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(UUID transactionId, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException{
        try {
            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("Cant Update Status Customer Broker New Negotiation Transaction, not exists.", "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            table.updateRecord(record);

            System.out.print("\n\n**** 8) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, unknown failure.");
        }
    }

    //GET NEW NEGOTIATION TRANSACTION FROM TRANSACTION ID
    public CustomerBrokerUpdate getRegisterCustomerBrokerUpdateNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException{
        CustomerBrokerUpdate getTransaction = null;

        try {

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");
            }
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("The number of records is 0 ", null, "", "");

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerUpdateFromRecord(records);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

        return getTransaction;
    }

    //GET NEW NEGOTIATION TRANSACTION FROM NEGOTIATION ID
    public CustomerBrokerUpdate getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(UUID negotiationId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException{

        try {
            CustomerBrokerUpdate getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerUpdateFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEW NEGOTIATION TRANSACTION
    public List<CustomerBrokerUpdate> getAllRegisterCustomerBrokerUpdateNegotiationTranasction() throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException{
        List<CustomerBrokerUpdate> getTransactions = null;

        try {
            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");
            }
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("The number of records is 0 ", null, "", "");

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

        return getTransactions;
    }

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO SUBMIT
    public List<CustomerBrokerUpdate> getPendingToSubmitNegotiation() throws CantGetNegotiationTransactionListException{

        try {

            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker New Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    /*
    public List<String> getPendingToSubmitNegotiation() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        return getStringList(
                NegotiationTransactionStatus.PENDING_SUBMIT.getCode(),
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);

    }*/

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO CONFIRM
    public List<CustomerBrokerUpdate> getPendingToConfirmtNegotiation() throws CantGetNegotiationTransactionListException {
        try {

            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker New Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_CONFIRMATION.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }
    }
    /*public List<String> getPendingToConfirmtNegotiation() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        return getStringList(
                NegotiationTransactionStatus.PENDING_CONFIRMATION.getCode(),
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);

    }*/

    //GET TRANSACTION ID OF NEGOTIATION TRANSACTION
    public UUID getTransactionId(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        String transactionId= getValue(
                negotiationId,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME);

        return UUID.fromString(transactionId);

    }
    
    //GET LIST PENDING EVENT
    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            table.addStringFilter(
                    CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getStringValue(
                        CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE,e,"Getting events in EventStatus.PENDING","Cannot load the table into memory");
        }
    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventType(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            table.addStringFilter(
                    CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    //SAVE NEW EVENT
    public void saveNewEventTansaction(String eventType, String eventSource) throws CantSaveEventException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            DatabaseTableRecord eventRecord = table.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            //Logger LOG = Logger.getGlobal();
            //LOG.info("Distribution DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TIMESTAMP_COLUMN_NAME, unixTime);
            table.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Distribution database");
        } catch(Exception exception){
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    //UPDATE STATUS THE EVENT
    public void updateEventTansactionStatus(UUID eventId, EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, eventStatus.getCode());
            table.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,"Updating parameter "+CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME,"");
        }
    }

    /*PRIVATE METHOD*/
    private boolean transactionExists(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {
        try {
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new tablet exists", "Customer Broker New Negotiation Transaction", "");
            }
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction Id Not Exists", "Cant load " + CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction Id Not Exists", "unknown failure.");
        }
    }
    
    private CustomerBrokerUpdate getCustomerBrokerUpdateFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                            transactionId       = record.getUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME);
        UUID                            negotiationId       = record.getUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);
        String                          publicKeyBroker     = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                          publicKeyCustomer   = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        NegotiationTransactionStatus    status              = NegotiationTransactionStatus.getByCode(record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME));
        NegotiationType                 negotiationType     = NegotiationType.getByCode(record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME));
        String                          negotiationXML      = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME);
        long                            timestamp           = record.getLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME);

        return new CustomerBrokerUpdateImpl(transactionId,negotiationId,publicKeyBroker,publicKeyCustomer,status,negotiationType,negotiationXML,timestamp);
    }

    private List<String> getStringList(String key,String keyColumn,String valueColumn) throws CantGetNegotiationTransactionListException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException ("CANT GET NEGOTIATION TRANSACTION LISt. TABLE NO FOUNT.", "NEGOTIATION TRANSACTION CUSTOMER BROKER NEW", "CANT GET NEGOTIATION TRANSACTION LIST, TABLE NO FOUNT.");
            }
            List<String> negotiationList=new ArrayList<>();
            String negotiation;
            table.addStringFilter(keyColumn,key,DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return negotiationList;
            }

            for(DatabaseTableRecord databaseTableRecord : records){
                negotiation = databaseTableRecord.getStringValue(valueColumn);
                negotiationList.add(negotiation);
            }

            return negotiationList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE,e,"Getting "+valueColumn+" based on "+key,"Cannot load the table into memory");
        } catch (Exception e){
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e),"Getting "+valueColumn+" based on "+key,"Cannot load the table into memory");
        }
    }

    private String getValue(String key,String keyColumn,String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            table.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records.get(0).getStringValue(valueColumn);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        //Represents the maximum number of records in records
        int validResultsNumber = 1;
        int recordsSize;
        if(records.isEmpty()){ return; }
        recordsSize = records.size();
        if(recordsSize > validResultsNumber){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+validResultsNumber+", but I got "+recordsSize);
        }
    }
}
