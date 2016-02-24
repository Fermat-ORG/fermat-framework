/*
 * @#OutgoingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 *
 * Created by Gabriel Araujo (gabe_512@hotmail.com)
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMetaDataDao {

    /**
     * Represent the database
     */
    private Database database;

    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private ErrorManager errorManager;

    /**
     * Constructor
     * @param database
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ChatMetaDataDao(Database database, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
        super();
        this.database = database;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }
    private void reportUnexpectedException(FermatException e){
        errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }
    /**
     * Return the Database
     *
     * @return Database
     */
    Database getDatabase() {
        return database;
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDatabase().getTable(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TABLE_NAME);
    }

    /**
     * Method that find an ChatMetadataRecord by id in the data base.
     *
     * @param hash String id.
     * @return ChatMetadataRecord found.
     * @throws CantReadRecordDataBaseException
     */

    public ChatMetadataRecord findByTransactionHash(String hash)throws CantReadRecordDataBaseException {
        if (hash == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME, hash, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            reportUnexpectedException(cantReadRecordDataBaseException);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }
    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @return List<ChatMetadataRecord>
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataRecord> findAllToSend() throws CantReadRecordDataBaseException, CantLoadTableToMemoryException {

        DatabaseTable databaseTable = getDatabaseTable();
        databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DistributionStatus.DELIVERING.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();

            /*
             * 2 - read all records
             */
        List<DatabaseTableRecord> records = databaseTable.getRecords();

        List<ChatMetadataRecord> list = new ArrayList<>();
        try{
            if(!records.isEmpty() && records.size()>0){
                for (DatabaseTableRecord record : records) {
                    list.add(constructFrom(record));
                }
            }

        } catch (Exception e){
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
            reportUnexpectedException(cantReadRecordDataBaseException);
            throw cantReadRecordDataBaseException;
        }

        return list;
    }
    public void initialize() throws CantInitializeChatNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {
                CommunicationChatNetworkServiceDatabaseFactory databaseFactory = new CommunicationChatNetworkServiceDatabaseFactory(pluginDatabaseSystem,errorManager);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
                reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
                throw cantInitializeChatNetworkServiceDatabaseException;
            } catch (Exception z) {
                CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, z, "", "There is a problem and i cannot create the database.");
                reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
                throw cantInitializeChatNetworkServiceDatabaseException;
            }

        } catch (CantOpenDatabaseException e) {
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
            reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;
        } catch (Exception e) {
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
            reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws CantReadRecordDataBaseException
     */
    public ChatMetadataRecord findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            reportUnexpectedException(cantReadRecordDataBaseException);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }

    /**
     * Returns a unique UUID to the transaction ID
     * @param id
     * @return
     * @throws CantReadRecordDataBaseException
     */
    public UUID getNewUUID(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        //transforma string to UUID
        UUID newUUID = UUID.fromString(id);
        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable databaseTable = getDatabaseTable();
            databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = databaseTable.getRecords();


            /*
             * 3 -Check if there is any record with the same UUUID and get new one until its done.
             */

            while(records.size()>0){

                id =UUID.randomUUID().toString();
                databaseTable = getDatabaseTable();
                databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
                databaseTable.loadToMemory();
                records = databaseTable.getRecords();
                newUUID = UUID.fromString(id);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            reportUnexpectedException(cantReadRecordDataBaseException);
            throw cantReadRecordDataBaseException;
        }

        return newUUID;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CommunicationChatNetworkServiceDatabaseConstants</code>
     *
     * @return All ChatMetadataRecord.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationChatNetworkServiceDatabaseConstants
     */
    public List<ChatMetadataRecord> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<ChatMetadataRecord> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable templateTable = getDatabaseTable();
            templateTable.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            templateTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 3 - Create a list of ChatMetadataRecord objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  ChatMetadataRecord
                 */
                ChatMetadataRecord ChatMetadaTransactionRecord = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(ChatMetadaTransactionRecord);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            reportUnexpectedException(cantReadRecordDataBaseException);
           throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity ChatMetadataRecord to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(ChatMetadataRecord entity) throws CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {


            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDatabase().newTransaction();
            transaction.addRecordToInsert(getDatabaseTable(), entityRecord);
            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            reportUnexpectedException(cantInsertRecordDataBaseException);
            throw cantInsertRecordDataBaseException;

        }catch(Exception e){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself\n"+e.getMessage();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            reportUnexpectedException(cantInsertRecordDataBaseException);
            throw cantInsertRecordDataBaseException;
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ChatMetadataRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(ChatMetadataRecord entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */

            DatabaseTable metadataTable = getDatabaseTable();
            metadataTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, entity.getTransactionId().toString(), DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();
            if (metadataTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = metadataTable.getRecords().get(0);
            setValuesToRecord(record, entity);
            metadataTable.updateRecord(record);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | RecordsNotFoundException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            reportUnexpectedException(cantUpdateRecordDataBaseException);
            throw cantUpdateRecordDataBaseException;

        }

    }
    private void setValuesToRecord(DatabaseTableRecord entityRecord, ChatMetadataRecord chatMetadataRecord) {
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME,              chatMetadataRecord.getTransactionId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME,            chatMetadataRecord.getTransactionHash());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME,                      chatMetadataRecord.getChatId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDOBJECTO_COLUMN_NAME,                   chatMetadataRecord.getObjectId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORTYPE_COLUMN_NAME,              chatMetadataRecord.getLocalActorType().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORPUBKEY_COLUMN_NAME,            chatMetadataRecord.getLocalActorPublicKey());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORTYPE_COLUMN_NAME,             chatMetadataRecord.getRemoteActorType().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORPUBKEY_COLUMN_NAME,           chatMetadataRecord.getRemoteActorPublicKey());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATNAME_COLUMN_NAME,                    chatMetadataRecord.getChatName());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATSTATUS_COLUMN_NAME,                  chatMetadataRecord.getChatMessageStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_STATUS_COLUMN_NAME,              chatMetadataRecord.getMessageStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DATE_COLUMN_NAME,                        chatMetadataRecord.getDate().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDMENSAJE_COLUMN_NAME,                   chatMetadataRecord.getMessageId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_COLUMN_NAME,                     chatMetadataRecord.getMessage());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME,          chatMetadataRecord.getDistributionStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME,              chatMetadataRecord.getProcessed());
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id Long id.
     * @throws CantDeleteRecordDataBaseException
     */
    public void delete(Long id) throws CantDeleteRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {

            /*
             * Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDatabase().newTransaction();

            //falta configurar la llamada para borrar la entidad

            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            reportUnexpectedException(cantDeleteRecordDataBaseException);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * Create a instance of ChatMetadataRecord from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return ChatMetadataRecord setters the values from table
     */
    private ChatMetadataRecord constructFrom(DatabaseTableRecord record) {

        ChatMetadataRecord chatMetadataRecord = new ChatMetadataRecord();

        try {


            chatMetadataRecord.setTransactionId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME)));
            chatMetadataRecord.setTransactionHash(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME));
            chatMetadataRecord.setChatId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME)));
            chatMetadataRecord.setObjectId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDOBJECTO_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorType(PlatformComponentType.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorPublicKey(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setRemoteActorType(PlatformComponentType.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setRemoteActorPublicKey(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setChatName(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATNAME_COLUMN_NAME));
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setMessageStatus(MessageStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_STATUS_COLUMN_NAME)));
            chatMetadataRecord.setDate(Timestamp.valueOf(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DATE_COLUMN_NAME)));
            chatMetadataRecord.setMessageId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDMENSAJE_COLUMN_NAME)));
            chatMetadataRecord.setMessage(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_COLUMN_NAME));
            chatMetadataRecord.setDistributionStatus(DistributionStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setProcessed(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            reportUnexpectedException(FermatException.wrapException(e));
            return null;
        }

        return chatMetadataRecord;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a ChatMetadataRecord pass
     * by parameter
     *
     * @param chatMetadataRecord the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ChatMetadataRecord chatMetadataRecord) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();
        setValuesToRecord(entityRecord, chatMetadataRecord);
        return entityRecord;

    }

}
