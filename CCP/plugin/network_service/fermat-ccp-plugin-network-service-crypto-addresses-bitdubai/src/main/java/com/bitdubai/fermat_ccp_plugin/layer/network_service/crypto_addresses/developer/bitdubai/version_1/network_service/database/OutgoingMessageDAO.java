package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.database;

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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions.CantDeleteRecordDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions.CantInsertRecordDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions.CantReadRecordDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions.CantUpdateRecordDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.CryptoAddressesNetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesTypes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingMessageDAO {
    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public OutgoingMessageDAO(Database dataBase) {
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
        return getDataBase().getTable(CryptoAddressesNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);
    }

    /**
     * Method that find an CryptoAddressesNetworkServiceMessage by id in the data base.
     *
     * @param id Long id.
     * @return CryptoAddressesNetworkServiceMessage found.
     * @throws CantReadRecordDatabaseException
     */
    public CryptoAddressesNetworkServiceMessage findById(String id) throws CantReadRecordDatabaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        CryptoAddressesNetworkServiceMessage cryptoAddressesNetworkServiceMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.setStringFilter(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into CryptoAddressesNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  CryptoAddressesNetworkServiceMessage
                 */
                cryptoAddressesNetworkServiceMessage = constructFrom(record);
            }

            return cryptoAddressesNetworkServiceMessage;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The data no exist";
            throw new CantReadRecordDatabaseException(CantReadRecordDatabaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All CryptoAddressesNetworkServiceMessage.
     * @throws CantReadRecordDatabaseException
     */
    public List<CryptoAddressesNetworkServiceMessage> findAll() throws CantReadRecordDatabaseException {

        List<CryptoAddressesNetworkServiceMessage> list;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable cryptoAddressesTable = getDatabaseTable();
            cryptoAddressesTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = cryptoAddressesTable.getRecords();

            /*
             * 3 - Create a list of CryptoAddressesNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into CryptoAddressesNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  CryptoAddressesNetworkServiceMessage
                 */
                CryptoAddressesNetworkServiceMessage cryptoAddressesNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(cryptoAddressesNetworkServiceMessage);

            }

            /*
             * return the list
             */
            return list;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The data no exist";
            throw new CantReadRecordDatabaseException(CantReadRecordDatabaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CryptoAddressesNetworkServiceDatabaseConstants</code>
     *
     * @return All CryptoAddressesNetworkServiceMessage.
     * @throws CantReadRecordDatabaseException
     * @see CryptoAddressesNetworkServiceDatabaseConstants
     */
    public List<CryptoAddressesNetworkServiceMessage> findAll(String columnName, String columnValue) throws CantReadRecordDatabaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<CryptoAddressesNetworkServiceMessage> list;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable templateTable = getDatabaseTable();
            templateTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            templateTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 3 - Create a list of CryptoAddressesNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into CryptoAddressesNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  CryptoAddressesNetworkServiceMessage
                 */
                CryptoAddressesNetworkServiceMessage outgoingCryptoAddressesNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(outgoingCryptoAddressesNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The data no exist";
            throw new CantReadRecordDatabaseException(CantReadRecordDatabaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CryptoAddressesNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<CryptoAddressesNetworkServiceMessage>
     * @throws CantReadRecordDatabaseException
     */
    public List<CryptoAddressesNetworkServiceMessage> findAll(Map<String, Object> filters) throws CantReadRecordDatabaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<CryptoAddressesNetworkServiceMessage> list;
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
             * 4 - Create a list of CryptoAddressesNetworkServiceMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into CryptoAddressesNetworkServiceMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  CryptoAddressesNetworkServiceMessage
                 */
                CryptoAddressesNetworkServiceMessage cryptoAddressesNetworkServiceMessage = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(cryptoAddressesNetworkServiceMessage);

            }

        /*
         * return the list
         */
            return list;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The data no exist";
            throw new CantReadRecordDatabaseException(CantReadRecordDatabaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
        }

    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity CryptoAddressesNetworkServiceMessage to create.
     * @throws CantInsertRecordDatabaseException
     */
    public void create(CryptoAddressesNetworkServiceMessage entity) throws CantInsertRecordDatabaseException {

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

            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            throw new CantInsertRecordDatabaseException(CantInsertRecordDatabaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);

        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity CryptoAddressesNetworkServiceMessage to update.
     * @throws CantUpdateRecordDatabaseException
     */
    public void update(CryptoAddressesNetworkServiceMessage entity) throws CantUpdateRecordDatabaseException {

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

            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The record do not exist";
            throw new CantUpdateRecordDatabaseException(CantUpdateRecordDatabaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id Long id.
     * @throws CantDeleteRecordDatabaseException
     */
    public void delete(Long id) throws CantDeleteRecordDatabaseException {

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

            String context = "Database Name: " + CryptoAddressesNetworkServiceDatabaseConstants.DATA_BASE_NAME;
            String possibleCause = "The record do not exist";
            throw new CantDeleteRecordDatabaseException(CantDeleteRecordDatabaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);

        }

    }


    /**
     * @param record with values from the table
     * @return CryptoAddressesNetworkServiceMessage setters the values from table
     */
    private CryptoAddressesNetworkServiceMessage constructFrom(DatabaseTableRecord record) {

        CryptoAddressesNetworkServiceMessage cryptoAddressesNetworkServiceMessage = new CryptoAddressesNetworkServiceMessage();

        try {

            cryptoAddressesNetworkServiceMessage.setId(record.getLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME));
            cryptoAddressesNetworkServiceMessage.setSender(UUID.fromString(record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME)));
            cryptoAddressesNetworkServiceMessage.setReceiver(UUID.fromString(record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME)));
            cryptoAddressesNetworkServiceMessage.setTextContent(record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME));
            cryptoAddressesNetworkServiceMessage.setMessageType(MessagesTypes.getByCode(record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME)));
            cryptoAddressesNetworkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            cryptoAddressesNetworkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME)));
            cryptoAddressesNetworkServiceMessage.setStatus(MessagesStatus.getByCode(record.getStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {

            //this should not happen, but if it happens return null
            return null;
        }

        return cryptoAddressesNetworkServiceMessage;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a CryptoAddressesNetworkServiceMessage pass
     * by parameter
     *
     * @param outgoingCryptoAddressesNetworkServiceMessage the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(CryptoAddressesNetworkServiceMessage outgoingCryptoAddressesNetworkServiceMessage) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getId());
        entityRecord.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getSender().toString());
        entityRecord.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getReceiver().toString());
        entityRecord.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getTextContent());
        entityRecord.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getMessageType().getCode());
        entityRecord.setLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getShippingTimestamp().getTime());
        entityRecord.setLongValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getDeliveryTimestamp().getTime());
        entityRecord.setStringValue(CryptoAddressesNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME, outgoingCryptoAddressesNetworkServiceMessage.getStatus().getCode());

        /*
         * return the new table record
         */
        return entityRecord;

    }
}
