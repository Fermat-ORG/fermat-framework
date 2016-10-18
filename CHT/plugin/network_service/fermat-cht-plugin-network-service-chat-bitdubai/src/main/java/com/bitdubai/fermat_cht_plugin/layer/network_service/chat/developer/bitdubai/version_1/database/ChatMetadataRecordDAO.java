package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MessageMetadataRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMetadataRecordDAO {

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
        return getDatabase().getTable(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TABLE);
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

            DatabaseTable databaseTable = getDatabaseTableMessage();

            DatabaseTableRecord entityRecord = databaseTable.getEmptyRecord();

            setValuesToRecord(entityRecord, entity);

            databaseTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException databaseTransactionFailedException) {

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

    public void createNotification(MessageMetadataRecord messageMetadataRecord) throws CantCreateNotificationException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException {
        create(messageMetadataRecord);
    }

    public void deleteMessageByPackageId(UUID packageId) throws CantGetNotificationException, CantReadRecordDataBaseException {

        if (packageId == null)
            throw new IllegalArgumentException("The packageId is required, can not be null");

        try {

            DatabaseTable OUTGOINGMessageTable = getDatabaseTableMessage();

            OUTGOINGMessageTable.addUUIDFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            OUTGOINGMessageTable.deleteRecord();

        } catch (CantDeleteRecordException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: ").append(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }
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
        entityRecord.setUUIDValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_ID_COLUMN_NAME, messageMetadataRecord.getPackageId());
        entityRecord.setUUIDValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_MESSAGE_ID_COLUMN_NAME, messageMetadataRecord.getMessageId());
    }

    public UUID getMessageIdByPackageId(UUID packageId) throws CantGetNotificationException, CantReadRecordDataBaseException {

        if (packageId == null)
            throw new IllegalArgumentException("The packageId is required, can not be null");

        try {

            DatabaseTable OUTGOINGMessageTable = getDatabaseTableMessage();

            OUTGOINGMessageTable.addStringFilter(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_ID_COLUMN_NAME, packageId.toString(), DatabaseFilterType.EQUAL);

            OUTGOINGMessageTable.loadToMemory();

            List<DatabaseTableRecord> records = OUTGOINGMessageTable.getRecords();

            if (records.size() > 0)
                return records.get(0).getUUIDValue(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_MESSAGE_ID_COLUMN_NAME);
            else
                return null;

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
}
