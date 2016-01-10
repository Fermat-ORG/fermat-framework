package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.11.15.
 */
public class NegotiationTransmissionNetworkServiceDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private Database database;

    public NegotiationTransmissionNetworkServiceDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Database database) {
        this.pluginDatabaseSystem   = pluginDatabaseSystem;
        this.pluginId               = pluginId;
        this.database               = database;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                NegotiationTransmissionNetworkServiceDatabaseFactory databaseFactory = new NegotiationTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    Database getDataBase() {
        return database;
    }

    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
    }

    /*REGISTER NEW SEND NEGOTIATION TRANSMISSION*/
    public void registerSendNegotiatioTransmission(NegotiationTransmission negotiationTransmission,NegotiationTransmissionState negotiationTransmissionState) throws CantRegisterSendNegotiationTransmissionException{
        try {
            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            loadRecordAsSendNegotiatioTransmission(record, negotiationTransmission, negotiationTransmissionState);
            table.insertRecord(record);

            if(negotiationTransmission.getNegotiationTransactionType().getCode() == NegotiationTransmissionType.TRANSMISSION_NEGOTIATION.getCode()) {
                if (negotiationTransmission.getTransmissionState().getCode() == NegotiationTransmissionState.PROCESSING_SEND.getCode()) {
                    System.out.print("\n\n**** 8) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER SEND NEGOTIATION TRANSMISSION ****\n");
                } else {
                    System.out.print("\n\n**** 13) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER RECEIVE NEGOTIATION TRANSMISSION ****\n");
                }
            }else{
                if (negotiationTransmission.getTransmissionState().getCode() == NegotiationTransmissionState.PROCESSING_SEND.getCode()) {
                    System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER SEND CONFIRMATION TRANSMISSION ****\n");
                } else {
                    System.out.print("\n\n**** ) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER RECEIVE CONFIRMATION TRANSMISSION ****\n");
                }
            }

        } catch (CantInsertRecordException e){
            throw new CantRegisterSendNegotiationTransmissionException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE + ". CAN'T REGISTER IN DATABSE A NEGOTIATION TRANSMISSION", e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantRegisterSendNegotiationTransmissionException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND CONFIRM TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }
    }

    /*UPDATE REGISTER SEND NEGOTIATION TRANSMISSION*/
    public void updateRegisterSendNegotiatioTransmission(NegotiationTransmission negotiationTransmission) throws CantRegisterSendNegotiationTransmissionException {
/*
        if (negotiationTransmission == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            loadRecordAsSendNegotiatioTransmission(record, negotiationTransmission);
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), record);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantRegisterSendNegotiationTransmissionException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE + ". CAN'T UPDATE REGISTER IN DATABSE A NEGOTIATION TRANSMISSION", e, contextBuffer.toString(), "The record do not exist");

        }
*/
    }

    /*CONFIRM RECEPTION*/
    public void confirmReception(UUID transmissionId) throws CantRegisterSendNegotiationTransmissionException {
        try {

            NegotiationTransmissionState state = NegotiationTransmissionState.DONE;
            this.changeState(transmissionId, state);

        } catch (CantRegisterSendNegotiationTransmissionException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantRegisterSendNegotiationTransmissionException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE + ". CAN'T CONFIRM RECEPTION IN REGISTER OF DATABSE NEGOTIATION TRANSMISSION", e, contextBuffer.toString(), "The record do not exist");
        }
    }

    public List<NegotiationTransmission> findAllByTransmissionState(NegotiationTransmissionState negotiationTransmissionState) throws CantReadRecordDataBaseException {

        if (negotiationTransmissionState == null) {
            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<NegotiationTransmission> list = null;

        try {
            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            table.addStringFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmissionState.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();

            list = new ArrayList<>();
            list.clear();

            for (DatabaseTableRecord record : records) {
                NegotiationTransmission outgoingTemplateNetworkServiceMessage = getNegotiationTransmissionFromRecord(record);
                list.add(outgoingTemplateNetworkServiceMessage);
            }

        } catch (CantLoadTableToMemoryException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "The data no exist");
        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, "", "Invalid parameter");
        }

        return list;
    }

    public List<NegotiationTransmission> getAllNegotiationTransmission() throws CantReadRecordDataBaseException {

        try {

            List<NegotiationTransmission> list = new ArrayList<>();

            List<DatabaseTableRecord> records;
            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            if (table == null)
                throw new CantReadRecordDataBaseException("Cant check if negotiation_transmission_network_service exists", "Network Service - Negotiation Transmission", "");

            table.loadToMemory();
            records = table.getRecords();
            if(records.isEmpty())
                return list;
            
            for (DatabaseTableRecord record : records) {
                list.add(getNegotiationTransmissionFromRecord(record));
            }

            return list;

        } catch (CantLoadTableToMemoryException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "The data no exist");
        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, "", "Invalid parameter");
        }
    }
    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<FermatMessage>
     * @throws CantRegisterSendNegotiationTransmissionException
     */
    public List<NegotiationTransmission> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty()) {
            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<NegotiationTransmission> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {
            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            for (String key : filters.keySet()) {
                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));
                filtersTable.add(newFilter);
            }
            table.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();

            list = new ArrayList<>();
            list.clear();

            for (DatabaseTableRecord record : records) {
                NegotiationTransmission outgoingTemplateNetworkServiceMessage = getNegotiationTransmissionFromRecord(record);
                list.add(outgoingTemplateNetworkServiceMessage);
            }

        } catch (CantLoadTableToMemoryException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "The data no exist");
        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, "", "Invalid parameter");
        }

        return list;
    }

    /**
     * Method that update an CryptoTransmissionMetadata in the data base.
     *
     * @throws CantRegisterSendNegotiationTransmissionException
     */
    public void changeState(UUID transmissionId, NegotiationTransmissionState negotiationTransmissionState) throws CantRegisterSendNegotiationTransmissionException {

        if (transmissionId == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            NegotiationTransmission negotiationTransmission = getNegotiationTransmissionRecord(transmissionId);
            negotiationTransmission.setTransmissionState(negotiationTransmissionState);

            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            
            //1- Create the record to the entity
            loadRecordAsSendNegotiatioTransmission(record, negotiationTransmission);

            //2.- Create a new transaction and execute
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), record);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantRegisterSendNegotiationTransmissionException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, databaseTransactionFailedException, contextBuffer.toString(), "The data no exist");
        } catch (CantGetNegotiationTransmissionException e) {
            e.printStackTrace();
        }

    }

    public void changeState(NegotiationTransmission negotiationTransmission) throws CantRegisterSendNegotiationTransmissionException {

        if (negotiationTransmission == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTable table =  this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            loadRecordAsSendNegotiatioTransmission(record, negotiationTransmission);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), record);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            throw new CantRegisterSendNegotiationTransmissionException (CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, databaseTransactionFailedException, contextBuffer.toString(), "The data no exist");
        }

    }

    public NegotiationTransmission getNegotiationTransmissionRecord(UUID transmissionId) throws CantGetNegotiationTransmissionException {

        if (transmissionId == null)
            throw new CantGetNegotiationTransmissionException("",null, "requestId, can not be null","");

        try {

            DatabaseTable databaseTable = getDatabaseTable();
            databaseTable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (!records.isEmpty())
                return getNegotiationTransmissionFromRecord(records.get(0));
            else
                throw new CantGetNegotiationTransmissionException(null, "RequestID: "+transmissionId, "Cannot find an address exchange request with the given request id.");

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetNegotiationTransmissionException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {
            throw new CantGetNegotiationTransmissionException("",exception, "Check the cause.","");
        }
    }

    /*PRIVATE*/
    private NegotiationTransmission getNegotiationTransmissionFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
        NegotiationTransmission negotiationTransmission = null;
        UUID                            transmissionId          =   record.getUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME);
        UUID                            transactionId           =   record.getUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME);
        UUID                            negotiationId           =   record.getUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME);
        NegotiationTransactionType      transactionType         =   NegotiationTransactionType.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME));
        String                          publicKeyActorSend      =   record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME);
        PlatformComponentType           actorSendType           =   PlatformComponentType.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME));
        String                          publicKeyActorReceive   =   record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME);
        PlatformComponentType           actorReceiveType        =   PlatformComponentType.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME));
        NegotiationTransmissionType     transmissionType        =   NegotiationTransmissionType.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_TYPE_COLUMN_NAME));
        NegotiationTransmissionState    transmissionState       =   NegotiationTransmissionState.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME));
        NegotiationType                 negotiationType         =   NegotiationType.getByCode(record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TYPE_COLUMN_NAME));
        String                          negotiationXML          =   record.getStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_XML_COLUMN_NAME);
        long                            timestamp               =   record.getLongValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME);

        negotiationTransmission = new NegotiationTransmissionImpl(
                transmissionId,
                transactionId,
                negotiationId,
                transactionType,
                publicKeyActorSend,
                actorSendType,
                publicKeyActorReceive,
                actorReceiveType,
                transmissionType,
                transmissionState,
                negotiationType,
                negotiationXML,
                timestamp
        );

        return negotiationTransmission;
    }

    private void loadRecordAsSendNegotiatioTransmission(DatabaseTableRecord record, NegotiationTransmission negotiationTransmission) {
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME, negotiationTransmission.getTransactionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME, negotiationTransmission.getNegotiationId());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME, negotiationTransmission.getPublicKeyActorSend());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME, negotiationTransmission.getActorSendType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME, negotiationTransmission.getPublicKeyActorReceive());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME, negotiationTransmission.getActorReceiveType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmission.getTransmissionState().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationTransactionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_XML_COLUMN_NAME, negotiationTransmission.getTransmissionState().getCode());
        record.setLongValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME, negotiationTransmission.getTimestamp());
    }

    private void loadRecordAsSendNegotiatioTransmission(DatabaseTableRecord record, NegotiationTransmission negotiationTransmission, NegotiationTransmissionState negotiationTransmissionState) {
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME, negotiationTransmission.getTransactionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME, negotiationTransmission.getNegotiationId());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME, negotiationTransmission.getPublicKeyActorSend());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME, negotiationTransmission.getActorSendType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME, negotiationTransmission.getPublicKeyActorReceive());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME, negotiationTransmission.getActorReceiveType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmissionState.getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationTransactionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_XML_COLUMN_NAME, negotiationTransmission.getNegotiationXML());
        record.setLongValue(NegotiationTransmissionNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME, negotiationTransmission.getTimestamp());
    }

}