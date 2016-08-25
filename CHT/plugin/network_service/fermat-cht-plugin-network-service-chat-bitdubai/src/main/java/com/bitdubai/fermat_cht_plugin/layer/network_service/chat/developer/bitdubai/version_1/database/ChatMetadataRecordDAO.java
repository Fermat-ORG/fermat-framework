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
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.interfaces.DAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MessageMetadataRecord;

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

    public DatabaseTable getDatabaseTableMessage() {
        return getDatabase().getTable(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_TABLE);
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
    private void create(MessageMetadataRecord entity) throws CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {

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

            DatabaseTable databaseTable = getDatabaseTableMessage();
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
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself\n" + e.getMessage();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    @Override
    public void createNotification(MessageMetadataRecord messageMetadataRecord) throws CantCreateNotificationException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {
        create(messageMetadataRecord);
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ChatMetadataRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(MessageMetadataRecord entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTable databaseTable = getDatabaseTableMessage();
            databaseTable.addStringFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, entity.getTransactionId().toString(), DatabaseFilterType.EQUAL);
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
     * Create a instance of ChatMetadataRecord from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return ChatMetadataRecord setters the values from table
     */
    private MessageMetadataRecord constructFromMessageMetadata(DatabaseTableRecord record) {

        MessageMetadataRecord messageMetadataRecord = new MessageMetadataRecord();

        try {

            messageMetadataRecord.setTransactionId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME)));
            messageMetadataRecord.setChatMessageTransactionType(ChatMessageTransactionType.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_TRANSACTION_TYPE_COLUMN_NAME)));
            messageMetadataRecord.setLocalActorType(PlatformComponentType.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME)));
            messageMetadataRecord.setLocalActorPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME));
            messageMetadataRecord.setRemoteActorType(PlatformComponentType.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME)));
            messageMetadataRecord.setRemoteActorPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME));
            messageMetadataRecord.setMessageStatus(MessageStatus.getByCode(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME)));
            messageMetadataRecord.setDate(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME));
            messageMetadataRecord.setMessageId(UUID.fromString(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_IDMESSAGE_COLUMN_NAME)));
            messageMetadataRecord.setMessage(record.getStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            return null;
        }

        return messageMetadataRecord;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a ChatMetadataRecord pass
     * by parameter
     *
     * @param messageMetadataRecord the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(MessageMetadataRecord messageMetadataRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Create the record to the entity
         */
        DatabaseTable databaseTable = getDatabaseTableMessage();
        DatabaseTableRecord entityRecord = databaseTable.getEmptyRecord();

        /*
         * return the new table record
         */
        setValuesToRecord(entityRecord, messageMetadataRecord);
        return entityRecord;

    }

    /**
     * @param messageMetadataRecord
     * @param entityRecord
     * @throws CantOpenDatabaseException
     * @throws CantCreateDatabaseException
     */
    private void setValuesToRecord(DatabaseTableRecord entityRecord, MessageMetadataRecord messageMetadataRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, messageMetadataRecord.getTransactionId().toString());
        entityRecord.setFermatEnum(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_TRANSACTION_TYPE_COLUMN_NAME, messageMetadataRecord.getChatMessageTransactionType());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME, messageMetadataRecord.getLocalActorType().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME, messageMetadataRecord.getLocalActorPublicKey());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME, messageMetadataRecord.getRemoteActorType().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME, messageMetadataRecord.getRemoteActorPublicKey());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME, messageMetadataRecord.getMessageStatus().getCode());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME, messageMetadataRecord.getDate());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_IDMESSAGE_COLUMN_NAME, messageMetadataRecord.getMessageId().toString());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME, messageMetadataRecord.getMessage());
    }

    public MessageMetadataRecord getMessageNotificationByMessageId(UUID messageId) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (messageId == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        MessageMetadataRecord messageMetadataRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable OUTGOINGMessageTable = getDatabaseTableMessage();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_IDMESSAGE_COLUMN_NAME, messageId.toString(), DatabaseFilterType.EQUAL);
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
                messageMetadataRecord = constructFromMessageMetadata(record);
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

        return messageMetadataRecord;
    }

    public MessageMetadataRecord getMessageNotificationByTransactionId(UUID transactionId) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (transactionId == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        MessageMetadataRecord messageMetadataRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable OUTGOINGMessageTable = getDatabaseTableMessage();
            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
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
                messageMetadataRecord = constructFromMessageMetadata(record);
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

        return messageMetadataRecord;
    }
}
