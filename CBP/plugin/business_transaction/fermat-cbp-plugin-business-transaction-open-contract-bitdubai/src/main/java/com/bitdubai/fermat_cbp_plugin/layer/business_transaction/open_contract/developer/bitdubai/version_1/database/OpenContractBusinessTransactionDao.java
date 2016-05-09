package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CannotFindKeyValueException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantInitializeOpenContractBusinessTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/11/15.
 */
public class OpenContractBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;
    private ErrorManager errorManager;
    private Database database;

    public OpenContractBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final ErrorManager errorManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;
        this.database             = database;
        this.errorManager         = errorManager;
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
                errorManager.reportUnexpectedPluginException(
                        Plugins.OPEN_CONTRACT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.OPEN_CONTRACT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantInitializeOpenContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
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
        try{
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
        }catch(CantInsertRecordException e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE,
                    e,
                    "Cant Insert Recort in persist Contract Record","Check the cause");
        }catch(Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInsertRecordException(CantCreateDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "Unexpected Result",
                    "Check the cause");
        }

    }

    public String getContractXML(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            return getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_XML_COLUMN_NAME);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }

    }

    public String getNegotiationId(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            return getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public boolean isContractHashExists(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            String stringFromDatabase=getContractXML(contractHash);
            return stringFromDatabase!=null;
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public boolean isContractHashSentConfirmation(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(contractHash);
            return contractTransactionStatus.getCode().equals(ContractTransactionStatus.PENDING_CONFIRMATION.getCode());
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public boolean isContractHashPendingResponse(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(contractHash);
            return contractTransactionStatus.getCode().equals(ContractTransactionStatus.PENDING_RESPONSE.getCode());
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
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
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public ContractTransactionStatus getContractTransactionStatusByNegotiationId(String negotiationId) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    negotiationId,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public UUID getTransactionId(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try{
            String transactionId= getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_ID_COLUMN_NAME);
            return UUID.fromString(transactionId);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public ContractType getContractType(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            String contractTypeCode=getValue(
                    contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_TYPE_COLUMN_NAME);
            return ContractType.getByCode(contractTypeCode);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting Contract Type from database",
                    "The contractType in database is invalid");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

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

    public String getEventType(String eventId)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
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
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
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
        try{
            updateRecordStatus(contractHash,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    contractTransactionStatus.getCode());
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public List<String> getPendingToSubmitContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            return getStringList(
                    ContractTransactionStatus.PENDING_SUBMIT.getCode(),
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public List<String> getPendingToConfirmContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            return getStringList(
                    ContractTransactionStatus.PENDING_CONFIRMATION.getCode(),
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public List<String> getPendingToAskConfirmContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            return getStringList(
                    ContractTransactionStatus.PENDING_RESPONSE.getCode(),
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

//    public boolean getContractHash() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
//        try{
//            List<String> list=new ArrayList<>();
//            list = getStringList(
//                    ContractTransactionStatus.PENDING_RESPONSE.getCode(),
//                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
//                    OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
//
//
//        }catch (Exception e){
//            errorManager.reportUnexpectedPluginException(
//                    Plugins.OPEN_CONTRACT,
//                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
//                    e);
//            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
//        }
//    }

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

    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
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
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantGetContractListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
        }
    }

    public void updateEventStatus(String eventId, EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
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
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Unexpected Result","Check the cause");
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
            databaseTable.addStringFilter(
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
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Open Contract database");
        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.OPEN_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

}
