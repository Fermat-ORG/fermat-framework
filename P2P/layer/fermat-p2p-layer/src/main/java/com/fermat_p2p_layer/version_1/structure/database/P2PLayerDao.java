package com.fermat_p2p_layer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;
import com.fermat_p2p_layer.version_1.structure.exceptions.CantCheckPackageIdException;
import com.fermat_p2p_layer.version_1.structure.exceptions.CantDeleteRecordException;
import com.fermat_p2p_layer.version_1.structure.exceptions.CantGetNetworkServiceMessageException;
import com.fermat_p2p_layer.version_1.structure.exceptions.CantPersistsMessageException;
import com.fermat_p2p_layer.version_1.structure.exceptions.MessageNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_CONTENT_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_FAIL_COUNT_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_FERMAT_MESSAGE_STATUS_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_IS_BETWEEN_ACTORS_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_MESSAGES_TABLE_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_NETWORK_SERVICE_TYPE_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_PACKAGE_ID_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_SIGNATURE_COLUMN_NAME;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/08/16.
 *
 * @author darkestpriest
 */
public class P2PLayerDao {

    /**
     * Represents the plugin database
     */
    private final Database database;

    /**
     * Constructor
     */
    public P2PLayerDao(final Database database) {

        this.database = database;
    }

    /**
     * This method returns the default database table
     *
     * @return an instance of a P2P_LAYER_MESSAGES database table
     */
    private DatabaseTable getDatabaseTable() {

        return database.getTable(P2P_LAYER_MESSAGES_TABLE_NAME);
    }

    /**
     * This method persists a NetworkServiceMessage in database
     *
     * @param networkServiceMessage instance to persist
     *
     * @throws CantPersistsMessageException if something goes wrong.
     */
    public void persistMessage(final NetworkServiceMessage networkServiceMessage) throws CantPersistsMessageException {

        try{

            if(existsPackageId(networkServiceMessage.getId())){

                increaseCountFail(networkServiceMessage.getId());

            } else{

                DatabaseTable databaseTable = getDatabaseTable();

                DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

                buildDatabaseRecord(networkServiceMessage, databaseTableRecord);

                databaseTable.insertRecord(databaseTableRecord);
            }

        } catch (CantPersistsMessageException e) {

            throw e;
        } catch (CantInsertRecordException e) {
            throw new CantPersistsMessageException(
                    e,
                    "Persisting a message in database",
                    "Cannot insert record");
        } catch (CantCheckPackageIdException e) {
            throw new CantPersistsMessageException(
                    e,
                    "Persisting a message in database",
                    "Cannot check if the message exists in database");
        } catch (Exception e){
            throw new CantPersistsMessageException(
                    e,
                    "Persisting a message in database",
                    "Unexpected exception");
        }

    }

    /**
     * This method fills a database table record with the given data
     *
     * @param networkServiceMessage with the message information
     * @param databaseTableRecord   database table record in where we put it.
     *
     * @return a DatabaseTableRecord instance.
     */
    private DatabaseTableRecord buildDatabaseRecord(final NetworkServiceMessage networkServiceMessage,
                                                    final DatabaseTableRecord   databaseTableRecord  ) {

        databaseTableRecord.setUUIDValue   (P2P_LAYER_PACKAGE_ID_COLUMN_NAME           , networkServiceMessage.getId());
        databaseTableRecord.setStringValue (P2P_LAYER_CONTENT_COLUMN_NAME              , networkServiceMessage.getContent());
        databaseTableRecord.setFermatEnum  (P2P_LAYER_NETWORK_SERVICE_TYPE_COLUMN_NAME , networkServiceMessage.getNetworkServiceType());
        databaseTableRecord.setStringValue (P2P_LAYER_SENDER_PUBLIC_KEY_COLUMN_NAME    , networkServiceMessage.getSenderPublicKey());
        databaseTableRecord.setStringValue (P2P_LAYER_RECEIVER_PUBLIC_KEY_COLUMN_NAME  , networkServiceMessage.getReceiverPublicKey());
        databaseTableRecord.setLongValue   (P2P_LAYER_SHIPPING_TIMESTAMP_COLUMN_NAME   , getLongValueFromTimestamp(networkServiceMessage.getShippingTimestamp()));
        databaseTableRecord.setLongValue   (P2P_LAYER_DELIVERY_TIMESTAMP_COLUMN_NAME   , getLongValueFromTimestamp(networkServiceMessage.getDeliveryTimestamp()));
        databaseTableRecord.setBooleanValue(P2P_LAYER_IS_BETWEEN_ACTORS_COLUMN_NAME    , networkServiceMessage.isBetweenActors());
        databaseTableRecord.setFermatEnum  (P2P_LAYER_FERMAT_MESSAGE_STATUS_COLUMN_NAME, networkServiceMessage.getMessageStatus());
        databaseTableRecord.setStringValue (P2P_LAYER_SIGNATURE_COLUMN_NAME            , networkServiceMessage.getSignature());
        databaseTableRecord.setIntegerValue(P2P_LAYER_FAIL_COUNT_COLUMN_NAME           , networkServiceMessage.getFailCount());

        return databaseTableRecord;
    }

