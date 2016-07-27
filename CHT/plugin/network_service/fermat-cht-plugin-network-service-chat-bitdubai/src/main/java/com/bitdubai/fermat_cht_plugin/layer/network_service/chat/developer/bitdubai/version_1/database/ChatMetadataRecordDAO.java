package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.interfaces.DAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMetadataRecordDAO implements DAO {

    private Database database;

    public ChatMetadataRecordDAO(final Database database) {
        super();
        this.database = database;
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDatabase().getTable(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_TABLE);
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @return List<ChatMetadataRecord>
     * @throws CantReadRecordDataBaseException
     */
    public List<ChatMetadataRecord> findAllToSend(ChatProtocolState chatProtocolState, DistributionStatus distributionStatus) throws CantReadRecordDataBaseException, CantLoadTableToMemoryException {

        DatabaseTable databaseTable = getDatabaseTable();
        databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, chatProtocolState.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DISTRIBUTIONSTATUS_COLUMN_NAME, distributionStatus.getCode(), DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();

            /*
             * 2 - read all records
             */
        List<DatabaseTableRecord> records = databaseTable.getRecords();

        List<ChatMetadataRecord> list = new ArrayList<>();
        try {
            if (!records.isEmpty() && records.size() > 0) {
                for (DatabaseTableRecord record : records) {
                    list.add(constructFrom(record));
                }
            }

        } catch (Exception e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            throw cantReadRecordDataBaseException;
        }
        return list;
    }

    /**
     * Returns a unique UUID to the transaction ID
     *
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
            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = databaseTable.getRecords();


            /*
             * 3 -Check if there is any record with the same UUUID and get new one until its done.
             */

            while (records.size() > 0) {

                id = UUID.randomUUID().toString();
                databaseTable = getDatabaseTable();
                databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
                databaseTable.loadToMemory();
                newUUID = UUID.fromString(id);
                records = databaseTable.getRecords();


            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return newUUID;
    }

    public Database getDatabase() {
        return database;
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

            DatabaseTable databaseTable = getDatabaseTable();
            DatabaseTransaction transaction = getDatabase().newTransaction();
            transaction.addRecordToInsert(databaseTable, entityRecord);
            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        } catch (Exception e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = new StringBuilder().append("The Template Database triggered an unexpected problem that wasn't able to solve by itself\n").append(e.getMessage()).toString();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    @Override
    public void createNotification(ChatMetadataRecord chatMetadataRecord) throws CantCreateNotificationException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {
        create(chatMetadataRecord);
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
            DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


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
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }

    public ChatMetadataRecord getNotificationByChatAndMessageId(UUID chatId, UUID messageId) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
        if (chatId == null) {
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDMENSAJE_COLUMN_NAME, messageId.toString(), DatabaseFilterType.EQUAL);
        } else if (messageId == null) {
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDCHAT_COLUMN_NAME, chatId.toString(), DatabaseFilterType.EQUAL);
        } else if (chatId != null && messageId != null) {
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDMENSAJE_COLUMN_NAME, messageId.toString(), DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDCHAT_COLUMN_NAME, chatId.toString(), DatabaseFilterType.EQUAL);
        }

        if (chatId == null && messageId == null)
            new IllegalArgumentException("The id is required, can not be null");

        ChatMetadataRecord chatMetadaTransactionRecord = null;

        try {
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


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
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return chatMetadaTransactionRecord;
    }

    public String getNotificationResponseToByID(UUID transactionID) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (transactionID == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        String responseTO;
        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataRecord objects
             */
            ChatMetadataRecord chatMetadaTransactionRecord = null;
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
            }
            responseTO = (chatMetadaTransactionRecord != null) ? chatMetadaTransactionRecord.getResponseToNotification() : null;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return responseTO;
    }

    public ChatMetadataRecord getNotificationByResponseTo(UUID transactionID) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (transactionID == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ChatMetadataRecord chatMetadaTransactionRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_RESPONSE_TO_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


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
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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

            DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
                if (chatMetadaTransactionRecord != null) {
                    chatMetadaTransactionRecord.setChatMessageStatus(chatMessageStatus);
                    update(chatMetadaTransactionRecord);
                }

            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
            DatabaseTable OUTGOINGMessageTable = getDatabaseTable();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);
            OUTGOINGMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();


            /*
             * 3 - Convert into ChatMetadataRecord objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ChatMetadataRecord
                 */
                chatMetadaTransactionRecord = constructFrom(record);
                if (chatMetadaTransactionRecord != null) {
                    chatMetadaTransactionRecord.setMessageStatus(messageStatus);
                    update(chatMetadaTransactionRecord);
                }

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }
    }


    @Override
    public void changeChatProtocolState(UUID requestId, ChatProtocolState protocolState) throws CHTException, CantGetNotificationException, NotificationNotFoundException {
        if (requestId == null) {
            throw new IllegalArgumentException("The senderPublicKey is required, can not be null");
        }
        if (protocolState == null) {
            throw new IllegalArgumentException("The messageStatus is required, can not be null");
        }
        ChatMetadataRecord chatMetadataRecord = getNotificationById(requestId);
        if (chatMetadataRecord != null) {
            chatMetadataRecord.changeState(protocolState);
            update(chatMetadataRecord);
        }

    }

    @Override
    public List<ChatMetadataRecord> listRequestsByChatProtocolStateAndDistributionStatus(ChatProtocolState chatProtocolState, DistributionStatus distributionStatus) throws CantReadRecordDataBaseException, CantLoadTableToMemoryException {
        return findAllToSend(chatProtocolState, distributionStatus);
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>ChatNetworkServiceDataBaseConstants</code>
     *
     * @return All ChatMetadataRecord.
     * @throws CantReadRecordDataBaseException
     * @see ChatNetworkServiceDataBaseConstants
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
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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

    @Override
    public List<ChatMetadata> listUnreadNotifications() throws CHTException {
        try {
            DatabaseTable databaseTable = getDatabaseTable();

            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENT_COUNT_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();

            List<ChatMetadata> chatMetadatas = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                chatMetadatas.add(constructFrom(record));
            }
            return chatMetadatas;

        } catch (Exception e) {
            CHTException chtException = new CHTException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            throw chtException;
        }
    }

    @Override
    public void markNotificationAsRead(UUID transactionID) throws CantConfirmNotificationException {
        try {

            ChatMetadataRecord chatMetadataRecord = getNotificationById(transactionID);

            chatMetadataRecord.setFlagReadead(true);

            update(chatMetadataRecord);

        } catch (CantGetNotificationException e) {
            CantConfirmNotificationException cantConfirmNotificationException = new CantConfirmNotificationException(e, new StringBuilder().append("notificationId: ").append(transactionID).toString(), "Error trying to get the notification.");
            throw cantConfirmNotificationException;
        } catch (NotificationNotFoundException e) {
            CantConfirmNotificationException cantConfirmNotificationException = new CantConfirmNotificationException(e, new StringBuilder().append("notificationId: ").append(transactionID).toString(), "Notification not found.");
            throw cantConfirmNotificationException;
        } catch (CantUpdateRecordDataBaseException e) {
            CantConfirmNotificationException cantConfirmNotificationException = new CantConfirmNotificationException(e, new StringBuilder().append("notificationId: ").append(transactionID).toString(), "Error updating getDatabase().");
            throw cantConfirmNotificationException;
        } catch (CantReadRecordDataBaseException e) {
            CantConfirmNotificationException cantConfirmNotificationException = new CantConfirmNotificationException(e, new StringBuilder().append("notificationId: ").append(transactionID).toString(), "Error reading getDatabase().");
            throw cantConfirmNotificationException;
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
            DatabaseTable databaseTable = getDatabaseTable();
            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, entity.getTransactionId().toString(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            setValuesToRecord(record, entity);
            databaseTable.updateRecord(record);

        } catch (CantUpdateRecordException | CantCreateDatabaseException | CantOpenDatabaseException | RecordsNotFoundException | CantLoadTableToMemoryException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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
    public void delete(UUID id) throws CantDeleteRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {

            /*
             * Create a new transaction and execute
             */

            DatabaseTable databaseTable = getDatabaseTable();
            DatabaseTableFilter filter = databaseTable.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(id.toString());
            filter.setColumn(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_FIRST_KEY_COLUMN);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDatabase().newTransaction();
            databaseTable.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
            getDatabase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

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

            chatMetadataRecord.setTransactionId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME)));
            chatMetadataRecord.setResponseToNotification(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_RESPONSE_TO_COLUMN_NAME));
            chatMetadataRecord.setChatId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDCHAT_COLUMN_NAME)));
            chatMetadataRecord.setObjectId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDOBJECTO_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorType(PlatformComponentType.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setLocalActorPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setRemoteActorType(PlatformComponentType.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME)));
            chatMetadataRecord.setRemoteActorPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME));
            chatMetadataRecord.setChatName(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATNAME_COLUMN_NAME));
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setMessageStatus(MessageStatus.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME)));
            chatMetadataRecord.setDate(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME));
            chatMetadataRecord.setMessageId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDMENSAJE_COLUMN_NAME)));
            chatMetadataRecord.setMessage(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME));
            chatMetadataRecord.setDistributionStatus(DistributionStatus.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DISTRIBUTIONSTATUS_COLUMN_NAME)));
            chatMetadataRecord.setProcessed(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROCCES_STATUS_COLUMN_NAME));
            chatMetadataRecord.changeState(ChatProtocolState.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME)));
            chatMetadataRecord.setSentDate(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENTDATE_COLUMN_NAME));
            chatMetadataRecord.setSentCount(record.getIntegerValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENT_COUNT_COLUMN_NAME));
            chatMetadataRecord.setFlagReadead(Boolean.valueOf(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_READ_MARK_COLUMN_NAME)));
            chatMetadataRecord.setMsgXML(record.getStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_XML_MSG_REPRESENTATION));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
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
    private DatabaseTableRecord constructFrom(ChatMetadataRecord chatMetadataRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Create the record to the entity
         */
        DatabaseTable databaseTable = getDatabaseTable();
        DatabaseTableRecord entityRecord = databaseTable.getEmptyRecord();

        /*
         * return the new table record
         */
        setValuesToRecord(entityRecord, chatMetadataRecord);
        return entityRecord;

    }

    /**
     * @param chatMetadataRecord
     * @param entityRecord
     * @throws CantOpenDatabaseException
     * @throws CantCreateDatabaseException
     */
    private void setValuesToRecord(DatabaseTableRecord entityRecord, ChatMetadataRecord chatMetadataRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, chatMetadataRecord.getTransactionId().toString());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_RESPONSE_TO_COLUMN_NAME, chatMetadataRecord.getResponseToNotification());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDCHAT_COLUMN_NAME, chatMetadataRecord.getChatId().toString());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDOBJECTO_COLUMN_NAME, chatMetadataRecord.getObjectId().toString());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME, chatMetadataRecord.getLocalActorType().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME, chatMetadataRecord.getLocalActorPublicKey());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME, chatMetadataRecord.getRemoteActorType().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME, chatMetadataRecord.getRemoteActorPublicKey());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATNAME_COLUMN_NAME, chatMetadataRecord.getChatName());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_CHATSTATUS_COLUMN_NAME, chatMetadataRecord.getChatMessageStatus().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME, chatMetadataRecord.getMessageStatus().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME, chatMetadataRecord.getDate());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_IDMENSAJE_COLUMN_NAME, chatMetadataRecord.getMessageId().toString());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME, chatMetadataRecord.getMessage());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_DISTRIBUTIONSTATUS_COLUMN_NAME, chatMetadataRecord.getDistributionStatus().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROCCES_STATUS_COLUMN_NAME, chatMetadataRecord.getProcessed());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, chatMetadataRecord.getChatProtocolState().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENTDATE_COLUMN_NAME, chatMetadataRecord.getSentDate());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_READ_MARK_COLUMN_NAME, String.valueOf(chatMetadataRecord.isFlagReadead()));
        entityRecord.setIntegerValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_SENT_COUNT_COLUMN_NAME, chatMetadataRecord.getSentCount());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_XML_MSG_REPRESENTATION, chatMetadataRecord.getMsgXML());

    }


    public void changeStatusNotSentMessage() throws CantUpdateRecordDataBaseException, CantOpenDatabaseException, CantCreateDatabaseException {


        try {
            DatabaseTable databaseTable = getDatabaseTable();

            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();

            for (DatabaseTableRecord record : records) {
                //update record

                record.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.PROCESSING_SEND.getCode());
                update(constructFrom(record));

            }
        } catch (CantLoadTableToMemoryException e) {

            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            throw cantUpdateRecordDataBaseException;
        }
    }

    public void changeStatusNotSentMessage(String receiveIdentityKey) throws CantUpdateRecordDataBaseException {


        try {
            DatabaseTable databaseTable = getDatabaseTable();

            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);

            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME, receiveIdentityKey, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();


            for (DatabaseTableRecord record : records) {

                //update record

                record.setStringValue(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROTOCOL_STATE_COLUMN_NAME, ChatProtocolState.PROCESSING_SEND.getCode());

                databaseTable.updateRecord(record);
            }


        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            throw cantUpdateRecordDataBaseException;
        }
    }


}
