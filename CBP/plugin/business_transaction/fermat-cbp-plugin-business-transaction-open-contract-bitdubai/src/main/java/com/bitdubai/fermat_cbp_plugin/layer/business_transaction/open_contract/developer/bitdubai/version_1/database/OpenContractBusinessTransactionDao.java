package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CannotFindKeyValueException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantInitializeOpenContractBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/11/15.
 */
public class OpenContractBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public OpenContractBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                  final UUID                 pluginId           ,
                                                  final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database = database;
    }

    public void initialize() throws CantInitializeOpenContractBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                OpenContractBusinessTransactionDatabaseFactory databaseFactory = new OpenContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantInitializeOpenContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantInitializeOpenContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Open Contract DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_TABLE_NAME);
    }

    public void persistContractRecord(Contract contractRecord, ContractType contractType) throws CantInsertRecordException {

        ContractTransactionStatus contractTransactionStatus=ContractTransactionStatus.CREATING_CONTRACT;
        TransactionTransmissionStates transactionTransmissionStates=TransactionTransmissionStates.NOT_READY_TO_SEND;
        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord=buildDatabaseRecord(databaseTableRecord,
                contractRecord,
                contractTransactionStatus,
                transactionTransmissionStates,
                contractType);
        databaseTable.insertRecord(databaseTableRecord);

    }

    public String getContractXML(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return getValue(
                contractHash,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_XML_COLUMN_NAME);

    }

    public String getNegotiationId(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return getValue(
                contractHash,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME);

    }

    public boolean isContractHashExists(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        String stringFromDatabase=getContractXML(contractHash);
        return stringFromDatabase!=null;
    }

    public boolean isContractHashSentConfirmation(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(contractHash);
        return contractTransactionStatus.getCode().equals(ContractTransactionStatus.CONTRACT_CONFIRMED.getCode());
    }

    public boolean isContractHashPendingResponse(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(contractHash);
        return contractTransactionStatus.getCode().equals(ContractTransactionStatus.PENDING_RESPONSE.getCode());
    }

    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }
    }

    public UUID getTransactionId(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        String transactionId= getValue(
                contractHash,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_ID_COLUMN_NAME);
        return UUID.fromString(transactionId);

    }

    public ContractType getContractType(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            String contractTypeCode=getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_TYPE_COLUMN_NAME);
            return ContractType.getByCode(contractTypeCode);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting Contract Type from database",
                    "The contractType in database is invalid");
        }

    }

    private String getValue(String key,
                            String keyColumn,
                            String valueColumn)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.setStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(valueColumn);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.setStringFilter(
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    Contract contractRecord,
                                                    ContractTransactionStatus contractTransactionStatus,
                                                    TransactionTransmissionStates transactionTransmissionStates,
                                                    ContractType contractType) {

        UUID transactionId=UUID.randomUUID();
        String contractXML= XMLParser.parseObject(contractRecord);
        record.setUUIDValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME, contractRecord.getNegotiatiotId());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME, contractRecord.getContractId());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_STATUS_COLUMN_NAME, contractRecord.getStatus().getCode());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME, transactionTransmissionStates.getCode());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_TYPE_COLUMN_NAME, contractType.getCode());
        record.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_XML_COLUMN_NAME, contractXML);

        return record;
    }

    public void updateContractTransactionStatus(String contractHash,
                                                ContractTransactionStatus contractTransactionStatus)
            throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        updateRecordStatus(contractHash,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                contractTransactionStatus.getCode());
    }

    public List<String> getPendingToSubmitContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.PENDING_SUBMIT.getCode(),
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
    }

    public List<String> getPendingToConfirmContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.PENDING_CONFIRMATION.getCode(),
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
    }

    /**
     * This method returns a List with the parameter in the arguments.
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     */
    private List<String> getStringList(
            String key,
            String keyColumn,
            String valueColumn) throws CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            List<String> contractHashList=new ArrayList<>();
            String contractHash;
            databaseTable.setStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return contractHashList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                contractHash=databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(contractHash);
            }
            return contractHashList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting "+valueColumn+" based on "+key,
                    "Cannot load the table into memory");
        }
    }

    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.setStringFilter(
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getStringValue(
                        OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }
    }

    public void updateEventStatus(String eventId, EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.setStringFilter(
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }
    }

    /**
     * This method update a database record by contract hash.
     * @param contractHash
     * @param statusColumnName
     * @param newStatus
     * @throws CannotFindKeyValueException
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    private void updateRecordStatus(String contractHash,
                                             String statusColumnName,
                                             String newStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.setStringFilter(
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(statusColumnName, newStatus);
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating parameter "+statusColumnName,"");
        }
    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            /*throw new CannotFindKeyValueException("The parameter\n"+
                    parameter+
                    "\nis not registered in database");*/
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            //Logger LOG = Logger.getGlobal();
            //LOG.info("Distribution DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);
            //LOG.info("record:" + eventRecord.getStringValue(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME));
            
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Distribution database");
        } catch(Exception exception){
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

}
