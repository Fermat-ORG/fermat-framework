/*
 * @#OutgoingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesTypes;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service.OutgoingMessageDataAccessObject</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 25/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingMessageDataAccessObject {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the Error manager.
     */
    private ErrorManager errorManager = null;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     * @param errorManager
     */
    public OutgoingMessageDataAccessObject(Database dataBase, ErrorManager errorManager) {
        super();
        this.errorManager = errorManager;
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
        return getDataBase().getTable(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);
    }

    /**
     * Method that find an OutgoingIntraUserNetworkServiceMessage by id in the data base.
     *
     * @param id Long id.
     * @return OutgoingIntraUserNetworkServiceMessage found.
     * @throws CantReadRecordDataBaseException
     */
    public OutgoingIntraUserNetworkServiceMessage findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        OutgoingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable =  getDatabaseTable();
            incomingMessageTable.setStringFilter(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into OutgoingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  OutgoingIntraUserNetworkServiceMessage
                 */
                incomingIntraUserNetworkServiceMessage = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return incomingIntraUserNetworkServiceMessage;
    }

    ;

    /**
     * Method that list the all entities on the data base.
     *
     * @return All OutgoingIntraUserNetworkServiceMessage.
     * @throws CantReadRecordDataBaseException
     */
    public List<OutgoingIntraUserNetworkServiceMessage> findAll() throws CantReadRecordDataBaseException {
        List<OutgoingIntraUserNetworkServiceMessage> list = null;

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
             * 3 - Create a list of OutgoingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();
            /*
             * 4 - Convert into OutgoingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {
                /*
                 * 4.1 - Create and configure a  OutgoingIntraUserNetworkServiceMessage
                 */
               OutgoingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>IntraUserNetworkServiceDatabaseConstants</code>
     *
<<<<<<< HEAD:DMP/plugin/network_service/fermat-dmp-plugin-network-service-intra-user-bitdubai/src/main/java/com/bitdubai/fermat_dmp_plugin/layer/network_service/intra_user/developer/bitdubai/version_1/structure/OutgoingMessageDataAccessObject.java
     * @return All OutgoingIntraUserNetworkServiceMessage.
     * @throws CantReadRecordDataBaseException
     * @see IntraUserNetworkServiceDatabaseConstants
=======
     *  @see com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants
     *  @return All OutgoingIntraUserNetworkServiceMessage.
     *  @throws CantReadRecordDataBaseException
>>>>>>> bc22c4b18429592ec5dd45c782b82f9205aeb563:DMP/plugin/network_service/fermat-dmp-plugin-network-service-intra-user-bitdubai/src/main/java/com/bitdubai/fermat_dmp_plugin/layer/network_service/intra_user/developer/bitdubai/version_1/network_service/OutgoingMessageDataAccessObject.java
     */
    public List<OutgoingIntraUserNetworkServiceMessage> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<OutgoingIntraUserNetworkServiceMessage> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 3 - Create a list of OutgoingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into OutgoingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  OutgoingIntraUserNetworkServiceMessage
                 */
               OutgoingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>IntraUserNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<OutgoingIntraUserNetworkServiceMessage>
     * @throws CantReadRecordDataBaseException
     */
    public List<OutgoingIntraUserNetworkServiceMessage> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<OutgoingIntraUserNetworkServiceMessage> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = networkIntraUserTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            networkIntraUserTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            networkIntraUserTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 4 - Create a list of OutgoingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into OutgoingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  OutgoingIntraUserNetworkServiceMessage
                 */
               OutgoingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    ;

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity OutgoingIntraUserNetworkServiceMessage to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(OutgoingIntraUserNetworkServiceMessage entity) throws CantInsertRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity OutgoingIntraUserNetworkServiceMessage to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(OutgoingIntraUserNetworkServiceMessage entity) throws CantUpdateRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
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
            contextBuffer.append("Database Name: " + com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * @param record with values from the table
     * @return OutgoingIntraUserNetworkServiceMessage setters the values from table
     */
    private OutgoingIntraUserNetworkServiceMessage constructFrom(DatabaseTableRecord record) {

        OutgoingIntraUserNetworkServiceMessage outgoingIntraUserNetworkServiceMessage = new OutgoingIntraUserNetworkServiceMessage();

        try {

            outgoingIntraUserNetworkServiceMessage.setId(record.getLongValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME));
            outgoingIntraUserNetworkServiceMessage.setSender(UUID.fromString(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME)));
            outgoingIntraUserNetworkServiceMessage.setReceiver(UUID.fromString(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME)));
            ;
            outgoingIntraUserNetworkServiceMessage.setTextContent(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME));
            outgoingIntraUserNetworkServiceMessage.setMessageType(MessagesTypes.getByCode(record.getStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME)));
            outgoingIntraUserNetworkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            outgoingIntraUserNetworkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME)));;
            outgoingIntraUserNetworkServiceMessage.setStatus(MessagesStatus.getByCode(record.getStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            //this should not happen, but if it happens return null
            return null;
        }

        return outgoingIntraUserNetworkServiceMessage;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a intraUserNetworkServiceMessage pass
     * by parameter
     *
     * @param incomingIntraUserNetworkServiceMessage the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(OutgoingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setLongValue  (com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME,                 incomingIntraUserNetworkServiceMessage.getId());
        entityRecord.setStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME,          incomingIntraUserNetworkServiceMessage.getSender().toString());
        entityRecord.setStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME,        incomingIntraUserNetworkServiceMessage.getReceiver().toString());
        entityRecord.setStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME,       incomingIntraUserNetworkServiceMessage.getTextContent());
        entityRecord.setStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME,               incomingIntraUserNetworkServiceMessage.getMessageType().getCode());
        entityRecord.setLongValue  (com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingIntraUserNetworkServiceMessage.getShippingTimestamp().getTime());
        entityRecord.setLongValue  (com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, incomingIntraUserNetworkServiceMessage.getDeliveryTimestamp().getTime());
        entityRecord.setStringValue(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME,             incomingIntraUserNetworkServiceMessage.getStatus().getCode());
        /*
         * return the new table record
         */
        return entityRecord;

    }

}
