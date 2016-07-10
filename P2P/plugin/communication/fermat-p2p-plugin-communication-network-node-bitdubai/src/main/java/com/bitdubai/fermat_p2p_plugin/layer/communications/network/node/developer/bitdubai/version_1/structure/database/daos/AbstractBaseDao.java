package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantTruncateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.AbstractBaseEntity;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.OutgoingMessageDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractBaseDao<E extends AbstractBaseEntity> {

    /**
     * Represent the database instance
     */
    private final Database dataBase;

    /**
     * Represent the tableName
     */
    private final String tableName;

    /**
     * Represent the idTableName
     */
    private final String idTableName;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     * @param tableName
     * @param idTableName
     */
    public AbstractBaseDao(final Database dataBase   ,
                           final String   tableName  ,
                           final String   idTableName) {

        this.dataBase    = dataBase   ;
        this.tableName   = tableName  ;
        this.idTableName = idTableName;
    }

    /**
     * Return the data base instance
     * @return Database
     */
    private Database getDataBase() {
        return dataBase;
    }

    /**
     * Return the data base table instance
     * @return DatabaseTable
     */
    protected DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(tableName);
    }

    /**
     * Method that find a entity by his id in the data base.
     *
     * @param id entity.
     *
     * @return FermatMessage found.
     *
     * @throws CantReadRecordDataBaseException   if something goes wrong.
     * @throws RecordNotFoundException           if i can't find the record.
     */
    public final E findById(final String id) throws CantReadRecordDataBaseException, RecordNotFoundException {

        if (id == null)
            throw new IllegalArgumentException("The id is required, can not be null.");

        try {

            final DatabaseTable table = getDatabaseTable();
            table.addStringFilter(idTableName, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if(!records.isEmpty())
                return getEntityFromDatabaseTableRecord(records.get(0));
            else
                throw new RecordNotFoundException("id: " + id, "Cannot find an entity with that id in the table "+tableName);

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that find a entity by its columnName and  columnValue in the data base.
     *
     * @param columnName entity.
     * @param columnValue entity.
     *
     * @return FermatMessage found.
     *
     * @throws CantReadRecordDataBaseException   if something goes wrong.
     * @throws RecordNotFoundException           if i can't find the record.
     */
    public final E findEntityByFilter(final String columnName, final String columnValue) throws CantReadRecordDataBaseException, RecordNotFoundException {

        if (columnName == null || columnName.isEmpty() || columnValue == null || columnValue.isEmpty())
            throw new IllegalArgumentException("The filter are required, can not be null or empty.");

        try {

            final DatabaseTable table = getDatabaseTable();
            table.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if(!records.isEmpty())
                return getEntityFromDatabaseTableRecord(records.get(0));
            else
                throw new RecordNotFoundException("columnValue: " + columnValue, "Cannot find an entity with that columnName in the table "+tableName);

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that checks if an entity exists in database.
     *
     * @param id entity.
     *
     * @return a boolean value indicating if the entity exists.
     *
     * @throws CantReadRecordDataBaseException   if something goes wrong.
     */
    public final boolean exists(final String id) throws CantReadRecordDataBaseException {

        if (id == null)
            throw new IllegalArgumentException("The id is required, can not be null.");

        try {

            final DatabaseTable table = getDatabaseTable();
            table.addStringFilter(idTableName, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        }
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All entities.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public final List<E> findAll() throws CantReadRecordDataBaseException {

        try {
            // load the data base to memory
            DatabaseTable table = getDatabaseTable();
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @param offset  pointer to start bringing records.
     * @param max     number of records to bring
     *
     * @return All entities.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public final List<E> findAll(final Integer offset,
                                 final Integer max ) throws CantReadRecordDataBaseException {

        try {
            // load the data base to memory
            DatabaseTable table = getDatabaseTable();
            table.setFilterOffSet(offset.toString());
            table.setFilterTop(max.toString());
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that get the count of all entities on the table.
     *
     * @return count of All entities.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public final long getAllCount() throws CantReadRecordDataBaseException {

        try {
            // load the data base to memory
            DatabaseTable table = getDatabaseTable();

            return table.getCount();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        }
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>DatabaseConstants</code>
     *
     * @return All entities filtering by the parameter specified.
     *
     * @throws CantReadRecordDataBaseException
     *
     */
    public final List<E> findAll(final String columnName ,
                                 final String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null || columnName.isEmpty() || columnValue == null || columnValue.isEmpty())
            throw new IllegalArgumentException("The filter are required, can not be null or empty.");

        try {

            // load the data base to memory with filters
            final DatabaseTable table = getDatabaseTable();

            table.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }

    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>DatabaseConstants</code>
     *
     * @param max     number of records to bring
     * @param offset  pointer to start bringing records.
     *
     * @return All entities filtering by the parameter specified.
     *
     * @throws CantReadRecordDataBaseException
     *
     */
    public final List<E> findAll(final String  columnName ,
                                 final String  columnValue,
                                 final Integer max        ,
                                 final Integer offset     ) throws CantReadRecordDataBaseException {

        if (columnName == null || columnName.isEmpty() || columnValue == null || columnValue.isEmpty())
            throw new IllegalArgumentException("The filter are required, can not be null or empty.");

        try {

            // load the data base to memory with filters
            final DatabaseTable table = getDatabaseTable();

            table.setFilterTop(max.toString());
            table.setFilterOffSet(offset.toString());

            table.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }

    }

    /**
     * Method that get the count of all entities on the table with The valid value of
     * the column name are the att of the <code>DatabaseConstants</code>
     *
     * @return get the count of All entities filtering by the parameter specified.
     *
     * @throws CantReadRecordDataBaseException
     *
     */
    public final long getAllCount(final String columnName ,
                                  final String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null || columnName.isEmpty() || columnValue == null || columnValue.isEmpty())
            throw new IllegalArgumentException("The filter are required, can not be null or empty.");

        try {

            // load the data base to memory with filters
            final DatabaseTable table = getDatabaseTable();

            table.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);

            return table.getCount();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        }
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>DatabaseConstants</code>
     *
     * @return All entities filtering by the parameters specified.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     */
    public final List<E> findAll(final Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");

        try {

            // Prepare the filters
            final DatabaseTable table = getDatabaseTable();

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                tableFilters.add(newFilter);
            }


            // load the data base to memory with filters
            table.setFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>DatabaseConstants</code>
     *
     * @param max     number of records to bring
     * @param offset  pointer to start bringing records.
     *
     * @return All entities filtering by the parameters specified.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     */
    public final List<E> findAll(final Map<String, Object> filters,
                                 final Integer             max    ,
                                 final Integer             offset ) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");

        try {

            // Prepare the filters
            final DatabaseTable table = getDatabaseTable();

            table.setFilterTop(max.toString());
            table.setFilterOffSet(offset.toString());

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                tableFilters.add(newFilter);
            }


            // load the data base to memory with filters
            table.setFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<E> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "Invalid parameter found, maybe the enum is wrong.");
        }
    }

    /**
     * Method that get the count of all entities on the table with The valid value of
     * the key are the att of the <code>DatabaseConstants</code>
     *
     * @return get the count of All entities filtering by the parameters specified.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     */
    public final long getAllCount(final Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");

        try {

            // Prepare the filters
            final DatabaseTable table = getDatabaseTable();

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                tableFilters.add(newFilter);
            }


            // load the data base to memory with filters
            table.setFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            return table.getCount();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + tableName, "The data no exist");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity to create.
     *
     * @throws CantInsertRecordDataBaseException if something goes wrong.
     */
    public final void create(final E entity) throws CantInsertRecordDataBaseException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null");

        try {

            DatabaseTableRecord entityRecord = getDatabaseTableRecordFromEntity(entity);

            getDatabaseTable().insertRecord(entityRecord);

        } catch (final CantInsertRecordException cantInsertRecordException) {

            throw new CantInsertRecordDataBaseException(
                    cantInsertRecordException,
                    "Table Name: " + tableName,
                    "The Template Database triggered an unexpected problem that wasn't able to solve by itself."
            );
        }
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity to update.
     *
     * @throws CantUpdateRecordDataBaseException  if something goes wrong.
     * @throws RecordNotFoundException            if we can't find the record in db.
     */
    public final void update(final E entity) throws CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();
            table.addStringFilter(idTableName, entity.getId(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty())
                table.updateRecord(getDatabaseTableRecordFromEntity(entity));
            else
                throw new RecordNotFoundException("id: " + entity.getId(), "Cannot find an entity with that id.");

        } catch (final CantUpdateRecordException e) {

            throw new CantUpdateRecordDataBaseException(e, "Table Name: " + tableName, "The record do not exist");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id
     *
     * @throws CantDeleteRecordDataBaseException  if something goes wrong.
     * @throws RecordNotFoundException            if we can't find the record in db.
     */
    public final void delete(final String id) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        if (id == null)
            throw new CantDeleteRecordDataBaseException("id null", "The id is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();

            table.addStringFilter(idTableName, id, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty())
                table.deleteRecord(records.get(0));
            else
                throw new RecordNotFoundException("id: "+id, "Cannot find an outgoing message with that id.");

        } catch (CantDeleteRecordException e) {

            throw new CantDeleteRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDeleteRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @throws CantDeleteRecordDataBaseException  if something goes wrong.
     */
    public final void deleteAll() throws CantDeleteRecordDataBaseException {

        try {

            final DatabaseTable table = this.getDatabaseTable();

            table.truncate();

        } catch (CantTruncateTableException e) {

            throw new CantDeleteRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and I cannot delete all records.");
        }
    }

    /**
     * Method that creates a transaction statement pair for a new entity in the database.
     *
     * @param entity to create.
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    public final DatabaseTransactionStatementPair createInsertTransactionStatementPair(final E entity) throws CantCreateTransactionStatementPairException {

        DatabaseTable table = getDatabaseTable();
        DatabaseTableRecord entityRecord = getDatabaseTableRecordFromEntity(entity);

        return new DatabaseTransactionStatementPair(
                table,
                entityRecord
        );
    }

    /**
     * Method that creates a transaction statement pair for the updating of an entity in the database.
     *
     * @param entity to update.
     *
     * @throws CantCreateTransactionStatementPairException  if something goes wrong.
     */
    public final DatabaseTransactionStatementPair createUpdateTransactionStatementPair(final E entity) throws CantCreateTransactionStatementPairException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();
            table.addStringFilter(idTableName, entity.getId(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty())
                return new DatabaseTransactionStatementPair(
                        table,
                        getDatabaseTableRecordFromEntity(entity)
                );
            else
                throw new CantCreateTransactionStatementPairException("id: " + entity.getId(), "Cannot find an entity with that id.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantCreateTransactionStatementPairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * Method that creates a transaction statement pair for the deletion of an entity in the database.
     *
     * @param id to delete.
     *
     * @throws CantCreateTransactionStatementPairException if something goes wrong.
     */
    public final DatabaseTransactionStatementPair createDeleteTransactionStatementPair(final String id) throws CantCreateTransactionStatementPairException {

        DatabaseTable table = getDatabaseTable();

        if (id == null)
            throw new IllegalArgumentException("The id is required, can not be null.");

        try {

            table.addStringFilter(idTableName, id, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty())
                return new DatabaseTransactionStatementPair(
                        table,
                        records.get(0)
                );
            else
                throw new CantCreateTransactionStatementPairException("id: " + id, "Cannot find a record with that id.");
        } catch (final CantLoadTableToMemoryException cantLoadTableToMemoryException) {

            throw new CantCreateTransactionStatementPairException(
                    cantLoadTableToMemoryException,
                    "Table Name: " + tableName,
                    "There was an error trying to load the table."
            );
        }

    }

    public DatabaseTransaction getNewTransaction() {

        return this.dataBase.newTransaction();
    }

    /**
     * Get the timestamp representation if the value are not null
     *
     * @param value
     * @return Timestamp/null
     */
    public Timestamp getTimestampFromLongValue(Long value){
        if (value != null && value != 0){
            return new Timestamp(value);
        }else {
            return null;
        }
    }

    /**
     * Get the long value of the timestamp if are not null
     *
     * @param timestamp
     * @return Long/null
     */
    public Long getLongValueFromTimestamp(Timestamp timestamp){
        if (timestamp != null){
            return timestamp.getTime();
        }else {
            return Long.valueOf(0);
        }
    }

    /**
     * Get the TableName value
     *
     * @return TableName
     */
    protected String getTableName() {
        return tableName;
    }

    /**
     * Get the IdTableName value
     *
     * @return IdTableName
     */
    protected String getIdTableName() {
        return idTableName;
    }


    /**
     * Construct a Entity whit the values of the a DatabaseTableRecord pass
     * by parameter
     *
     * @param record with values from the table
     * @return entity setters the values from table
     */
    abstract protected E getEntityFromDatabaseTableRecord(final DatabaseTableRecord record) throws InvalidParameterException;

    /**
     * Construct a DatabaseTableRecord whit the values of the a entity pass
     * by parameter
     *
     * @param entity the contains the values
     *
     * @return DatabaseTableRecord whit the values
     */
    abstract protected DatabaseTableRecord getDatabaseTableRecordFromEntity(final E entity);
}
