package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose Briceño josebricenor@gmail.com on 18/02/16.
 */
public class DAPMessageDAO {

    //VARIABLE DECLARATION

    private final Database database;

    public DAPMessageDAO(Database database) {
        this.database = database;
    }


    //PUBLIC METHODS

    //CRUD OPERATIONS

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity DAPMessage to create.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException
     */
    public void create(DAPMessage entity, MessageStatus status) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable table = getDAPMessagesTable();
            DatabaseTableRecord entityRecord = table.getEmptyRecord();
            setValuesToRecord(entityRecord, entity, status);
            table.insertRecord(entityRecord);
        } catch (CantInsertRecordException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity DAPMessage to update.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException
     */
    public void update(DAPMessage entity, MessageStatus status) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable metadataTable = getDAPMessagesTable();
            metadataTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, entity.getIdMessage().toString(), DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();

            if (metadataTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = metadataTable.getRecords().get(0);
            setValuesToRecord(record, entity, status);
            metadataTable.updateRecord(record);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | RecordsNotFoundException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id String id.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantDeleteRecordDataBaseException
     */
    public void delete(String id) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantDeleteRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {
            DatabaseTable table = getDAPMessagesTable();
            table.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = table.getRecords().get(0);
            table.deleteRecord(record);

        } catch (CantDeleteRecordException e) {
            e.printStackTrace();
        } catch (RecordsNotFoundException e) {
            e.printStackTrace();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }

    }

    public List<DAPMessage> findAllDAPMessages() throws DAPException {
        try {

            return constructDAPMessageListFromRecord(getDAPMessagesTable().getRecords());
        } catch (org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantLoadDAPMessageException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    /**
     * Method that find an DigitalAssetMetadataTransactionImpl by id in the data base.
     *
     * @param id Long id.
     * @return DigitalAssetMetadataTransactionImpl found.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException
     */
    public DAPMessage findById(String id) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        DAPMessage dapMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable dapMessagesTable = getDAPMessagesTable();
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            dapMessagesTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = dapMessagesTable.getRecords();


            /*
             * 3 - Convert into DAPMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  DAPMessage
                 */
                dapMessage = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return dapMessage;
    }

    /**
     * Method that find an unread DAPMessage by Type in the data base.
     *
     * @param code String.
     * @return A list of DAPMessage found.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException
     */
    public List<DAPMessage> findUnreadByType(String code) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException {

        if (code == null) {
            throw new IllegalArgumentException("The code type is required, can not be null");
        }

        List<DAPMessage> listDAPMessage = new ArrayList<DAPMessage>();
        DAPMessage dapMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable dapMessagesTable = getDAPMessagesTable();
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, MessageStatus.NEW_RECEIVED.getCode(), DatabaseFilterType.EQUAL);
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME, code, DatabaseFilterType.EQUAL);
            dapMessagesTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = dapMessagesTable.getRecords();


            /*
             * 3 - Convert into DAPMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  DAPMessage
                 */
                dapMessage = constructFrom(record);
                listDAPMessage.add(dapMessage);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /**
     * Method that find an unread DAPMessage by Subject in the data base.
     *
     * @param code String.
     * @return List of DAPMessage found.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException
     */
    public List<DAPMessage> findUnreadBySubject(String code) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException {

        if (code == null) {
            throw new IllegalArgumentException("The code subject is required, can not be null");
        }

        List<DAPMessage> listDAPMessage = new ArrayList<DAPMessage>();
        DAPMessage dapMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable dapMessagesTable = getDAPMessagesTable();
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, MessageStatus.NEW_RECEIVED.getCode(), DatabaseFilterType.EQUAL);
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME, code, DatabaseFilterType.EQUAL);
            dapMessagesTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = dapMessagesTable.getRecords();


            /*
             * 3 - Convert into DAPMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  DAPMessage
                 */
                dapMessage = constructFrom(record);
                listDAPMessage.add(dapMessage);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /**
     * Method that find an unread DAPMessage by Subject in the data base.
     *
     * @return List of DAPMessage found.
     * @throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException
     */
    public List<DAPMessage> findUnreadMessages() throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException {

        List<DAPMessage> listDAPMessage = new ArrayList<DAPMessage>();
        DAPMessage dapMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable dapMessagesTable = getDAPMessagesTable();
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, MessageStatus.NEW_RECEIVED.getCode(), DatabaseFilterType.EQUAL);
            dapMessagesTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = dapMessagesTable.getRecords();


            /*
             * 3 - Convert into DAPMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  DAPMessage
                 */
                dapMessage = constructFrom(record);
                listDAPMessage.add(dapMessage);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /*
    * Confirm the reception of the DAPMessage updating its state to READ
    *
    * */

    public void confirmDAPMessageReception(DAPMessage dapMessage) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantUpdateRecordDataBaseException {
        update(dapMessage, MessageStatus.READ);
    }

    /**
     * Create a instance of DAPMessage from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return DAPMessage setters the values from table
     */
    private DAPMessage constructFrom(DatabaseTableRecord record) {
        return DAPMessage.fromXML(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME));
    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, DAPMessage dapMessage, MessageStatus state) {
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, dapMessage.getIdMessage().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME, dapMessage.getMessageContent().messageType().getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME, dapMessage.getSubject().getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, state.getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME, dapMessage.toXML());
    }

    private DAPMessage constructDAPMessageByDatabaseRecord(DatabaseTableRecord record) throws InvalidParameterException, org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantLoadDAPMessageException {
        return DAPMessage.fromXML(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME));
    }

    private List<DAPMessage> constructDAPMessageListFromRecord(List<DatabaseTableRecord> records) throws org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantLoadDAPMessageException, InvalidParameterException {
        List<DAPMessage> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            toReturn.add(constructDAPMessageByDatabaseRecord(record));
        }
        return toReturn;
    }


    //GETTER AND SETTERS

    /**
     * Return the Database
     *
     * @return Database
     */
    Database getDataBase() {
        return database;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private DatabaseTable getDAPMessagesTable() {
        return getDatabaseTable(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TABLE_NAME);
    }
}
