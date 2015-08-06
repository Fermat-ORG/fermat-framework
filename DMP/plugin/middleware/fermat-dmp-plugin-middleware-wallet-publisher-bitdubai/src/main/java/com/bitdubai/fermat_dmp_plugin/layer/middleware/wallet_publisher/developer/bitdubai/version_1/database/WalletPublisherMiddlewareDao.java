/*
 * @#WalletPublisherMiddlewareDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ComponentPublishedMiddlewareInformation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewareDao {


    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public WalletPublisherMiddlewareDao(Database dataBase) {
        super();
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
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_TABLE_NAME);
    }

    /**
     * Method that find an ComponentPublishedMiddlewareInformation by id in the data base.
     *
     *  @param id Long id.
     *  @return ComponentPublishedMiddlewareInformation found.
     *  @throws CantReadRecordDataBaseException
     */
    public ComponentPublishedMiddlewareInformation findById (String id) throws CantReadRecordDataBaseException {

        if (id == null){
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable =  getDatabaseTable();
            incomingMessageTable.setStringFilter(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_FIRST_KEY_COLUMN, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ComponentPublishedMiddlewareInformation objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 3.1 - Create and configure a  ComponentPublishedMiddlewareInformation
                 */
                walletPublishedMiddlewareInformation = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return walletPublishedMiddlewareInformation;
    };

    /**
     * Method that list the all entities on the data base.
     *
     *  @return All ComponentPublishedMiddlewareInformation.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ComponentPublishedMiddlewareInformation> findAll () throws CantReadRecordDataBaseException {


        List<ComponentPublishedMiddlewareInformation> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ComponentPublishedMiddlewareInformation objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ComponentPublishedMiddlewareInformation objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ComponentPublishedMiddlewareInformation
                 */
                ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };


    /** Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     *  @see WalletPublisherMiddlewareDatabaseConstants
     *  @return All ComponentPublishedMiddlewareInformation.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ComponentPublishedMiddlewareInformation> findAll (String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()){

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<ComponentPublishedMiddlewareInformation> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();
            walletPublishedMiddlewareInformationTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ComponentPublishedMiddlewareInformation objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ComponentPublishedMiddlewareInformation objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ComponentPublishedMiddlewareInformation
                 */
                ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     * @param filters
     * @return List<ComponentPublishedMiddlewareInformation>
     * @throws CantReadRecordDataBaseException
     */
    public List<ComponentPublishedMiddlewareInformation> findAll (Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()){

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<ComponentPublishedMiddlewareInformation> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();

            for (String key: filters.keySet()){

                DatabaseTableFilter newFilter = walletPublishedMiddlewareInformationTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            walletPublishedMiddlewareInformationTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 4 - Create a list of ComponentPublishedMiddlewareInformation objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ComponentPublishedMiddlewareInformation objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 5.1 - Create and configure a  ComponentPublishedMiddlewareInformation
                 */
                ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };

    /**
     * Method that create a new entity in the data base.
     *
     *  @param entity ComponentPublishedMiddlewareInformation to create.
     *  @throws CantInsertRecordDataBaseException
     */
    public void create (ComponentPublishedMiddlewareInformation entity) throws CantInsertRecordDataBaseException {

        if (entity == null){
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
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }

    }

    /**
     * Method that update an entity in the data base.
     *
     *  @param entity ComponentPublishedMiddlewareInformation to update.
     *  @throws CantUpdateRecordDataBaseException
     */
    public void update(ComponentPublishedMiddlewareInformation entity) throws CantUpdateRecordDataBaseException {

        if (entity == null){
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
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }

    }

    /**
     * Method that delete a entity in the data base.
     *
     *  @param id Long id.
     *  @throws CantDeleteRecordDataBaseException
     */
    public void delete (Long id) throws CantDeleteRecordDataBaseException {

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

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     *
     * @param record with values from the table
     * @return ComponentPublishedMiddlewareInformation setters the values from table
     */
    private ComponentPublishedMiddlewareInformation constructFrom(DatabaseTableRecord record){

        ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation = new ComponentPublishedMiddlewareInformation();

        try {

            walletPublishedMiddlewareInformation.setId(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME));
            walletPublishedMiddlewareInformation.setSender(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME)));
            walletPublishedMiddlewareInformation.setReceiver(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME)));;
            walletPublishedMiddlewareInformation.setTextContent(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME));
            walletPublishedMiddlewareInformation.setMessageType(MessagesTypes.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME)));
            walletPublishedMiddlewareInformation.setShippingTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            walletPublishedMiddlewareInformation.setDeliveryTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME)));;
            walletPublishedMiddlewareInformation.setStatus(MessagesStatus.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {

            //this should not happen, but if it happens return null
            return null;
        }

        return walletPublishedMiddlewareInformation;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a intraUserNetworkServiceMessage pass
     * by parameter
     *
     * @param walletPublishedMiddlewareInformation the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ComponentPublishedMiddlewareInformation walletPublishedMiddlewareInformation){

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setLongValue  (WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_ID_COLUMN_NAME,                       walletPublishedMiddlewareInformation.getId());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_WFP_ID_COLUMN_NAME,                   walletPublishedMiddlewareInformation.getSender().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_WFP_NAME_COLUMN_NAME,                 walletPublishedMiddlewareInformation.getReceiver().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_WALLET_ID_COLUMN_NAME,                walletPublishedMiddlewareInformation.getTextContent());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_CATALOG_ID_COLUMN_NAME,               walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_STORE_ID_COLUMN_NAME,                 walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_SCREEN_SIZE_COLUMN_NAME,              walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_INITIAL_WALLET_VERSION_COLUMN_NAME,   walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_FINAL_WALLET_VERSION_COLUMN_NAME,     walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_INITIAL_PLATFORM_VERSION_COLUMN_NAME, walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_FINAL_PLATFORM_VERSION_COLUMN_NAME,   walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_COMPONENT_TYPE_COLUMN_NAME,           walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_VERSION_COLUMN_NAME,                  walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setLongValue  (WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_VERSION_TIMESTAMP_COLUMN_NAME,        walletPublishedMiddlewareInformation.getDeliveryTimestamp().getTime());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_STATUS_COLUMN_NAME,                   walletPublishedMiddlewareInformation.getMessageType().getCode());
        entityRecord.setLongValue  (WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_STATUS_TIMESTAMP_COLUMN_NAME,         walletPublishedMiddlewareInformation.getDeliveryTimestamp().getTime());
        entityRecord.setLongValue  (WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_PUBLICATION_TIMESTAMP_COLUMN_NAME,    walletPublishedMiddlewareInformation.getDeliveryTimestamp().getTime());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_PUBLISHER_ID_COLUMN_NAME,             walletPublishedMiddlewareInformation.getStatus().getCode());

        /*
         * return the new table record
         */
        return entityRecord;

    }


}
