package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.exceptions.CantInitializeCloseContractBusinessTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/12/15.
 */
public class CloseContractBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;
    private final ErrorManager errorManager;

    private Database database;

    public CloseContractBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                              final UUID                 pluginId           ,
                                              final Database database,
                                               final ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database = database;
        this.errorManager = errorManager;
    }

    public void initialize() throws CantInitializeCloseContractBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CloseContractBusinessTransactionDatabaseFactory databaseFactory = new CloseContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantInitializeCloseContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantInitializeCloseContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
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
        return getDataBase().getTable(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TABLE_NAME);
    }

    public List<String> getNewContractToCloseList() throws CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.CLOSING_CONTRACT.getCode(),
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
    }

    public List<String> getClosingConfirmContractToCloseList() throws CantGetContractListException {
        return getStringList(
                ContractTransactionStatus.CONFIRM_CLOSED_CONTRACT.getCode(),
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
    }

    public ContractType getContractType(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            String contractTypeCode=getValue(
                    contractHash,
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_TYPE_COLUMN_NAME);
            return ContractType.getByCode(contractTypeCode);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting Contract Type from database",
                    "The contractType in database is invalid");
        }

    }

    public String getContractXML(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return getValue(
                contractHash,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_XML_COLUMN_NAME);

    }

    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            String stringContractTransactionStatus=getValue(
                    contractHash,
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }
    }

    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }


    public String getTransactionId(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        String transactionId= getValue(
                contractHash,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_ID_COLUMN_NAME);
        //return UUID.fromString(transactionId);
        return transactionId;

    }

    /**
     * This method returns a String value from database.
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key,
                            String keyColumn,
                            String valueColumn)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                return null;
            }
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
            databaseTable.addStringFilter(
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

    public void persistContractRecord(Contract contractRecord, ContractType contractType) throws CantInsertRecordException {

        ContractTransactionStatus contractTransactionStatus=ContractTransactionStatus.CLOSING_CONTRACT;
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

    /**
     * Returns a DatabaseTableRecord from a contract
     * @param record
     * @param contractRecord
     * @param contractTransactionStatus
     * @param transactionTransmissionStates
     * @param contractType
     * @return
     */
    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    Contract contractRecord,
                                                    ContractTransactionStatus contractTransactionStatus,
                                                    TransactionTransmissionStates transactionTransmissionStates,
                                                    ContractType contractType) {

        UUID transactionId=UUID.randomUUID();
        String contractXML= XMLParser.parseObject(contractRecord);
        record.setUUIDValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_NEGOTIATION_ID_COLUMN_NAME, contractRecord.getNegotiatiotId());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME, contractRecord.getContractId());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_STATUS_COLUMN_NAME, contractRecord.getStatus().getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME, transactionTransmissionStates.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_TYPE_COLUMN_NAME, contractType.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_XML_COLUMN_NAME, contractXML);

        return record;
    }

    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
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
                        CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method updates the ContractTransactionStatus by a contractHash
     * @param contractHash
     * @param contractTransactionStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateContractTransactionStatus(String contractHash, ContractTransactionStatus contractTransactionStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{

            System.out.println("\nTEST CONTRACT - CLOSE CONTRACT - DAO - updateContractTransactionStatus() \n");

            ObjectChecker.checkArgument(contractHash);

            DatabaseTable databaseTable=getDatabaseContractTable();

            databaseTable.addStringFilter(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash,
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);

            record.setStringValue(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());

            databaseTable.updateRecord(record);

        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Getting value from database",
                    "Unexpected results in database");
        }

    }

    /*public void updateContractTransactionStatus(String contractHash,
                                                ContractTransactionStatus contractTransactionStatus)
            throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        updateRecordStatus(contractHash,
                CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                contractTransactionStatus.getCode());
    }*/
    /*
    private void updateRecordStatus(String contractHash,
                                    String statusColumnName,
                                    String newStatus) throws
            UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {

        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
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
    */

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
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    /**
     * This method save an incoming new event in database.
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            eventRecord.setUUIDValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);


        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Close Contract database");
        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    public void updateEventStatus(String eventId, EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public long getCompletionDateByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                return 0;
            }
            checkDatabaseRecords(records);
            DatabaseTableRecord databaseTableRecord = records.get(0);
            return databaseTableRecord.getLongValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_COMPLETION_DATE_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting completion date from database",
                    "Cannot load the database table");
        }
    }

    /**
     * This method sets the completion date in the database.
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public void setCompletionDateByContractHash(String contractHash, long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseContractTable();
            databaseTable.addStringFilter(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    contractHash, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty())
                return ;

            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setLongValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_COMPLETION_DATE_COLUMN_NAME, completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Setting completion date from database", "Cannot load the database table");
        }
    }
}
