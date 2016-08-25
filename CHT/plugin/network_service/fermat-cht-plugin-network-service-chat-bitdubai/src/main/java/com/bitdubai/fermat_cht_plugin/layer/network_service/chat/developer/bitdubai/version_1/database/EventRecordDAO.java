package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handler.EventRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MessageMetadataRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 24/08/16.
 */
public class EventRecordDAO {

    private Database database;

    public EventRecordDAO(final Database database) {
        super();
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public DatabaseTable getDatabaseTableEvent() {
        return getDatabase().getTable(ChatNetworkServiceDataBaseConstants.P2P_CLIENT_EVENT_RECORD_TABLE);
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a ChatMetadataRecord pass
     * by parameter
     *
     * @param eventRecord the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(EventRecord eventRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Create the record to the entity
         */
        DatabaseTable databaseTable = getDatabaseTableEvent();
        DatabaseTableRecord entityRecord = databaseTable.getEmptyRecord();

        /*
         * return the new table record
         */
        setValuesToRecord(entityRecord, eventRecord);
        return entityRecord;

    }

    /**
     * @param eventRecord
     * @param entityRecord
     * @throws CantOpenDatabaseException
     * @throws CantCreateDatabaseException
     */
    private void setValuesToRecord(DatabaseTableRecord entityRecord, EventRecord eventRecord) throws CantOpenDatabaseException, CantCreateDatabaseException {

        /*
         * Set the entity values
         */
        entityRecord.setUUIDValue(ChatNetworkServiceDataBaseConstants.PACKAGE_ID_RECORD_COLUMN_NAME, eventRecord.getEventPackageId());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.SENDER_PUBLIC_KEY_COLUMN_NAME, eventRecord.getSenderPublicKey());
        entityRecord.setStringValue(ChatNetworkServiceDataBaseConstants.DESTINATION_PUBLICK_KEY_COLUMN_NAME, eventRecord.getDestinationPublicKey());

    }
    /**
     * Method that create a new entity in the data base.
     *
     * @param entity ChatMetadataRecord to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void persist(EventRecord entity) throws CantInsertRecordDataBaseException {

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

            DatabaseTable databaseTable = getDatabaseTableEvent();
            databaseTable.insertRecord(entityRecord);

        } catch (Exception e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself\n" + e.getMessage();
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(e.getMessage(), e, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ChatMetadataRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(EventRecord entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTable databaseTable = getDatabaseTableEvent();
            databaseTable.addUUIDFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME, entity.getEventPackageId(), DatabaseFilterType.EQUAL);
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
    private EventRecord constructFrom(DatabaseTableRecord record) {

        EventRecord eventRecord = new EventRecord();

        try {

            eventRecord.setEventPackageId(record.getUUIDValue(ChatNetworkServiceDataBaseConstants.PACKAGE_ID_RECORD_COLUMN_NAME));
            eventRecord.setSenderPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.SENDER_PUBLIC_KEY_COLUMN_NAME));
            eventRecord.setDestinationPublicKey(record.getStringValue(ChatNetworkServiceDataBaseConstants.DESTINATION_PUBLICK_KEY_COLUMN_NAME));

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            return null;
        }

        return eventRecord;
    }
    public EventRecord getEventById(UUID packageId) throws CantGetNotificationException, NotificationNotFoundException, CantReadRecordDataBaseException {

        if (packageId == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        EventRecord eventRecord = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable OUTGOINGMessageTable = getDatabaseTableEvent();
            OUTGOINGMessageTable.addUUIDFilter(ChatNetworkServiceDataBaseConstants.PACKAGE_ID_RECORD_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);
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
                eventRecord = constructFrom(record);
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

        return eventRecord;
    }
}
