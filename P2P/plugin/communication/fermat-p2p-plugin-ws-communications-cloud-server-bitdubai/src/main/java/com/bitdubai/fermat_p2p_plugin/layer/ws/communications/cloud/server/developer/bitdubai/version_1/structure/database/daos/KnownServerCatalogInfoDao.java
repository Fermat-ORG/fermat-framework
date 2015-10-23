/*
 * @#IncomingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database.CommunicationsCloudServerP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities.KnownServerCatalogInfo;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.IncomingMessageDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class KnownServerCatalogInfoDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public KnownServerCatalogInfoDao(Database dataBase) {
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
        return getDataBase().getTable(CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);
    }

    /**
     * Method that find an FermatMessage by id in the data base.
     *
     * @param id String
     * @return KnownServerCatalogInfo found.
     * @throws CantReadRecordDataBaseException
     */
    public KnownServerCatalogInfo findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        KnownServerCatalogInfo knownServerCatalogInfo = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable table = getDatabaseTable();
            table.setStringFilter(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_TABLE_NAME, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = table.getRecords();


            /*
             * 3 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  FermatMessage
                 */
                knownServerCatalogInfo = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return knownServerCatalogInfo;
    }

    ;

    /**
     * Method that list the all entities on the data base.
     *
     * @return All KnownServerCatalogInfo.
     * @throws CantReadRecordDataBaseException
     */
    public List<KnownServerCatalogInfo> findAll() throws CantReadRecordDataBaseException {

        List<KnownServerCatalogInfo> list = null;
        try {
            /*
             * 1 - load the data base to memory
             */
            DatabaseTable table = getDatabaseTable();
            table.loadToMemory();
            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = table.getRecords();
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
                KnownServerCatalogInfo knownServerCatalogInfo = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(knownServerCatalogInfo);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

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

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CommunicationsCloudServerP2PDatabaseConstants</code>
     *
     * @return All KnownServerCatalogInfo.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationsCloudServerP2PDatabaseConstants
     */
    public List<KnownServerCatalogInfo> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<KnownServerCatalogInfo> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable table = getDatabaseTable();
            table.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = table.getRecords();

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
                KnownServerCatalogInfo knownServerCatalogInfo = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(knownServerCatalogInfo);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

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

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CommunicationsCloudServerP2PDatabaseConstants</code>
     *
     * @return All KnownServerCatalogInfo.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationsCloudServerP2PDatabaseConstants
     */
    public List<KnownServerCatalogInfo> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }


        List<KnownServerCatalogInfo> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable table = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }


            /*
             * 2 - load the data base to memory with filters
             */
            table.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            table.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = table.getRecords();

            /*
             * 4 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  FermatMessage
                 */
                KnownServerCatalogInfo knownServerCatalogInfo = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(knownServerCatalogInfo);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

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

    ;

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity KnownServerCatalogInfo to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(KnownServerCatalogInfo entity) throws CantInsertRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity KnownServerCatalogInfo to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(KnownServerCatalogInfo entity) throws CantUpdateRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

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

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationsCloudServerP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;
        }

    }


    /**
     * @param record with values from the table
     * @return KnownServerCatalogInfo setters the values from table
     */
    private KnownServerCatalogInfo constructFrom(DatabaseTableRecord record) {

        KnownServerCatalogInfo knownServerCatalogInfo = new KnownServerCatalogInfo();

        try {

            knownServerCatalogInfo.setIdentityPublicKey(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            knownServerCatalogInfo.setName(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_NAME_COLUMN_NAME));
            knownServerCatalogInfo.setIp(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IP_COLUMN_NAME));
            knownServerCatalogInfo.setDefaultPort(record.getIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_DEFAULT_PORT_COLUMN_NAME));
            knownServerCatalogInfo.setLatitude(Double.valueOf(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATITUDE_COLUMN_NAME)));
            knownServerCatalogInfo.setLongitude(Double.valueOf(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LONGITUDE_COLUMN_NAME)));
            knownServerCatalogInfo.setLateNotificationCounter(record.getIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME));
            knownServerCatalogInfo.setLateNotificationCounter(record.getIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_OFFLINE_COUNTER_COLUMN_NAME));
            knownServerCatalogInfo.setRegisteredTimestamp(new Timestamp(record.getLongValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME)));
            knownServerCatalogInfo.setLastConnectionTimestamp(new Timestamp(record.getLongValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME)));

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return knownServerCatalogInfo;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a FermatMessage pass
     * by parameter
     *
     * @param knownServerCatalogInfo the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(KnownServerCatalogInfo knownServerCatalogInfo) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

         /*
         * Set the entity values
         */
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, knownServerCatalogInfo.getIdentityPublicKey());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_NAME_COLUMN_NAME, knownServerCatalogInfo.getName());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IP_COLUMN_NAME, knownServerCatalogInfo.getIp());
        entityRecord.setIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_DEFAULT_PORT_COLUMN_NAME, knownServerCatalogInfo.getDefaultPort());
        entityRecord.setIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_WEB_SERVICE_PORT_COLUMN_NAME, knownServerCatalogInfo.getWebServicePort());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATITUDE_COLUMN_NAME, knownServerCatalogInfo.getLatitude().toString());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LONGITUDE_COLUMN_NAME, knownServerCatalogInfo.getLongitude().toString());
        entityRecord.setIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME, knownServerCatalogInfo.getLateNotificationCounter());
        entityRecord.setIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_OFFLINE_COUNTER_COLUMN_NAME, knownServerCatalogInfo.getOfflineCounter());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME, knownServerCatalogInfo.getRegisteredTimestamp().toString());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, knownServerCatalogInfo.getLastConnectionTimestamp().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
