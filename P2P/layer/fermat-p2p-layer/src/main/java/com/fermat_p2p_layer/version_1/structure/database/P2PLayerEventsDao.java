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
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_EVENT_ID_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_EVENT_NS_OWNER_COLUMN_NAME;
import static com.fermat_p2p_layer.version_1.structure.database.P2PLayerDatabaseConstants.P2P_LAYER_EVENT_TABLE_NAME;
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
 *
 */
public class P2PLayerEventsDao {

    /**
     * Represents the plugin database
     */
    private final Database database;

    /**
     * Constructor
     */
    public P2PLayerEventsDao(final Database database) {

        this.database = database;
    }

    /**
     * This method returns the default database table
     *
     * @return an instance of a P2P_LAYER_MESSAGES database table
     */
    private DatabaseTable getDatabaseTable() {

        return database.getTable(P2P_LAYER_EVENT_TABLE_NAME);
    }

    /**
     * This method persists a NetworkServiceMessage in database
     *
     * @param networkServiceType instance to persist
     *
     * @throws CantPersistsMessageException if something goes wrong.
     */
    public void persistEvent(final UUID packageId,final NetworkServiceType networkServiceType) throws CantPersistsMessageException {

        try{

            if(!existsPackageId(packageId)){

                DatabaseTable databaseTable = getDatabaseTable();

                DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

                buildDatabaseRecord(packageId,networkServiceType, databaseTableRecord);

                databaseTable.insertRecord(databaseTableRecord);
            }

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
     *
     * @return a DatabaseTableRecord instance.
     */
    private DatabaseTableRecord buildDatabaseRecord(final UUID packageId,
                                                    final NetworkServiceType networkServiceType,
                                                    final DatabaseTableRecord   databaseTableRecord  ) {

        databaseTableRecord.setUUIDValue(P2P_LAYER_EVENT_ID_COLUMN_NAME, packageId);
        databaseTableRecord.setStringValue(P2P_LAYER_EVENT_NS_OWNER_COLUMN_NAME, networkServiceType.getCode());

        return databaseTableRecord;
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

            table.addUUIDFilter(P2P_LAYER_EVENT_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            return table.numRecords() != 0;

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
    public NetworkServiceType getEventOwnerById(final UUID packageId) throws CantGetNetworkServiceMessageException, MessageNotFoundException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_EVENT_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if(!records.isEmpty())
                return NetworkServiceType.getByCode(records.get(0).getStringValue(P2P_LAYER_EVENT_NS_OWNER_COLUMN_NAME));
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
     * This method deletes a record from database
     *
     * @param packageId of the message which we're trying to delete.
     *
     * @throws CantDeleteRecordException if something goes wrong.
     */
    public void deleteEventByPackageId(final UUID packageId) throws CantDeleteRecordException {

        try{

            DatabaseTable table = getDatabaseTable();

            table.addUUIDFilter(P2P_LAYER_EVENT_ID_COLUMN_NAME, packageId, DatabaseFilterType.EQUAL);

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

}
