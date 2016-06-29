package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures.ComponentProfileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mati on 2016.03.31..
 */
public class ComponentDAO {

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
    public ComponentDAO(Database dataBase,
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
        return getDataBase().getTable(SystemMonitorNetworkServiceDatabaseConstants.PLATFORM_COMPONENTS_TABLE_NAME);
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All Connection.
     * @throws DatabaseTransactionFailedException
     */
    public List<ComponentProfileInfo> findAll() throws DatabaseTransactionFailedException {


        List<ComponentProfileInfo> list = null;

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
                ComponentProfileInfo connection = constructFrom(record);

                /*
                 * 4.2 - Add to the list
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
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that find an Connection by id in the data base.
     *
     * @param id Long id.
     * @return Connection found
     */
    public ComponentProfileInfo findById(String id) throws DatabaseTransactionFailedException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ComponentProfileInfo connection = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable connectionTable = getDatabaseTable();
            connectionTable.addStringFilter(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
                connection = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Not found " + cantLoadTableToMemory.getMessage();
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Method that delete all entity in the data base.
     *
     * @throws CantDeleteRecordDataBaseException
     */

    public void deleteComponentEvent() throws CantDeleteRecordDataBaseException {

        try {

            DatabaseTable databaseTable = getDatabaseTable();
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();


            for (DatabaseTableRecord record : records) {


                databaseTable.deleteRecord(record);
            }


        } catch (CantDeleteRecordException e) {

            throw new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.", "");
        }

    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity Connection to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(ComponentProfileInfo entity) throws CantInsertRecordDataBaseException, DatabaseTransactionFailedException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        if (findById(entity.getId().toString()) != null) {
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
    public void update(ComponentProfileInfo entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable metadataTable = getDatabaseTable();
            metadataTable.addStringFilter(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_ID_COLUMN_NAME, entity.getId().toString(), DatabaseFilterType.EQUAL);
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
     * Construct a DatabaseTableRecord whit the values of the a Connection pass
     * by parameter
     *
     * @param connection the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ComponentProfileInfo connection) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();
        setValuesToRecord(entityRecord, connection);
        return entityRecord;
    }

    /**
     * Create a instance of Connection from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return Connection setters the values from table
     */
    private ComponentProfileInfo constructFrom(DatabaseTableRecord record) throws InvalidParameterException {

        ComponentProfileInfo connection = new ComponentProfileInfo(
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_ID_COLUMN_NAME),
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_NAME_COLUMN_NAME),
                record.getStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_TYPE_COLUMN_NAME)
        );

        return connection;
    }

    private void setValuesToRecord(DatabaseTableRecord entityRecord, ComponentProfileInfo connection) {
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_ID_COLUMN_NAME, connection.getId());
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_NAME_COLUMN_NAME, connection.getName());
        entityRecord.setStringValue(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_TYPE_COLUMN_NAME, connection.getType());
    }

}
