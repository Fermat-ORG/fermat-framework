package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessageGson;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantLoadDAPMessageException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose Briceño josebricenor@gmail.com on 18/02/16.
 */
public class DAPMessageDAO {

    //VARIABLE DECLARATION

    private final Database database;
    private final OutgoingMessageDao outgoingMessageDao;

    public DAPMessageDAO(Database database, OutgoingMessageDao outgoingMessageDao) {
        this.database = database;
        this.outgoingMessageDao = outgoingMessageDao;
    }


    //PUBLIC METHODS

    //CRUD OPERATIONS

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity DAPMessage to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(DAPMessage entity) throws CantInsertRecordDataBaseException {

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
            transaction.addRecordToInsert(getDAPMessagesTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

            } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity DAPMessage to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(DAPMessage entity, String state) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable metadataTable = getDAPMessagesTable();
            metadataTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, entity.getIdMessage().toString(), DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();

            if (metadataTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = metadataTable.getRecords().get(0);

            if (!metadataTable.getRecords().get(0).getStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME).equals("READ") && state==null){
                state="UNREAD";
            }else if (metadataTable.getRecords().get(0).getStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME).equals("READ") && state==null){
                state="READ";
            }

            setValuesToRecord(record, entity, state);
            metadataTable.updateRecord(record);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | RecordsNotFoundException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id String id.
     * @throws CantDeleteRecordDataBaseException
     */
    public void delete(String id) throws CantDeleteRecordDataBaseException {

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
        } catch (CantLoadDAPMessageException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    /**
     * Method that find an DigitalAssetMetadataTransactionImpl by id in the data base.
     *
     * @param id Long id.
     * @return DigitalAssetMetadataTransactionImpl found.
     * @throws CantReadRecordDataBaseException
     */
    public DAPMessage findById(String id) throws CantReadRecordDataBaseException {

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
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return dapMessage;
    }

    /**
     * Method that find an unread DAPMessage by Type in the data base.
     *
     * @param code String.
     * @return A list of DAPMessage found.
     * @throws CantReadRecordDataBaseException
     *
     * */
    public List<DAPMessage> findUnreadByType(String code) throws CantReadRecordDataBaseException {

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
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, "UNREAD", DatabaseFilterType.EQUAL);
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
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /**
     * Method that find an unread DAPMessage by Subject in the data base.
     *
     * @param code String.
     * @return List of DAPMessage found.
     * @throws CantReadRecordDataBaseException
     */
    public List<DAPMessage> findUnreadBySubject(String code) throws CantReadRecordDataBaseException {

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
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, "UNREAD", DatabaseFilterType.EQUAL);
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
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /**
     * Method that find an unread DAPMessage by Subject in the data base.
     *
     * @return List of DAPMessage found.
     * @throws CantReadRecordDataBaseException
     */
    public List<DAPMessage> findUnreadMessages() throws CantReadRecordDataBaseException {

        List<DAPMessage> listDAPMessage = new ArrayList<DAPMessage>();
        DAPMessage dapMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable dapMessagesTable = getDAPMessagesTable();
            dapMessagesTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, "UNREAD", DatabaseFilterType.EQUAL);
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
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return listDAPMessage;
    }

    /*
    * Confirm the reception of the DAPMessage updating its state to READ
    *
    * */

    public void confirmDAPMessageReception(DAPMessage dapMessage) throws CantUpdateRecordDataBaseException {
        update(dapMessage,"READ");
    }

    /**
     * Method that create a new entity of DAPMessage and FermatMessage that will be sent.
     *
     * @param entity DAPMessage to be send.
     *
     */
    public void sendMessage(DAPMessage entity) throws CantInsertRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            //We persist DAPMessage entity
            create(entity);

            /*
                 * Created the FermatMessage
                 */
            FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(entity.getActorSender().toString(),//Sender
                    entity.getActorReceiver().toString(), //Receiver
                    entity.toJson().toString(), //Message Content
                    FermatMessageContentType.TEXT);//Type
                /*
                 * Configure the correct status
                 */
            ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

            //We persist FermatMessage entity
            outgoingMessageDao.create(fermatMessage, entity.getIdMessage().toString());

        } catch (FMPException e) {
            e.printStackTrace();
        }
    }


    /*public void saveDAPMessage(DAPMessage dapMessage) throws CantSaveDAPMessageException {

        if (dapMessage == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        String dapMessageJson = DAPMessageGson.getGson().toJson(dapMessage);
        String context = "Message Type : "+ dapMessage.getMessageContent().messageType() +" Message Subject : "+ dapMessage.getSubject();
        try {
            DatabaseTable databaseTable = this.database.getTable(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TABLE_NAME);
            DatabaseTableRecord dapMessageRecord = databaseTable.getEmptyRecord();
            UUID dapMessageRecordID = UUID.randomUUID();

            dapMessageRecord.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, dapMessageRecordID);
            dapMessageRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME, dapMessageJson);

            databaseTable.insertRecord(dapMessageRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveDAPMessageException(exception, context, "Cannot insert a record in DAP MESSAGE Table");
        } catch (Exception exception) {
            throw new CantSaveDAPMessageException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }*/



    //PRIVATE METHODS

    /*private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Reception Transaction Database", "Error in database plugin.");
        }
    }*/

    /**
     * Construct a DatabaseTableRecord whit the values of the a DAPMessage pass
     * by parameter
     *
     * @param dapMessage the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(DAPMessage dapMessage) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDAPMessagesTable().getEmptyRecord();
        setValuesToRecord(entityRecord, dapMessage);
        return entityRecord;
    }

    /**
     * Create a instance of DAPMessage from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return DAPMessage setters the values from table
     */
    private DAPMessage constructFrom(DatabaseTableRecord record) {

        DAPMessage dapMessage = null;
        dapMessage = DAPMessageGson.getGson().fromJson(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME), DAPMessage.class);

        return dapMessage;
    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, DAPMessage dapMessage) {

        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, dapMessage.getIdMessage().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME, dapMessage.getMessageContent().messageType().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME, dapMessage.getSubject().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, "UNREAD");
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME, DAPMessageGson.getGson().toJson(dapMessage));

    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, DAPMessage dapMessage, String state) {

        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, dapMessage.getIdMessage().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME, dapMessage.getMessageContent().messageType().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME, dapMessage.getSubject().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, state);
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME, DAPMessageGson.getGson().toJson(dapMessage));

    }

    private DAPMessage constructDAPMessageByDatabaseRecord(DatabaseTableRecord record) throws InvalidParameterException, CantLoadDAPMessageException {
        DAPMessage toReturn = DAPMessageGson.getGson().fromJson(record.toString(), DAPMessage.class);
        return toReturn;
    }

    private List<DAPMessage> constructDAPMessageListFromRecord(List<DatabaseTableRecord> records) throws CantLoadDAPMessageException, InvalidParameterException {
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
