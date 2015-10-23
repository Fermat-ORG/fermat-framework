/*
 * @#PlatformComponentProfileRegisteredDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities.PlatformComponentProfileRegistered;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.PlatformComponentProfileRegisteredDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PlatformComponentProfileRegisteredDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public PlatformComponentProfileRegisteredDao(Database dataBase) {
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
     * @return PlatformComponentProfileRegistered found.
     * @throws CantReadRecordDataBaseException
     */
    public PlatformComponentProfileRegistered findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        PlatformComponentProfileRegistered platformComponentProfileRegistered = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable table = getDatabaseTable();
            table.setStringFilter(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_TABLE_NAME, id, DatabaseFilterType.EQUAL);
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
                platformComponentProfileRegistered = constructFrom(record);
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

        return platformComponentProfileRegistered;
    }

    ;

    /**
     * Method that list the all entities on the data base.
     *
     * @return All PlatformComponentProfileRegistered.
     * @throws CantReadRecordDataBaseException
     */
    public List<PlatformComponentProfileRegistered> findAll() throws CantReadRecordDataBaseException {

        List<PlatformComponentProfileRegistered> list = null;
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
                PlatformComponentProfileRegistered platformComponentProfileRegistered = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(platformComponentProfileRegistered);
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
     * @return All PlatformComponentProfileRegistered.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationsCloudServerP2PDatabaseConstants
     */
    public List<PlatformComponentProfileRegistered> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<PlatformComponentProfileRegistered> list = null;

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
                PlatformComponentProfileRegistered platformComponentProfileRegistered = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(platformComponentProfileRegistered);

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
     * @return All PlatformComponentProfileRegistered.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationsCloudServerP2PDatabaseConstants
     */
    public List<PlatformComponentProfileRegistered> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }


        List<PlatformComponentProfileRegistered> list = null;
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
                PlatformComponentProfileRegistered platformComponentProfileRegistered = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(platformComponentProfileRegistered);

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
     * @param entity PlatformComponentProfileRegistered to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(PlatformComponentProfileRegistered entity) throws CantInsertRecordDataBaseException {

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
     * @param entity PlatformComponentProfileRegistered to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(PlatformComponentProfileRegistered entity) throws CantUpdateRecordDataBaseException {

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
     * @return PlatformComponentProfileRegistered setters the values from table
     */
    private PlatformComponentProfileRegistered constructFrom(DatabaseTableRecord record) {

        PlatformComponentProfileRegistered platformComponentProfileRegistered = new PlatformComponentProfileRegistered();

        try {

            platformComponentProfileRegistered.setIdentityPublicKey(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            platformComponentProfileRegistered.setName(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NAME_COLUMN_NAME));
            platformComponentProfileRegistered.setAlias(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_ALIAS_COLUMN_NAME));
            platformComponentProfileRegistered.setPlatformComponentType(PlatformComponentType.getByCode(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_COMPONENT_TYPE_COLUMN_NAME)));
            platformComponentProfileRegistered.setNetworkServiceType(NetworkServiceType.getByCode(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME)));
            platformComponentProfileRegistered.setLastLatitude(Double.valueOf(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LATITUDE_COLUMN_NAME)));
            platformComponentProfileRegistered.setLastLongitude(Double.valueOf(record.getIntegerValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LONGITUDE_COLUMN_NAME)));
            platformComponentProfileRegistered.setExtraData(record.getStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_EXTRA_DATA_COLUMN_NAME));
            platformComponentProfileRegistered.setLastConnectionTimestamp(new Timestamp(record.getLongValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME)));

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return platformComponentProfileRegistered;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a FermatMessage pass
     * by parameter
     *
     * @param platformComponentProfileRegistered the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(PlatformComponentProfileRegistered platformComponentProfileRegistered) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

         /*
         * Set the entity values
         */
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, platformComponentProfileRegistered.getIdentityPublicKey());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NAME_COLUMN_NAME, platformComponentProfileRegistered.getName());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_ALIAS_COLUMN_NAME, platformComponentProfileRegistered.getAlias());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_COMPONENT_TYPE_COLUMN_NAME, platformComponentProfileRegistered.getPlatformComponentType().getCode());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME, platformComponentProfileRegistered.getNetworkServiceType().getCode());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LATITUDE_COLUMN_NAME, platformComponentProfileRegistered.getLastLatitude().toString());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LONGITUDE_COLUMN_NAME, platformComponentProfileRegistered.getLastLongitude().toString());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_EXTRA_DATA_COLUMN_NAME, platformComponentProfileRegistered.getExtraData());
        entityRecord.setStringValue(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, platformComponentProfileRegistered.getLastConnectionTimestamp().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
