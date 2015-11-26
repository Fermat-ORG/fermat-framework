/*
* @#NodeConnectionHistoryDao.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.daos;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.NetworkClientP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.entities.NodeConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.daos.NodeConnectionHistoryDao</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeConnectionHistoryDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    public NodeConnectionHistoryDao(Database dataBase) {
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
    DatabaseTable getDatabaseTable(){
        return getDataBase().getTable(NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);
    }

    public NodeConnectionHistory findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        NodeConnectionHistory nodeConnectionHistory = null;

        try{


               /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable table = getDatabaseTable();
            table.setStringFilter(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_TABLE_NAME, id, DatabaseFilterType.EQUAL);
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
                nodeConnectionHistory = constructFrom(record);
            }




        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return nodeConnectionHistory;

    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All NodeConnectionHistory
     * @throws CantReadRecordDataBaseException
     */
    public List<NodeConnectionHistory> findAll() throws CantReadRecordDataBaseException {

        List<NodeConnectionHistory> list = null;

        try{

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
                NodeConnectionHistory nodeConnectionHistory = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(nodeConnectionHistory);
            }

        }catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return list;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>NetworkClientP2PDatabaseConstants</code>
     *
     * @return All NodeConnectionHistory
     * @throws CantReadRecordDataBaseException
     * @see NetworkClientP2PDatabaseConstants
     */
    public List<NodeConnectionHistory> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<NodeConnectionHistory> list = null;

        try{

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
                NodeConnectionHistory nodeConnectionHistory = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(nodeConnectionHistory);
            }

        }catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return list;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CommunicationsCloudServerP2PDatabaseConstants</code>
     *
     * @return All NodeConnectionHistory
     * @throws CantReadRecordDataBaseException
     * @see NetworkClientP2PDatabaseConstants
     */
    public List<NodeConnectionHistory> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        List<NodeConnectionHistory> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try{


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
                NodeConnectionHistory nodeConnectionHistory = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(nodeConnectionHistory);

            }

        }catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return list;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity NodeConnectionHistory to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(NodeConnectionHistory entity) throws CantInsertRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Network Client Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }


    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity NodeConnectionHistory to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(NodeConnectionHistory entity) throws CantUpdateRecordDataBaseException {

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
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

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
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;
        }

    }


    /**
     * @param record with values from the table
     * @return NodeConnectionHistory setters the values from table
     */
    private NodeConnectionHistory constructFrom(DatabaseTableRecord record) {

        NodeConnectionHistory NodeConnectionHistory = new NodeConnectionHistory();

        try{

            NodeConnectionHistory.setIdentityPublicKey(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            NodeConnectionHistory.setIp(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME));
            NodeConnectionHistory.setDefaultPort(record.getIntegerValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME));
            NodeConnectionHistory.setLatitude(Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME)));
            NodeConnectionHistory.setLongitude(Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME)));
            NodeConnectionHistory.setLastConnectionTimestamp(new Timestamp(record.getLongValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME)));

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return NodeConnectionHistory;

    }


    private DatabaseTableRecord constructFrom(NodeConnectionHistory nodeConnectionHistory) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */

        entityRecord.setStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME,nodeConnectionHistory.getIdentityPublicKey());
        entityRecord.setStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME,nodeConnectionHistory.getIp());
        entityRecord.setIntegerValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME, nodeConnectionHistory.getDefaultPort());
        entityRecord.setStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME, nodeConnectionHistory.getLatitude().toString());
        entityRecord.setStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME, nodeConnectionHistory.getLongitude().toString());
        entityRecord.setStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, nodeConnectionHistory.getLastConnectionTimestamp().toString());

          /*
         * return the new table record
         */
        return entityRecord;

    }


}
