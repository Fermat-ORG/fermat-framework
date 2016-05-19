package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 17/10/15.
 */
public class IncomingMessageDao {
    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public IncomingMessageDao(Database dataBase) {
        super();
        this.dataBase = dataBase;
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
        return getDataBase().getTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);
    }

    /**
     * Method that find an FermatMessage by id in the data base.
     *
     * @param id Long id.
     * @return FermatMessage found.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException
     */
    public FermatMessage findById(String id) throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        FermatMessage incomingTemplateNetworkServiceMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.addStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  FermatMessage
                 */
                incomingTemplateNetworkServiceMessage = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return incomingTemplateNetworkServiceMessage;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All FermatMessage.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException
     */
    public List<FermatMessage> findAll() throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException {

        List<FermatMessage> list = null;
        try {
            /*
             * 1 - load the data base to memory
             */
            DatabaseTable networkIntraIssuerTable = getDatabaseTable();
            networkIntraIssuerTable.loadToMemory();
            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraIssuerTable.getRecords();
            /*
             * 3 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();
            /*
             * 4 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {
                /*
                 * 4.1 - Create and configure a  FermatMessage
                 */
                FermatMessage incomingTemplateNetworkServiceMessage = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingTemplateNetworkServiceMessage);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public List<FermatMessage> findAll(String columnName, String columnValue) throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<FermatMessage> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable networkIntraIssuerTable = getDatabaseTable();
            networkIntraIssuerTable.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            networkIntraIssuerTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraIssuerTable.getRecords();

            /*
             * 3 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  FermatMessage
                 */
                FermatMessage incomingTemplateNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException cantReadRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
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
     * @param entity FermatMessage to create.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInsertRecordDataBaseException
     */
    public void create(FermatMessage entity) throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInsertRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToInsert(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInsertRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }


    /**
     * Method that update an entity in the data base.
     *
     * @param entity FermatMessage to update.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantUpdateRecordDataBaseException
     */
    public void update(FermatMessage entity) throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantUpdateRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id Long id.
     * @throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantDeleteRecordDataBaseException
     */
    public void delete(Long id) throws org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantDeleteRecordDataBaseException {

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

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantDeleteRecordDataBaseException(org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.exceptions.CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;
        }

    }

    /**
     * @param record with values from the table
     * @return FermatMessage setters the values from table
     */
    private FermatMessage constructFrom(DatabaseTableRecord record) {

        FermatMessageCommunication incomingTemplateNetworkServiceMessage = new FermatMessageCommunication();

        try {

            incomingTemplateNetworkServiceMessage.setId(UUID.fromString(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME)));
            incomingTemplateNetworkServiceMessage.setSender(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setReceiver(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME));

            incomingTemplateNetworkServiceMessage.setContent(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setFermatMessageContentType((FermatMessageContentType.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME))));
            incomingTemplateNetworkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            incomingTemplateNetworkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME)));

            incomingTemplateNetworkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return incomingTemplateNetworkServiceMessage;
    }


    /**
     * Construct a DatabaseTableRecord whit the values of the a FermatMessage pass
     * by parameter
     *
     * @param incomingTemplateNetworkServiceMessage the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(FermatMessage incomingTemplateNetworkServiceMessage) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getId().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getSender());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getReceiver());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getContent());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getFermatMessageContentType().getCode());

        if (incomingTemplateNetworkServiceMessage.getShippingTimestamp() != null){
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getShippingTimestamp().getTime());
        }else {
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        if (incomingTemplateNetworkServiceMessage.getDeliveryTimestamp() != null){
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getDeliveryTimestamp().getTime());
        }else {
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getFermatMessagesStatus().getCode());

        /*
         * return the new table record
         */
        return entityRecord;

    }
}