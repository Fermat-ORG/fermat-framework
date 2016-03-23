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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public ConnectionDao(Database dataBase) {
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
        return getDataBase().getTable(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_TABLE_NAME);
    }

    /**
     * Method that find an Connection by id in the data base.
     *
     * @param id Long id.
     * @return Connection found
     */
    public Connection findById(String id) throws DatabaseTransactionFailedException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        Connection connection;
        connection = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable connectionTable = getDatabaseTable();
            connectionTable.addStringFilter(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            connectionTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = connectionTable.getRecords();


            /*
             * 3 - Convert into Connection objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  Connection
                 */
                com.squareup.okhttp.Connection = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Not found";
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

        return connection;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All Connection.
     * @throws DatabaseTransactionFailedException
     */
    public List<Connection> findAll() throws DatabaseTransactionFailedException {


        List<Connection> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable connectionTable = getDatabaseTable();
            connectionTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = connectionTable.getRecords();

            /*
             * 3 - Create a list of Connection objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into Connection objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  Connection
                 */
                Connection connection = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(connection);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
           // DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>SystemMonitorNetworkServiceDatabaseConstants</code>
     *
     * @return All Connection.
     * @throws DatabaseTransactionFailedException
     * @see SystemMonitorNetworkServiceDatabaseConstants
     */
    public List<Connection> findAll(String columnName, String columnValue) throws DatabaseTransactionFailedException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<Connection> list = null;

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
             * 3 - Create a list of Connection objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into Connection objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  Connection
                 */
                Connection connection = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(connection);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            //DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>SystemMonitorNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<Connection>
     * @throws DatabaseTransactionFailedException
     */
    public List<Connection> findAll(Map<String, Object> filters) throws DatabaseTransactionFailedException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<Connection> list = null;
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
             * 4 - Create a list of Connection objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into Connection objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  Connection
                 */
                Connection connection = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(connection);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            //DatabaseTransactionFailedException DatabaseTransactionFailedException = new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity Connection to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(Connection entity) throws CantInsertRecordDataBaseException, DatabaseTransactionFailedException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

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

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity Connection to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(Connection entity) throws CantUpdateRecordDataBaseException {

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
    public void delete(String id) throws CantDeleteRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * Create a instance of Connection from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return Connection setters the values from table
     */
    private Connection constructFrom(DatabaseTableRecord record) {

        Connection connection = new Connection();

        try {

            connection.connID = record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_FIRST_KEY_COLUMN);
            connection.peerID = record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEERID_COLUMN_NAME);
            connection.ipv4 = record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEER_IPV4_COLUMN_NAME);
            connection.ipv6 = record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEER_IPV6_COLUMN_NAME);
            connection.ns_name = record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NETWORK_SERVICE_NAME_COLUMN_NAME);

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return Connection;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a Connection pass
     * by parameter
     *
     * @param connection the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(Connection connection) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();
        setValuesToRecord(entityRecord, connection);
        return entityRecord;
    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, Connection connection) {
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_FIRST_KEY_COLUMN, connection.connID);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEERID_COLUMN_NAME, connection.peerID);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEER_IPV4_COLUMN_NAME, connection.ipv4);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_PEER_IPV6_COLUMN_NAME, connection.ipv6);
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NETWORK_SERVICE_NAME_COLUMN_NAME, connection.ns_name);
    }
}