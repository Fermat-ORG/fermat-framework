package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.DATABASE_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessage</code>
 * <p/>
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class OutgoingMessagesDao extends AbstractBaseDao<NetworkServiceMessage> {

    public OutgoingMessagesDao(final Database dataBase) {

        super(
                dataBase                        ,
                OUTGOING_MESSAGES_TABLE_NAME    ,
                OUTGOING_MESSAGES_ID_COLUMN_NAME
        );
    }

    @Override
    protected NetworkServiceMessage getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {


        NetworkServiceMessage networkServiceMessage = new NetworkServiceMessage();

        try {

            networkServiceMessage.setId(UUID.fromString(record.getStringValue(OUTGOING_MESSAGES_ID_COLUMN_NAME)));
            networkServiceMessage.setSenderPublicKey(record.getStringValue(OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setReceiverPublicKey(record.getStringValue(OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME));
            networkServiceMessage.setContent(record.getStringValue(OUTGOING_MESSAGES_CONTENT_COLUMN_NAME));
            networkServiceMessage.setMessageContentType(MessageContentType.getByCode(record.getStringValue(OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME)));
            networkServiceMessage.setShippingTimestamp(new Timestamp(record.getLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME)));

            if (record.getStringValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME) == null)
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(0));
            else
                networkServiceMessage.setDeliveryTimestamp(new Timestamp(record.getLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME)));

            networkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.getByCode(record.getStringValue(OUTGOING_MESSAGES_STATUS_COLUMN_NAME)));

            networkServiceMessage.setFailCount(record.getIntegerValue(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME));

            networkServiceMessage.setIsBetweenActors(Boolean.parseBoolean(record.getStringValue(OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME)));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return networkServiceMessage;
    }

    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NetworkServiceMessage entity) {

        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        entityRecord.setStringValue(OUTGOING_MESSAGES_ID_COLUMN_NAME, entity.getId().toString());
        entityRecord.setStringValue(OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME, entity.getSenderPublicKey());
        entityRecord.setStringValue(OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, entity.getReceiverPublicKey());
        entityRecord.setStringValue(OUTGOING_MESSAGES_CONTENT_COLUMN_NAME, entity.getContent());
        entityRecord.setStringValue(OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME, entity.getMessageContentType().getCode());
        entityRecord.setIntegerValue(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME, entity.getFailCount());

        if (entity.getShippingTimestamp() != null) {
            entityRecord.setLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, entity.getShippingTimestamp().getTime());
        } else {
            entityRecord.setLongValue(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        if (entity.getDeliveryTimestamp() != null) {
            entityRecord.setLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, entity.getDeliveryTimestamp().getTime());
        } else {
            entityRecord.setLongValue(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, (long) 0);
        }

        entityRecord.setStringValue(OUTGOING_MESSAGES_STATUS_COLUMN_NAME, entity.getFermatMessagesStatus().getCode());

        entityRecord.setStringValue(OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME, entity.isBetweenActors().toString());

        return entityRecord;
    }

    public void markAsDelivered(NetworkServiceMessage fermatMessage) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

        if (fermatMessage == null) {
            throw new IllegalArgumentException("The fermatMessage is required, can not be null");
        }

        fermatMessage.setDeliveryTimestamp(new Timestamp(System.currentTimeMillis()));
        fermatMessage.setFermatMessagesStatus(FermatMessagesStatus.DELIVERED);
        update(fermatMessage);

    }

    public void markAsFailed(NetworkServiceMessage fermatMessage) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

        if (fermatMessage == null) {
            throw new IllegalArgumentException("The fermatMessage is required, can not be null");
        }

        fermatMessage.setDeliveryTimestamp(new Timestamp(System.currentTimeMillis()));
        fermatMessage.setFermatMessagesStatus(FermatMessagesStatus.FAILED);
        update(fermatMessage);

    }

    public void markAsPendingToSend(NetworkServiceMessage fermatMessage) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

        if (fermatMessage == null) {
            throw new IllegalArgumentException("The fermatMessage is required, can not be null");
        }

        System.out.println("12345P2P markAsPendingToSend FAIL COUNT = "+fermatMessage.getFailCount());

