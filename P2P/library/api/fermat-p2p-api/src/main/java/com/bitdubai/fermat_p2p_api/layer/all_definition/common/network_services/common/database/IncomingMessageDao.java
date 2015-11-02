package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * @param id UUID id.
     *
     * @return FermatMessage found.
     *
     * @throws CantReadRecordDataBaseException   if something goes wrong.
     * @throws RecordNotFoundException           if i can't find the record.
     */
    public final FermatMessage findById(final UUID id) throws CantReadRecordDataBaseException,
                                                              RecordNotFoundException        {

        if (id == null)
            throw new IllegalArgumentException("The id is required, can not be null.");

        try {

            final DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();

            if(!records.isEmpty())
                return constructFrom(records.get(0));
            else
                throw new RecordNotFoundException("id: " + id, "Cannot find an incoming message with that id.");

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The data no exist");
        }
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All FermatMessage.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public final List<FermatMessage> findAll() throws CantReadRecordDataBaseException {

        try {
            // load the data base to memory
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.loadToMemory();

            final List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            final List<FermatMessage> list = new ArrayList<>();

            // Convert into FermatMessage objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(constructFrom(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The data no exist");
        }
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage filtering by the parameter specified..
     *
     * @throws CantReadRecordDataBaseException
     *
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public final List<FermatMessage> findAll(final String columnName ,
                                             final String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty())
            throw new IllegalArgumentException("The filter are required, can not be null or empty.");

        try {

            // load the data base to memory with filters
            final DatabaseTable networkIntraUserTable = getDatabaseTable();

            networkIntraUserTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            networkIntraUserTable.loadToMemory();

            final List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            final List<FermatMessage> list = new ArrayList<>();

            // Convert into FermatMessage objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(constructFrom(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The data no exist");
        }

    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public final List<FermatMessage> findAll(final Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");

        try {

            // Prepare the filters
            final DatabaseTable networkIntraUserTable = getDatabaseTable();

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = networkIntraUserTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                tableFilters.add(newFilter);
            }


            // load the data base to memory with filters
            networkIntraUserTable.setFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);
            networkIntraUserTable.loadToMemory();

            final List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            final List<FermatMessage> list = new ArrayList<>();

            // Convert into FermatMessage objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(constructFrom(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The data no exist");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity FermatMessage to create.
     *
     * @throws CantInsertRecordDataBaseException if something goes wrong.
     */
    public final void create(final FermatMessage entity) throws CantInsertRecordDataBaseException {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null");

        try {

            DatabaseTableRecord entityRecord = constructFrom(entity);

            getDatabaseTable().insertRecord(entityRecord);

        } catch (final CantInsertRecordException e) {

            throw new CantInsertRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The Template Database triggered an unexpected problem that wasn't able to solve by itself");
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity FermatMessage to update.
     *
     * @throws CantUpdateRecordDataBaseException  if something goes wrong.
     * @throws RecordNotFoundException            if we can't find the record in db.
     */
    public final void update(final FermatMessage entity) throws CantUpdateRecordDataBaseException,
                                                                RecordNotFoundException          {

        if (entity == null)
            throw new IllegalArgumentException("The entity is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();

            table.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, entity.getId(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty()) {

                // TODO make the setters for the fields.

                table.updateRecord(records.get(0));
            } else {
                throw new RecordNotFoundException("id: " + entity.getId(), "Cannot find an incoming message with that id.");
            }

        } catch (final CantUpdateRecordException e) {

            throw new CantUpdateRecordDataBaseException(e, "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, "The record do not exist");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    /**
     * Method that delete a entity in the data base.
     *
     * @param id UUID id.
     *
     * @throws CantDeleteRecordDataBaseException  if something goes wrong.
     * @throws RecordNotFoundException            if we can't find the record in db.
     */
    public final void delete(final UUID id) throws CantDeleteRecordDataBaseException,
                                                   RecordNotFoundException          {

        if (id == null)
            throw new CantDeleteRecordDataBaseException("id null", "The id is required, can not be null.");

        try {

            final DatabaseTable table = this.getDatabaseTable();

            table.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            if (!records.isEmpty())
                table.deleteRecord(records.get(0));
            else
                throw new RecordNotFoundException("id: "+id, "Cannot find an incoming message with that id.");

        } catch (CantDeleteRecordException e) {

            throw new CantDeleteRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDeleteRecordDataBaseException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

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
