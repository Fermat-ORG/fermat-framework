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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.interfaces.DAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;

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
public class IncomingNotificationDAO implements DAO {

    /**
     * Represent the database
     */
    private Database database;

    private PluginFileSystem pluginDatabaseSystem;
    private UUID pluginId;

    /**
     * Constructor
     * @param database
     * @param pluginFileSystem
     * @param pluginId
     */
    public IncomingNotificationDAO(Database database, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super();
        this.database = database;
        this.pluginDatabaseSystem = pluginFileSystem;
        this.pluginId = pluginId;
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
        return getDatabase().getTable(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_CHAT_TABLE_NOTIFICATION_TABLE_NAME);
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
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME, hash, DatabaseFilterType.EQUAL);
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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
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
        databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DistributionStatus.DELIVERING.getCode(), DatabaseFilterType.EQUAL);
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
            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        }

        return list;
    }

    /**
     *
     * @param id
     * @return
     * @throws CantReadRecordDataBaseException
     */
    @Deprecated
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
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
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
            databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
                databaseTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
                databaseTable.loadToMemory();
                records = databaseTable.getRecords();
                newUUID = UUID.fromString(id);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
     * @return All ChatMetadataRecord.
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataRecord> findAll() throws CantReadRecordDataBaseException {


        List<ChatMetadataRecord> list = null;

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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
     * the key are the att of the <code>CommunicationChatNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<ChatMetadataRecord>
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataRecord> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<ChatMetadataRecord> list = null;
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
             * 4 - Create a list of ChatMetadataRecord objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  ChatMetadataRecord
                 */
                ChatMetadataRecord ChatMetadaTransactionRecord = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(ChatMetadaTransactionRecord);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }catch(Exception e){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself\n"+e.getMessage();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    @Override
    public ChatMetadataRecord createNotification(ChatMetadataRecord chatMetadataRecord) throws CantCreateNotificationException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {
        create(chatMetadataRecord);
        return chatMetadataRecord;
    }

    @Override
    public ChatMetadataRecord getNotificationById(UUID transactionID) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (transactionID == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
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
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }

    @Override
    public void changeChatMessageState(String senderPublicKey, ChatMessageStatus chatMessageStatus) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException, CantReadRecordDataBaseException {
        if (senderPublicKey == null) {
            throw new IllegalArgumentException("The senderPublicKey is required, can not be null");
        }
        if (chatMessageStatus == null) {
            throw new IllegalArgumentException("The chatMessageStatus is required, can not be null");
        }
        ChatMetadataRecord chatMetadaTransactionRecord;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);
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
                if(chatMetadaTransactionRecord != null)
                {
                    chatMetadaTransactionRecord.setChatMessageStatus(chatMessageStatus);
                    update(chatMetadaTransactionRecord);
                }

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }
    }

    @Override
    public void changeChatMessageState(String senderPublicKey, MessageStatus messageStatus) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException, CantReadRecordDataBaseException {
        if (senderPublicKey == null) {
            throw new IllegalArgumentException("The senderPublicKey is required, can not be null");
        }
        if (messageStatus == null) {
            throw new IllegalArgumentException("The messageStatus is required, can not be null");
        }
        ChatMetadataRecord chatMetadaTransactionRecord;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);
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
                if(chatMetadaTransactionRecord != null)
                {
                    chatMetadaTransactionRecord.setMessageStatus(messageStatus);
                    update(chatMetadaTransactionRecord);
                }

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }
    }


    @Override
    public void changeChatProtocolState(UUID requestId, ChatProtocolState protocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, Exception {
        if (requestId == null) {
            throw new IllegalArgumentException("The senderPublicKey is required, can not be null");
        }
        if (protocolState == null) {
            throw new IllegalArgumentException("The messageStatus is required, can not be null");
        }
        ChatMetadataRecord chatMetadataRecord = getNotificationById(requestId);
        if(chatMetadataRecord != null && chatMetadataRecord.isFilled()){
            chatMetadataRecord.changeState(protocolState);
            update(chatMetadataRecord);
        }

    }

    @Override
    public List<ChatMetadata> listUnreadNotifications() throws CHTException {
        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ChatMetadata> chatMetadatas = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                chatMetadatas.add(constructFrom(record));
            }
            return chatMetadatas;

        } catch (Exception e) {

            throw new CHTException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        }
    }

    @Override
    public void markNotificationAsRead(UUID transactionID) throws CantConfirmNotificationException {
        try {

            ChatMetadataRecord chatMetadataRecord = getNotificationById(transactionID);

            chatMetadataRecord.setFlagReadead(true);

            update(chatMetadataRecord);

        } catch (CantGetNotificationException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+transactionID, "Error trying to get the notification.");
        } catch (NotificationNotFoundException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+transactionID, "Notification not found.");
        } catch (CantUpdateRecordDataBaseException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+transactionID, "Error updating database.");
        } catch (CantReadRecordDataBaseException e) {
            throw new CantConfirmNotificationException(e, "notificationId:"+transactionID, "Error reading database.");
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
            DatabaseTableRecord entityRecord = constructFrom(entity);
            DatabaseTableFilter filter = getDatabaseTable().getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(entity.getTransactionId().toString());
            filter.setColumn(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_CHAT_FIRST_KEY_COLUMN);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDatabase().newTransaction();
            getDatabaseTable().addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
            DatabaseTransaction transaction = getDatabase().newTransaction();

            //falta configurar la llamada para borrar la entidad

            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
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

            chatMetadataRecord.setTransactionId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME)));
            chatMetadataRecord.setTransactionHash(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME));
            chatMetadataRecord.setChatId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME)));
            chatMetadataRecord.setObjectId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorType(PlatformComponentType.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorPublicKey(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setRemoteActorType(PlatformComponentType.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setRemoteActorPublicKey(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setChatName(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME));
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setMessageStatus(MessageStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME)));
            chatMetadataRecord.setDate(Timestamp.valueOf(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DATE_COLUMN_NAME)));
            chatMetadataRecord.setMessageId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME)));
            chatMetadataRecord.setMessage(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME));
            chatMetadataRecord.setDistributionStatus(DistributionStatus.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setProcessed(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME));
            chatMetadataRecord.changeState(ChatProtocolState.getByCode(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME)));
            chatMetadataRecord.setSentDate(Timestamp.valueOf(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME)));
            chatMetadataRecord.setFlagReadead(Boolean.valueOf(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME)));
            chatMetadataRecord.setResponseToNotificationId(UUID.fromString(record.getStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME)));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
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

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME,              chatMetadataRecord.getTransactionId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME,            chatMetadataRecord.getTransactionHash());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME,                      chatMetadataRecord.getChatId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME,                   chatMetadataRecord.getObjectId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME,              chatMetadataRecord.getLocalActorType().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME,            chatMetadataRecord.getLocalActorPublicKey());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME,             chatMetadataRecord.getRemoteActorType().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME,           chatMetadataRecord.getRemoteActorPublicKey());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME,                    chatMetadataRecord.getChatName());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME,                  chatMetadataRecord.getChatMessageStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME,              chatMetadataRecord.getMessageStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DATE_COLUMN_NAME,                        chatMetadataRecord.getDate().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME,                   chatMetadataRecord.getMessageId().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME,                     chatMetadataRecord.getMessage());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME,          chatMetadataRecord.getDistributionStatus().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME,              chatMetadataRecord.getProcessed());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME,              chatMetadataRecord.getChatProtocolState().getCode());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME,                    chatMetadataRecord.getSentDate().toString());
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME,                   String.valueOf(chatMetadataRecord.isFlagReadead()));
        entityRecord.setStringValue(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, chatMetadataRecord.getResponseToNotificationId().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