    /**
     * This method returns a NetworkServiceMessage from DatabaseTableRecord
     *
     * @param databaseTableRecord with the needed information to build the message instance.
     *
     * @return a NetworkServiceMessage instance.
     */
    private NetworkServiceMessage buildNetworkServiceMessage(DatabaseTableRecord databaseTableRecord){

        NetworkServiceMessage networkServiceMessage = new NetworkServiceMessage();
        //Package Id
        networkServiceMessage.setId(databaseTableRecord.getUUIDValue(P2P_LAYER_PACKAGE_ID_COLUMN_NAME));
        //Message content
        networkServiceMessage.setContent(databaseTableRecord.getStringValue(P2P_LAYER_CONTENT_COLUMN_NAME));
        //Network Service type
        String nsTypeString = databaseTableRecord.getStringValue(P2P_LAYER_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        networkServiceMessage.setNetworkServiceType(NetworkServiceType.getByCode(nsTypeString));
        //Sender public key
        networkServiceMessage.setSenderPublicKey(databaseTableRecord.getStringValue(P2P_LAYER_SENDER_PUBLIC_KEY_COLUMN_NAME));
        networkServiceMessage.setReceiverPublicKey(databaseTableRecord.getStringValue(P2P_LAYER_RECEIVER_PUBLIC_KEY_COLUMN_NAME));

        networkServiceMessage.setShippingTimestamp(getTimestampFromLongValue(databaseTableRecord.getLongValue(P2P_LAYER_SHIPPING_TIMESTAMP_COLUMN_NAME)));
        networkServiceMessage.setDeliveryTimestamp(getTimestampFromLongValue(databaseTableRecord.getLongValue(P2P_LAYER_DELIVERY_TIMESTAMP_COLUMN_NAME)));

        //Is between actors
        networkServiceMessage.setIsBetweenActors(databaseTableRecord.getBooleanValue(P2P_LAYER_IS_BETWEEN_ACTORS_COLUMN_NAME));

        //Fermat Message Status
        String fmString = databaseTableRecord.getStringValue(P2P_LAYER_FERMAT_MESSAGE_STATUS_COLUMN_NAME);
        networkServiceMessage.setMessageStatus(MessageStatus.getByCode(fmString));

        //Signature
        networkServiceMessage.setSignature(databaseTableRecord.getStringValue(P2P_LAYER_SIGNATURE_COLUMN_NAME));
        //Fail count
        networkServiceMessage.setFailCount(databaseTableRecord.getIntegerValue(P2P_LAYER_FAIL_COUNT_COLUMN_NAME));

        return networkServiceMessage;

    }

    /**
     * This method checks if a package exists in database
     *
     * @param packageId of the message which we're trying to find.
     *
     * @return a boolean instance representing if we found or not the message.
     *
     * @throws CantCheckPackageIdException if something goes wrong.
     */
    public boolean existsPackageId(final UUID packageId) throws CantCheckPackageIdException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_PACKAGE_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            return table.getCount() != 0;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantCheckPackageIdException(
                    e,
                    "Checking if a package Id exists in database",
                    "Cannot load database table into memory");
        } catch (Exception e){
            throw new CantCheckPackageIdException(
                    e,
                    "Checking if a package Id exists in database",
                    "Unexpected exception");
        }

    }

    /**
     * This method returns a NetworkServiceMessage from database
     *
     * @param packageId in which the message was sent
     *
     * @return an instance of the requested network service message
     *
     * @throws CantGetNetworkServiceMessageException if something goes wrong.
     */
    public NetworkServiceMessage getNetworkServiceMessageById(final UUID packageId) throws CantGetNetworkServiceMessageException, MessageNotFoundException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_PACKAGE_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if(!records.isEmpty())
                return buildNetworkServiceMessage(records.get(0));
            else
                throw new MessageNotFoundException("packageId="+packageId, "Record not found with that id.");

        } catch (MessageNotFoundException e) {

            throw e;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database",
                    "Cannot load database table into memory");
        } catch (Exception e){
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a NetworkServiceMessage list from database by the fail count value
     *
     * @param failCount to filter
     *
     * @return a list of network service messages
     *
     * @throws CantGetNetworkServiceMessageException if something goes wrong.
     */
    public List<NetworkServiceMessage> listMessagesByFailCount(final Integer failCount) throws CantGetNetworkServiceMessageException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addStringFilter(P2P_LAYER_FAIL_COUNT_COLUMN_NAME, failCount.toString(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            List<NetworkServiceMessage> networkServiceMessages = new ArrayList<>();

            for (DatabaseTableRecord record : records)
                networkServiceMessages.add(buildNetworkServiceMessage(record));

            return networkServiceMessages;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database by fail count",
                    "Cannot load database table into memory");
        } catch (Exception e){
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database by fail count",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a NetworkServiceMessage list from database by the fail count interval
     *
     * @param failCountMax representing the max
     * @param failCountMin representing the min
     *
     * @return a list of NetworkServiceMessage corresponding to the given parameters
     *
     * @throws CantGetNetworkServiceMessageException if something goes wrong.
     */
    public List<NetworkServiceMessage> listMessagesByFailCount(final Integer failCountMin,
                                                               final Integer failCountMax) throws CantGetNetworkServiceMessageException {

        /*
         * If the both values are different from null and are equal, then I list only those who match with that amount of fail counts.
         */
        if(failCountMax != null &&
                failCountMin != null &&
                failCountMin >= failCountMax) {

            return listMessagesByFailCount(failCountMin);
        }

        try{

            DatabaseTable table = getDatabaseTable();

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            if (failCountMin != null) {

                DatabaseTableFilter minFilter = table.getNewFilter(
                        P2P_LAYER_FAIL_COUNT_COLUMN_NAME,
                        DatabaseFilterType.GREATER_OR_EQUAL_THAN,
                        failCountMin.toString()
                );

                tableFilters.add(minFilter);
            }

            if (failCountMax != null){

                DatabaseTableFilter minFilter = table.getNewFilter(
                        P2P_LAYER_FAIL_COUNT_COLUMN_NAME,
                        DatabaseFilterType.LESS_OR_EQUAL_THAN,
                        failCountMax.toString()
                );

                tableFilters.add(minFilter);
            }

            table.setFilterGroup(tableFilters, null, DatabaseFilterOperator.AND);

            table.loadToMemory();

            List<NetworkServiceMessage> networkServiceMessages = new ArrayList<>();

            List<DatabaseTableRecord> records = table.getRecords();

            for (DatabaseTableRecord record : records)
                networkServiceMessages.add(buildNetworkServiceMessage(record));

            return networkServiceMessages;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database by fail count",
                    "Cannot load database table into memory");
        } catch (Exception e){
            throw new CantGetNetworkServiceMessageException(
                    e,
                    "Getting NetworkServiceMessage from database by fail count",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a record from database
     *
     * @param packageId of the message which we're trying to delete.
     *
     * @throws CantDeleteRecordException if something goes wrong.
     */
    public void deleteMessageByPackageId(final UUID packageId) throws CantDeleteRecordException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_PACKAGE_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            table.deleteRecord();

        } catch (com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException e) {
            throw new CantDeleteRecordException(
                    e,
                    "Deleting NetworkServiceMessage from database",
                    "Cannot load database table into memory");
        } catch (Exception e){
            throw new CantDeleteRecordException(
                    e,
                    "Deleting NetworkServiceMessage from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a record from database
     *
     * @param packageId of the message which we're trying to increase its fail count.
     *
     * @throws CantPersistsMessageException  if something goes wrong.
     * @throws MessageNotFoundException      if the message cannot be found.
     */
    public void increaseCountFail(final UUID packageId) throws CantPersistsMessageException, MessageNotFoundException {

        try{
            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_PACKAGE_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if(records.isEmpty()){
                throw new MessageNotFoundException("packageId="+packageId, "A record with that package id could not be found.");
            } else{
                DatabaseTableRecord record = records.get(0);

                record.setIntegerValue(P2P_LAYER_FAIL_COUNT_COLUMN_NAME, record.getIntegerValue(P2P_LAYER_FAIL_COUNT_COLUMN_NAME)+1);

                table.updateRecord(record);
            }

        } catch (MessageNotFoundException e) {

            throw e;
        } catch (CantUpdateRecordException e) {
            throw new CantPersistsMessageException(
                    e,
                    "Increasing fail count",
                    "Cannot update database");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantPersistsMessageException(
                    e,
                    "Increasing fail count",
                    "Cannot load database table");
        } catch (Exception e) {
            throw new CantPersistsMessageException(
                    e,
                    "Increasing fail count",
                    "Unexpected exception");
        }
    }

    /**
     * Get the timestamp representation if the value are not null
     *
     * @param value a long instance to convert
     *
     * @return a Timestamp instance or a null value
     */
    public Timestamp getTimestampFromLongValue(final Long value){

        if (value != null && value != 0){
            return new Timestamp(value);
        }else {
            return null;
        }
    }

    /**
     * Get the long value of the timestamp if are not null
     *
     * @param timestamp instance to convert
     *
     * @return a Long instance
     */
    public Long getLongValueFromTimestamp(final Timestamp timestamp){

        if (timestamp != null){
            return timestamp.getTime();
        }else {
            return Long.valueOf(0);
        }
    }

}