//        final int MAX_FAIL_COUNT = 15;
//
//        if(fermatMessage.getFailCount()>=MAX_FAIL_COUNT) {
//            System.out.println("12345P2P MAX FAIL COUNT REACHED");
//            markAsFailed(fermatMessage);
//            return;
//        }

        fermatMessage.setDeliveryTimestamp(new Timestamp(System.currentTimeMillis()));
        fermatMessage.setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);
        fermatMessage.setFailCount(fermatMessage.getFailCount() + 1);
        update(fermatMessage);

    }

    /**
     * Method that list the all the network services messages pending to send which had between @countFailMin and @countFailMax intents.
     *
     * @return a list of Network Service Outgoing Messages.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    public List<NetworkServiceMessage> findByFailCount(final Integer countFailMin,
                                                       final Integer countFailMax) throws CantReadRecordDataBaseException {
        final Map<String, Object> filters = new HashMap();

        try {

            DatabaseTable templateTable = getDatabaseTable();
            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

//            filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
//            templateTable.addFermatEnumFilter(OUTGOING_MESSAGES_STATUS_COLUMN_NAME, FermatMessagesStatus.PENDING_TO_SEND, DatabaseFilterType.EQUAL);
            {
                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
                newFilter.setValue(MessagesStatus.PENDING_TO_SEND.getCode());
                tableFilters.add(newFilter);
            }

            if (countFailMin != null) {
                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.GREATER_OR_EQUAL_THAN);
                newFilter.setColumn(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME);
                newFilter.setValue( countFailMin.toString());

                tableFilters.add(newFilter);
//                templateTable.addStringFilter(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME, countFailMin.toString(), DatabaseFilterType.GREATER_OR_EQUAL_THAN);
            }

            if (countFailMax != null){
                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.LESS_OR_EQUAL_THAN);
                newFilter.setColumn(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME);
                newFilter.setValue(countFailMax.toString());

                tableFilters.add(newFilter);
//                templateTable.addStringFilter(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME, countFailMax.toString(), DatabaseFilterType.LESS_OR_EQUAL_THAN);
            }

            if (countFailMax == null && countFailMin == null){
                if (countFailMax != null){
                    DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                    newFilter.setType(DatabaseFilterType.EQUAL);
                    newFilter.setColumn(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME);
                    newFilter.setValue("0");

                    tableFilters.add(newFilter);
//                    templateTable.addStringFilter(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME, "0", DatabaseFilterType.EQUAL);
                }
            }

            templateTable.setFilterGroup(tableFilters, null, DatabaseFilterOperator.AND);
            templateTable.loadToMemory();

            List<DatabaseTableRecord> records = templateTable.getRecords();

            List<NetworkServiceMessage> list = new ArrayList<>();

            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            throw new CantReadRecordDataBaseException(
                    cantLoadTableToMemory,
                    "Database Name: " + DATABASE_NAME,
                    "The data no exist"
            );
        } catch (InvalidParameterException invalidParameterException) {

            throw new CantReadRecordDataBaseException(
                    invalidParameterException,
                    "Database Name: " + DATABASE_NAME,
                    "Data is inconsistent."
            );
        }
    }

    public List<NetworkServiceMessage> findBySentMessages() throws CantReadRecordDataBaseException {
        final Map<String, Object> filters = new HashMap();

        try {

            DatabaseTable templateTable = getDatabaseTable();
            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            {
                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
                newFilter.setValue(MessagesStatus.SENT.getCode());
                tableFilters.add(newFilter);
            }

            templateTable.setFilterGroup(tableFilters, null, DatabaseFilterOperator.AND);
            templateTable.loadToMemory();

            List<DatabaseTableRecord> records = templateTable.getRecords();

            List<NetworkServiceMessage> list = new ArrayList<>();

            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            throw new CantReadRecordDataBaseException(
                    cantLoadTableToMemory,
                    "Database Name: " + DATABASE_NAME,
                    "The data no exist"
            );
        } catch (InvalidParameterException invalidParameterException) {

            throw new CantReadRecordDataBaseException(
                    invalidParameterException,
                    "Database Name: " + DATABASE_NAME,
                    "Data is inconsistent."
            );
        }
    }

}
