package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.database.IncomingMessageDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class IncomingMessageDao {

    private final Database dataBase;

    public IncomingMessageDao(final Database dataBase) {

        this.dataBase = dataBase;
    }

    private Database getDataBase() {
        return dataBase;
    }

    private DatabaseTable getDatabaseTable() {

        return getDataBase().getTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);
    }

    /**
     * Method that find an FermatMessage by id in the data base.
     *
     * @param id Long id.
     * @return FermatMessage found.
     * @throws CantReadRecordDataBaseException
     */
    public final FermatMessage findById(final String id) throws CantReadRecordDataBaseException {

        if (id == null)
            throw new IllegalArgumentException("The id is required, can not be null");

        FermatMessage incomingTemplateNetworkServiceMessage = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  FermatMessage
                 */
                incomingTemplateNetworkServiceMessage = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return incomingTemplateNetworkServiceMessage;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All FermatMessage.
     * @throws CantReadRecordDataBaseException
     */
    public final List<FermatMessage> findAll() throws CantReadRecordDataBaseException {

        List<FermatMessage> list = null;
        try {
            /*
             * 1 - load the data base to memory
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.loadToMemory();
            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();
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
                FermatMessage incomingTemplateNetworkServiceMessage = constructFrom(record);
                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingTemplateNetworkServiceMessage);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
     * the column name are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public final List<FermatMessage> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<FermatMessage> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

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
                FermatMessage incomingTemplateNetworkServiceMessage = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(incomingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public final List<FermatMessage> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");


        List<FermatMessage> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = networkIntraUserTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }


            /*
             * 2 - load the data base to memory with filters
             */
            networkIntraUserTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            networkIntraUserTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

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
                FermatMessage incomingTemplateNetworkServiceMessage = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(incomingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity FermatMessage to create.
     * @throws CantInsertRecordDataBaseException
     */
    public final void create(final FermatMessage entity) throws CantInsertRecordDataBaseException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null");

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
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity FermatMessage to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(final FermatMessage entity) throws CantUpdateRecordDataBaseException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null.");

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
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
    public final void delete(Long id) throws CantDeleteRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {

            /*
             * Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();


            //getDatabaseTable().deleteRecord(transaction);

        } catch (Exception databaseTransactionFailedException) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;
        }

    }

    /**
     * @param record with values from the table
     * @return FermatMessage setters the values from table
     */
    private FermatMessage constructFrom(final DatabaseTableRecord record) {//throws InvalidParameterException {


        final FermatMessageCommunication incomingTemplateNetworkServiceMessage = new FermatMessageCommunication();
        try {
            incomingTemplateNetworkServiceMessage.setId(record.getUUIDValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setSender(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setReceiver(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setContent(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME));
            incomingTemplateNetworkServiceMessage.setFermatMessageContentType(FermatMessageContentType.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME)));
            incomingTemplateNetworkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME)));
            incomingTemplateNetworkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME)));

            incomingTemplateNetworkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME)));

        } catch(Exception e) {

        }
        return incomingTemplateNetworkServiceMessage;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a FermatMessage pass
     * by parameter
     *
     * @param incomingTemplateNetworkServiceMessage the contains the values
     *
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(final FermatMessage incomingTemplateNetworkServiceMessage) {

        /*
         * Create the record to the entity
         */
        final DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setUUIDValue  (CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME          , incomingTemplateNetworkServiceMessage.getId()                                );
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME   , incomingTemplateNetworkServiceMessage.getSender()                            );
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME , incomingTemplateNetworkServiceMessage.getReceiver()                          );
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getContent()                           );
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME        , incomingTemplateNetworkServiceMessage.getFermatMessageContentType().getCode());

        if (incomingTemplateNetworkServiceMessage.getShippingTimestamp() != null)
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getShippingTimestamp().getTime());
        else
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, 0);


        if (incomingTemplateNetworkServiceMessage.getDeliveryTimestamp() != null)
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getDeliveryTimestamp().getTime());
        else
            entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, 0);

        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, incomingTemplateNetworkServiceMessage.getFermatMessagesStatus().getCode());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
