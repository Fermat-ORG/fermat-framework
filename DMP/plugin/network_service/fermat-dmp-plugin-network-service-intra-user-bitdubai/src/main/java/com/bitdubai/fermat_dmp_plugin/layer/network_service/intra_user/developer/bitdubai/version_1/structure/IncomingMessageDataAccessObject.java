/*
 * @#IncomingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesTypes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IncomingMessageDataAccessObject</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IncomingMessageDataAccessObject {

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
    public IncomingMessageDataAccessObject(Database dataBase, ErrorManager errorManager) {
        super();
        this.errorManager = errorManager;
        this.dataBase = dataBase;
    }

    /**
     * Return the Database
     * @return Database
     */
    Database getDataBase() {
        return dataBase;
    }

    /**
     * Return the DatabaseTable
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);
    }

    /**
     * Method that find an IncomingIntraUserNetworkServiceMessage by id in the data base.
     *
     *  @param id Long id.
     *  @return IncomingIntraUserNetworkServiceMessage found.
     */
    public IncomingIntraUserNetworkServiceMessage findById (String id){

        if (id == null){
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable =  getDatabaseTable();
            incomingMessageTable.setStringFilter(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into IncomingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 3.1 - Create and configure a  IncomingIntraUserNetworkServiceMessage
                 */
                incomingIntraUserNetworkServiceMessage = constructFrom(record);
            }

        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            // Register the failure.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            return null;
        }

        return incomingIntraUserNetworkServiceMessage;
    };

    /**
     * Method that list the all entities on the data base.
     *
     *  @return All IncomingIntraUserNetworkServiceMessage.
     */
    public List<IncomingIntraUserNetworkServiceMessage> findAll () {


        List<IncomingIntraUserNetworkServiceMessage> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable networkIntraUserTable =  getDatabaseTable();
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 3 - Create a list of IncomingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into IncomingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  IncomingIntraUserNetworkServiceMessage
                 */
                IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            // Register the failure.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            return null;
        }

        /*
         * return the list
         */
        return list;
    };


    /** Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>IntraUserNetworkServiceDatabaseConstants</code>
     *
     *  @see IntraUserNetworkServiceDatabaseConstants
     *  @return All IncomingIntraUserNetworkServiceMessage.
     */
    public List<IncomingIntraUserNetworkServiceMessage> findAll (String columnName, String columnValue) {

        if (columnName == null ||
                columnName.isEmpty() ||
                    columnValue == null ||
                        columnValue.isEmpty()){

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<IncomingIntraUserNetworkServiceMessage> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable networkIntraUserTable =  getDatabaseTable();
            networkIntraUserTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 3 - Create a list of IncomingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into IncomingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  IncomingIntraUserNetworkServiceMessage
                 */
                IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            // Register the failure.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            return null;
        }

        /*
         * return the list
         */
        return list;
    };


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>IntraUserNetworkServiceDatabaseConstants</code>
     *
     *  @see IntraUserNetworkServiceDatabaseConstants
     *  @return All IncomingIntraUserNetworkServiceMessage.
     */
    public List<IncomingIntraUserNetworkServiceMessage> findAll (Map<String, Object> filters) {

        if (filters == null ||
                filters.isEmpty()){

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }


        List<IncomingIntraUserNetworkServiceMessage> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            for (String key: filters.keySet()){

                DatabaseTableFilter newFilter = null; // new AndroidDataBaseFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }


            /*
             * 2 - load the data base to memory with filters
             */
            DatabaseTable networkIntraUserTable =  getDatabaseTable();
            networkIntraUserTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR); //Verificar si es la forma correcta de usar
            networkIntraUserTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 4 - Create a list of IncomingIntraUserNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into IncomingIntraUserNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 5.1 - Create and configure a  IncomingIntraUserNetworkServiceMessage
                 */
                IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(incomingIntraUserNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            // Register the failure.
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            return null;
        }

        /*
         * return the list
         */
        return list;
    };

    /**
     * Method that create a new entity in the data base.
     *
     *  @param entity IncomingIntraUserNetworkServiceMessage to create.
     */
    public boolean create (IncomingIntraUserNetworkServiceMessage entity){

        if (entity == null){
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
            errorManager.reportUnexpectedPluginException (Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            return  Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    /**
     * Method that update an entity in the data base.
     *
     *  @param entity IncomingIntraUserNetworkServiceMessage to update.
     */
    public boolean update(IncomingIntraUserNetworkServiceMessage entity){

        if (entity == null){
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
            errorManager.reportUnexpectedPluginException (Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            return  Boolean.FALSE;
        }

        return Boolean.FALSE;
    }

    /**
     * Method that delete a entity in the data base.
     *
     *  @param id Long id.
     * */
    public boolean delete (Long id){

        if (id == null){
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
            errorManager.reportUnexpectedPluginException (Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            return  Boolean.FALSE;
        }

        return Boolean.FALSE;
    }


    /**
     *
     * @param record with values from the table
     * @return IncomingIntraUserNetworkServiceMessage setters the values from table
     */
    private IncomingIntraUserNetworkServiceMessage constructFrom(DatabaseTableRecord record){

        IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = new IncomingIntraUserNetworkServiceMessage();

        try {

            incomingIntraUserNetworkServiceMessage.setId(record.getLongValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME));
            incomingIntraUserNetworkServiceMessage.setSender(UUID.fromString(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME)));
            incomingIntraUserNetworkServiceMessage.setReceiver(UUID.fromString(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME)));;
            incomingIntraUserNetworkServiceMessage.setTextContent(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME));
            incomingIntraUserNetworkServiceMessage.setMessageType(MessagesTypes.getByCode(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME)));
            incomingIntraUserNetworkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            incomingIntraUserNetworkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME)));;
            incomingIntraUserNetworkServiceMessage.setStatus(MessagesStatus.getByCode(record.getStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {

            //this should not happen, but if it happens return null
            return null;
        }

        return incomingIntraUserNetworkServiceMessage;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a intraUserNetworkServiceMessage pass
     * by parameter
     *
     * @param incomingIntraUserNetworkServiceMessage the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage){

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setLongValue  (IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME,                 incomingIntraUserNetworkServiceMessage.getId());
        entityRecord.setStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME,          incomingIntraUserNetworkServiceMessage.getSender().toString());
        entityRecord.setStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME,        incomingIntraUserNetworkServiceMessage.getReceiver().toString());
        entityRecord.setStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME,       incomingIntraUserNetworkServiceMessage.getTextContent());
        entityRecord.setStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME,               incomingIntraUserNetworkServiceMessage.getMessageType().getCode());
        entityRecord.setLongValue  (IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingIntraUserNetworkServiceMessage.getShippingTimestamp().getTime());
        entityRecord.setLongValue  (IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, incomingIntraUserNetworkServiceMessage.getDeliveryTimestamp().getTime());
        entityRecord.setStringValue(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME,             incomingIntraUserNetworkServiceMessage.getStatus().getCode());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
