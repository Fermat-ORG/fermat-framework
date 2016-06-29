package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures.SystemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SystemDataDAO {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public SystemDataDAO(Database dataBase,
                         final PluginFileSystem pluginFileSystem,
                         final UUID pluginId) {
        super();
        this.dataBase = dataBase;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
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
        return getDataBase().getTable(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_TABLE_NAME);
    }

    /**
     * Method that find an SystemData by id in the data base.
     *
     * @param id Long id.
     * @return SystemData found
     */
    public SystemData findById(String id) throws DatabaseTransactionFailedException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        SystemData systemData = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable systemDataTable = getDatabaseTable();
            systemDataTable.addStringFilter(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            systemDataTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = systemDataTable.getRecords();


            /*
             * 3 - Convert into SystemData objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  SystemData
                 */
                systemData = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Not found";
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return systemData;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All SystemData.
     * @throws DatabaseTransactionFailedException
     */
    public List<SystemData> findAll() throws DatabaseTransactionFailedException {


        List<SystemData> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable systemDataTable = getDatabaseTable();
            systemDataTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = systemDataTable.getRecords();

            /*
             * 3 - Create a list of SystemData objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into SystemData objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  SystemData
                 */
                SystemData systemData = constructFrom(record);

                /*e.printStackTrace();
                 * 4.2 - Add to the list
                 */
                list.add(systemData);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            //DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>SystemMonitorNetworkServiceDeveloperDatabaseConstants</code>
     *
     * @return All SystemData.
     * @throws DatabaseTransactionFailedException
     * @see SystemMonitorNetworkServiceDatabaseConstants
     */
    public List<SystemData> findAll(String columnName, String columnValue) throws DatabaseTransactionFailedException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<SystemData> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable templateTable = getDatabaseTable();
            templateTable.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            templateTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 3 - Create a list of SystemData objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into SystemData objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  SystemData
                 */
                SystemData systemData = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(systemData);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            //DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>SystemMonitorNetworkServiceDeveloperDatabaseConstants</code>
     *
     * @param filters
     * @return List<SystemData>
     * @throws DatabaseTransactionFailedException
     */
    public List<SystemData> findAll(Map<String, Object> filters) throws DatabaseTransactionFailedException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<SystemData> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }


            /*
             * 2 - load the data base to memory with filters
             */
            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 4 - Create a list of SystemData objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into SystemData objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  SystemData
                 */
                SystemData systemData = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(systemData);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            // DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity SystemData to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(SystemData entity) throws CantInsertRecordDataBaseException, DatabaseTransactionFailedException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            if (findById(entity.getTransactionId().toString()) != null) {
                return;
            }
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

        } catch (DatabaseTransactionFailedException e) {
            e.printStackTrace();
        } catch (com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity SystemData to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(SystemData entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable metadataTable = getDatabaseTable();
            metadataTable.addStringFilter(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME, entity.getTransactionId().toString(), DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();

            if (metadataTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = metadataTable.getRecords().get(0);
            setValuesToRecord(record, entity);
            metadataTable.updateRecord(record);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | RecordsNotFoundException databaseTransactionFailedException) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

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
    public void delete(String id) throws CantDeleteRecordDataBaseException, DatabaseTransactionFailedException {

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

        } catch (com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException e) {
            e.printStackTrace();
        }

    }


    /**
     * Create a instance of SystemData from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return SystemData setters the values from table
     */
    private SystemData constructFrom(DatabaseTableRecord record) throws InvalidParameterException {
        SystemData systemData = new SystemData(
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_FIRST_KEY_COLUMN),
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NODE_TYPE_COLUMN_NAME),
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_HARDWARE_COLUMN_NAME),
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_OS_COLUMN_NAME)
        );

        return systemData;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a SystemData pass
     * by parameter
     *
     * @param systemData the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(SystemData systemData) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();
        setValuesToRecord(entityRecord, systemData);
        return entityRecord;
    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, SystemData systemData) {

        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_FIRST_KEY_COLUMN, systemData.systemID);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NODE_TYPE_COLUMN_NAME, systemData.nodeType);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_HARDWARE_COLUMN_NAME, systemData.hardware);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_OS_COLUMN_NAME, systemData.os);
    }

}