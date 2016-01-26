/*
 * @#OutgoingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataTransactionRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingMessageDao</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gn¡mail.com) on 15/10/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMetaDataDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public ChatMetaDataDao(Database dataBase, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        super();
        this.dataBase = dataBase;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Return the Database
     *
     * @return Database
     */
    Database getDataBase() {
        return dataBase;
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TABLE_NAME);
    }

    /**
     * Method that find an ChatMetadataTransactionRecord by id in the data base.
     *
     * @param hash Long id.
     * @return ChatMetadataTransactionRecord found.
     * @throws CantReadRecordDataBaseException
     */

    public ChatMetadataTransactionRecord findByTransactionHash(String hash)throws CantReadRecordDataBaseException {
        if (hash == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataTransactionRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME, hash, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataTransactionRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataTransactionRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }
    public ChatMetadataTransactionRecord findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataTransactionRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataTransactionRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataTransactionRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }

    public UUID getNewUUID(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataTransactionRecord chatMetadaTransactionRecord = null;

        //transforma string to UUID
        UUID newUUID = UUID.fromString(id);
        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable databaseTable = getDatabaseTable();
            databaseTable.addStringFilter(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
                databaseTable.addStringFilter(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
                databaseTable.loadToMemory();
                records = databaseTable.getRecords();
                newUUID = UUID.fromString(id);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return newUUID;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All ChatMetadataTransactionRecord.
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataTransactionRecord> findAll() throws CantReadRecordDataBaseException {


        List<ChatMetadataTransactionRecord> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 3 - Create a list of ChatMetadataTransactionRecord objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ChatMetadataTransactionRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  ChatMetadataTransactionRecord
                 */
                ChatMetadataTransactionRecord ChatMetadaTransactionRecord = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(ChatMetadaTransactionRecord);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>NetworkServiceChatNetworkServiceDatabaseConstants</code>
     *
     * @return All ChatMetadataTransactionRecord.
     * @throws CantReadRecordDataBaseException
     * @see NetworkServiceChatNetworkServiceDatabaseConstants
     */
    public List<ChatMetadataTransactionRecord> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<ChatMetadataTransactionRecord> list = null;

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
             * 3 - Create a list of ChatMetadataTransactionRecord objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ChatMetadataTransactionRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  ChatMetadataTransactionRecord
                 */
                ChatMetadataTransactionRecord ChatMetadaTransactionRecord = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(ChatMetadaTransactionRecord);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>NetworkServiceChatNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<ChatMetadataTransactionRecord>
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataTransactionRecord> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<ChatMetadataTransactionRecord> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }


            /*
             * 2 - load the data base to memory with filters
             */
            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 4 - Create a list of ChatMetadataTransactionRecord objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ChatMetadataTransactionRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  ChatMetadataTransactionRecord
                 */
                ChatMetadataTransactionRecord ChatMetadaTransactionRecord = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(ChatMetadaTransactionRecord);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }
    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            dataBase = pluginDatabaseSystem.openDatabase(this.pluginId, NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (DatabaseNotFoundException e) {
            NetworkServiceChatNetworkServiceDatabaseFactory networkServiceChatNetworkServiceDatabaseFactory = new NetworkServiceChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);
            dataBase = networkServiceChatNetworkServiceDatabaseFactory.createDatabase(this.pluginId, NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        }
        return dataBase;
    }
    private DatabaseTable getDatabaseTable(String tableName) {
        return dataBase.getTable(tableName);
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        if (table.getRecords().isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity ChatMetadataTransactionRecord to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(ChatMetadataTransactionRecord entity) throws CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {

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
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToInsert(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }catch(Exception e){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself\n"+e.getMessage();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ChatMetadataTransactionRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(ChatMetadataTransactionRecord entity) throws CantUpdateRecordDataBaseException {

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
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }

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
            DatabaseTransaction transaction = getDataBase().newTransaction();

            //falta configurar la llamada para borrar la entidad

            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkServiceChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * Create a instance of ChatMetadataTransactionRecord from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return ChatMetadataTransactionRecord setters the values from table
     */
    private ChatMetadataTransactionRecord constructFrom(DatabaseTableRecord record) {

        ChatMetadataTransactionRecord chatMetadataTransactionRecord = new ChatMetadataTransactionRecord();

        try {

            chatMetadataTransactionRecord.setTransactionId(UUID.fromString(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME)));
            chatMetadataTransactionRecord.setTransactionHash(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME));
            chatMetadataTransactionRecord.setChatId(UUID.fromString(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME)));
            chatMetadataTransactionRecord.setObjectId(UUID.fromString(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDOBJECTO_COLUMN_NAME)));
            chatMetadataTransactionRecord.setLocalActorType(PlatformComponentType.getByCode(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORTYPE_COLUMN_NAME)));
            chatMetadataTransactionRecord.setLocalActorPublicKey(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORPUBKEY_COLUMN_NAME));
            chatMetadataTransactionRecord.setRemoteActorType(PlatformComponentType.getByCode(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORTYPE_COLUMN_NAME)));
            chatMetadataTransactionRecord.setRemoteActorPublicKey(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORPUBKEY_COLUMN_NAME));
            chatMetadataTransactionRecord.setChatName(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_CHATNAME_COLUMN_NAME));
            chatMetadataTransactionRecord.setChatMessageStatus(ChatMessageStatus.getByCode(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_CHATSTATUS_COLUMN_NAME)));
            chatMetadataTransactionRecord.setMessageStatus(MessageStatus.getByCode(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_STATUS_COLUMN_NAME)));
            chatMetadataTransactionRecord.setDate(Timestamp.valueOf(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_DATE_COLUMN_NAME)));
            chatMetadataTransactionRecord.setMessageId(UUID.fromString(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDMENSAJE_COLUMN_NAME)));
            chatMetadataTransactionRecord.setMessage(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_COLUMN_NAME));
            chatMetadataTransactionRecord.setDistributionStatus(DistributionStatus.getByCode(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME)));
            chatMetadataTransactionRecord.setProcessed(record.getStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return chatMetadataTransactionRecord;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a ChatMetadataTransactionRecord pass
     * by parameter
     *
     * @param chatMetadataTransactionRecord the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ChatMetadataTransactionRecord chatMetadataTransactionRecord) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME,              chatMetadataTransactionRecord.getTransactionId().toString());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME,            chatMetadataTransactionRecord.getTransactionHash());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME,                      chatMetadataTransactionRecord.getChatId().toString());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDOBJECTO_COLUMN_NAME,                   chatMetadataTransactionRecord.getObjectId().toString());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORTYPE_COLUMN_NAME,              chatMetadataTransactionRecord.getLocalActorType().getCode());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORPUBKEY_COLUMN_NAME,            chatMetadataTransactionRecord.getLocalActorPublicKey());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORTYPE_COLUMN_NAME,             chatMetadataTransactionRecord.getRemoteActorType().getCode());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORPUBKEY_COLUMN_NAME,           chatMetadataTransactionRecord.getRemoteActorPublicKey());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_CHATNAME_COLUMN_NAME,                    chatMetadataTransactionRecord.getChatName());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_CHATSTATUS_COLUMN_NAME,                  chatMetadataTransactionRecord.getChatMessageStatus().getCode());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_STATUS_COLUMN_NAME,              chatMetadataTransactionRecord.getMessageStatus().getCode());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_DATE_COLUMN_NAME,                        chatMetadataTransactionRecord.getDate().toString());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDMENSAJE_COLUMN_NAME,                   chatMetadataTransactionRecord.getMessageId().toString());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_COLUMN_NAME,                     chatMetadataTransactionRecord.getMessage());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME,          chatMetadataTransactionRecord.getDistributionStatus().getCode());
        entityRecord.setStringValue(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME,              chatMetadataTransactionRecord.getProcessed());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
