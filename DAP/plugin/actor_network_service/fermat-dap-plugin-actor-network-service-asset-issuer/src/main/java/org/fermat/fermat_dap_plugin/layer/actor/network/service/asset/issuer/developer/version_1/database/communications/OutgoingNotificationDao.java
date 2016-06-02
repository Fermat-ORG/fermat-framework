package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.version_1.database.communications;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.fermat.fermat_dap_api.layer.dap_actor_network_service.ActorAssetNetworkServiceRecord;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantBuildDataBaseRecordException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetProfileImageException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetPendingRequestException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantPersistProfileImageException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantUpdateRecordDataBaseException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class OutgoingNotificationDao {

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = DeviceDirectory.LOCAL_USERS.getName() + "/DAP/ActorAssetIssuerNSOutgoing";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    public OutgoingNotificationDao(Database database,
                                   final PluginFileSystem pluginFileSystem,
                                   final UUID pluginId) {

        this.database = database;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public DatabaseTable getDatabaseTable() {
        return database.getTable(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
    }

    public ActorAssetNetworkServiceRecord createNotification(UUID notificationId,
                                                             String senderPublicKey,
                                                             Actors senderType,
                                                             String destinationPublicKey,
                                                             String senderAlias,
//                                                            String senderPhrase,
                                                             byte[] senderProfileImage,
                                                             Actors destinationType,
                                                             AssetNotificationDescriptor assetNotificationDescriptor,
                                                             long timestamp,
                                                             ActorAssetProtocolState actorAssetProtocolState,
                                                             boolean flagRea,
                                                             int sentCount,
                                                             BlockchainNetworkType blockchainNetworkType,
                                                             UUID responseToNotificationId,
                                                             String messageXML) throws CantCreateActorAssetNotificationException {

        try {
            ActorAssetNetworkServiceRecord connectionRequestRecord = null;
            if (!existNotification(notificationId)) {
                DatabaseTable outgoingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = outgoingNotificationTable.getEmptyRecord();

                connectionRequestRecord = new ActorAssetNetworkServiceRecord(
                        notificationId,
                        senderAlias,
//                        senderPhrase,
                        senderProfileImage,
                        assetNotificationDescriptor,
                        destinationType,
                        senderType,
                        senderPublicKey,
                        destinationPublicKey,
                        timestamp,
                        actorAssetProtocolState,
                        flagRea,
                        sentCount,
                        blockchainNetworkType,
                        responseToNotificationId,
                        messageXML
                );

                outgoingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, connectionRequestRecord));
            }

            return connectionRequestRecord;
        } catch (CantInsertRecordException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantGetActorAssetNotificationException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database", "");
        }
    }

    public void createNotification(ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord) throws CantCreateActorAssetNotificationException {
        try {
            if (!existNotification(assetUserNetworkServiceRecord.getId())) {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

                cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, assetUserNetworkServiceRecord));
            }
        } catch (CantInsertRecordException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantGetActorAssetNotificationException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database.", "");
        }
    }

    public ActorAssetNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetActorAssetNotificationException {

        if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

            try {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                cryptoPaymentRequestTable.addUUIDFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

                cryptoPaymentRequestTable.loadToMemory();

                List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

                if (!records.isEmpty())
                    return buildAssetUserNetworkServiceRecord(records.get(0));
                else
                    throw new CantGetActorAssetNotificationException("", null, "RequestID: " + notificationId, "Can not find an actor aset user request with the given request id.");
            } catch (CantLoadTableToMemoryException exception) {
                throw new CantGetActorAssetNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            } catch (InvalidParameterException exception) {
                throw new CantGetActorAssetNotificationException("", exception, "Check the cause.", "");
            }
        return null;
    }

    public List<ActorAssetNetworkServiceRecord> getNotificationByDestinationPublicKey(final String destinationPublicKey) throws CantGetActorAssetNotificationException {
        try {

            List<ActorAssetNetworkServiceRecord> assetUserNetworkServiceRecordList = new ArrayList<>();

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            for (DatabaseTableRecord record : records) {
                assetUserNetworkServiceRecordList.add(buildAssetUserNetworkServiceRecord(record));
            }
            return assetUserNetworkServiceRecordList;

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetActorAssetNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {
            throw new CantGetActorAssetNotificationException("", exception, "Check the cause.", "");
        }
    }

    public Actors getActorTypeFromRequest(String actorPublicKeySender) throws CantGetPendingRequestException {
        try {

            DatabaseTable actorRequestTable = getDatabaseTable();

            actorRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeySender, DatabaseFilterType.EQUAL);
            actorRequestTable.loadToMemory();
            List<DatabaseTableRecord> records = actorRequestTable.getRecords();

            if (!records.isEmpty())
                return buildAssetUserNetworkServiceRecord(records.get(0)).getActorSenderType();
            else {
                actorRequestTable.clearAllFilters();
                actorRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeySender, DatabaseFilterType.EQUAL);
                actorRequestTable.loadToMemory();
                List<DatabaseTableRecord> records1 = actorRequestTable.getRecords();
                if (!records1.isEmpty())
                    return buildAssetUserNetworkServiceRecord(records1.get(0)).getActorDestinationType();
                else
                    throw new CantGetPendingRequestException(new Exception(), "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetPendingRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetPendingRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (Exception e) {
            throw new CantGetPendingRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public Actors getActorTypeToRequest(String actorPublicKeyDestination) throws CantGetPendingRequestException {
        try {

            DatabaseTable actorToRequestTable = getDatabaseTable();

            actorToRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeyDestination, DatabaseFilterType.EQUAL);
            actorToRequestTable.loadToMemory();
            List<DatabaseTableRecord> records = actorToRequestTable.getRecords();

            if (!records.isEmpty())
                return buildAssetUserNetworkServiceRecord(records.get(0)).getActorDestinationType();
            else {
                actorToRequestTable.clearAllFilters();
                actorToRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeyDestination, DatabaseFilterType.EQUAL);
                actorToRequestTable.loadToMemory();
                List<DatabaseTableRecord> records1 = actorToRequestTable.getRecords();
                if (!records1.isEmpty())
                    return buildAssetUserNetworkServiceRecord(records1.get(0)).getActorSenderType();
                else
                    throw new CantGetPendingRequestException(new Exception(), "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
            }
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetPendingRequestException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetPendingRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (Exception e) {
            throw new CantGetPendingRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    public void changeActorUserNotificationDescriptor(final String senderPublicKey,
                                                      final AssetNotificationDescriptor assetNotificationDescriptor)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorAssetNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (assetNotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, assetNotificationDescriptor.getCode());
                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorAssetNotificationException("RequestId: " + senderPublicKey, "Cannot find a actor asset user request with the given id.");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }


    public void changeProtocolState(final UUID notificationId,
                                    final ActorAssetProtocolState actorAssetProtocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorAssetNotificationException {

        if (notificationId == null)
            throw new CantUpdateRecordDataBaseException("notification id null ", null);

        if (actorAssetProtocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode());
                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorAssetNotificationException("Notification: " + notificationId, "Cannot find a actor asset user request with the given id.");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }


    public List<ActorAssetNetworkServiceRecord> listRequestsByProtocolStateAndNotDone(ActorAssetProtocolState actorAssetProtocolState)
            throws CantGetActorAssetNotificationException {

        if (actorAssetProtocolState == null)
            throw new CantGetActorAssetNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorAssetNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildAssetUserNetworkServiceRecord(record));
            }
            return cryptoPaymentList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {
            throw new CantGetActorAssetNotificationException("", exception, "Exception invalidParameterException.", "");
        }
    }


    public void changeStatusNotSentMessage() throws CantGetActorAssetNotificationException {
        try {
            DatabaseTable actorStatusTable = getDatabaseTable();

            actorStatusTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.loadToMemory();

            List<DatabaseTableRecord> records = actorStatusTable.getRecords();

            for (DatabaseTableRecord record : records) {
                //update record
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.PROCESSING_SEND.getCode());
                actorStatusTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantUpdateRecordException e) {
            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }


    public void changeStatusNotSentMessage(String receiveIdentityKey) throws CantGetActorAssetNotificationException {

        try {
            DatabaseTable actorStatusTable = getDatabaseTable();

            actorStatusTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);

            actorStatusTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, receiveIdentityKey, DatabaseFilterType.EQUAL);

            actorStatusTable.loadToMemory();

            List<DatabaseTableRecord> records = actorStatusTable.getRecords();


            for (DatabaseTableRecord record : records) {
                //update record
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorAssetProtocolState.PROCESSING_SEND.getCode());
                actorStatusTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantUpdateRecordException e) {
            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }

    public List<ActorAssetNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorAssetProtocolState actorAssetProtocolState,
                                                                                   final AssetNotificationDescriptor assetNotificationDescriptor)
            throws CantGetActorAssetNotificationException {

        if (actorAssetProtocolState == null)
            throw new CantGetActorAssetNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        if (assetNotificationDescriptor == null)
            throw new CantGetActorAssetNotificationException("type null", null, "The RequestType is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, assetNotificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorAssetNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildAssetUserNetworkServiceRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantGetActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetActorAssetNotificationException("", exception, "Exception invalidParameterException.", "");
        }
    }

    public List<ActorNotification> listUnreadNotifications() throws CantGetActorAssetNotificationException {
        return null;
    }

    public void markNotificationAsRead(UUID notificationId) {

    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    ActorAssetNetworkServiceRecord connectionRequestRecord) throws CantBuildDataBaseRecordException {

        try {
            record.setUUIDValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getId());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, connectionRequestRecord.getActorSenderAlias());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, connectionRequestRecord.getAssetNotificationDescriptor().getCode());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorDestinationType().getCode());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorSenderType().getCode());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorSenderPublicKey());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorDestinationPublicKey());
            record.setLongValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, connectionRequestRecord.getSentDate());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, connectionRequestRecord.getActorAssetProtocolState().getCode());
            record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(connectionRequestRecord.isFlagRead()));
            record.setIntegerValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, connectionRequestRecord.getSentCount());
            if (connectionRequestRecord.getBlockchainNetworkType() != null)
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, connectionRequestRecord.getBlockchainNetworkType().getCode());
            if (connectionRequestRecord.getResponseToNotificationId() != null)
                record.setUUIDValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getResponseToNotificationId());
            if (connectionRequestRecord.getMessageXML() != null)
                record.setStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_MESSAGE, connectionRequestRecord.getMessageXML());

            /**
             * Persist profile image on a file
             */
            if (connectionRequestRecord.getActorSenderProfileImage() != null && connectionRequestRecord.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(connectionRequestRecord.getActorSenderPublicKey(), connectionRequestRecord.getActorSenderProfileImage());
            return record;
        } catch (Exception e) {
            throw new CantBuildDataBaseRecordException(CantBuildDataBaseRecordException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private ActorAssetNetworkServiceRecord buildAssetUserNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
        try {
            BlockchainNetworkType blockchainNetworkType = null;

            UUID notificationId = record.getUUIDValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME);
            String senderAlias = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
            String descriptor = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);
            String destinationType = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
            String senderType = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
            String senderPublicKey = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
            String destinationPublicKey = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
            long timestamp = record.getLongValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
            String protocolState = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
            String flagRead = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
            int sentCount = record.getIntegerValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME);
            String blockchainNetwork = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
            UUID responseToNotificationId = record.getUUIDValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);
            String messageXML = record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_MESSAGE);


            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.getByCode(protocolState);
            Boolean read = Boolean.valueOf(flagRead);
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.getByCode(descriptor);

            if (blockchainNetwork != null)
                blockchainNetworkType = BlockchainNetworkType.getByCode(blockchainNetwork);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

            Actors actorDestinationType = Actors.getByCode(destinationType);
            Actors actorSenderType = Actors.getByCode(senderType);

            byte[] profileImage;

            try {
                profileImage = getActorUserProfileImagePrivateKey(record.getStringValue(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new ActorAssetNetworkServiceRecord(
                    notificationId,
                    senderAlias,
//                    senderPhase,
                    profileImage,
                    assetNotificationDescriptor,
                    actorDestinationType,
                    actorSenderType,
                    senderPublicKey,
                    destinationPublicKey,
                    timestamp,
                    actorAssetProtocolState,
                    read,
                    sentCount,
                    blockchainNetworkType,
                    responseToNotificationId,
                    messageXML
            );

        } catch (Exception e) {
            throw new InvalidParameterException();
        }
    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity AssetUserNetworkServiceRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(ActorAssetNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {
            DatabaseTable outgoingNotificationTable = getDatabaseTable();
            DatabaseTableRecord emptyRecord = getDatabaseTable().getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = buildDatabaseRecord(emptyRecord, entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = database.newTransaction();

            //set filter

            outgoingNotificationTable.addUUIDFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, entity.getId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(outgoingNotificationTable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + AssetIssuerNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        } catch (CantBuildDataBaseRecordException e) {
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");
            throw cantUpdateRecordDataBaseException;
        }
    }

    public void delete(UUID notificationId) throws CantDeleteRecordException {

        try {
            DatabaseTable table = getDatabaseTable();
            table.addUUIDFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            for (DatabaseTableRecord record : records) {
                table.deleteRecord(record);
            }
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.", "");
        }

    }


    public boolean existNotification(final UUID notificationId) throws CantGetActorAssetNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(AssetIssuerNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty())
                return true;
            else
                return false;

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetActorAssetNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }


    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {

        try {

            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);
            file.persistToMedia();

        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE, e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }


    private byte[] getActorUserProfileImagePrivateKey(final String publicKey) throws CantGetActorAssetProfileImageException, FileNotFoundException {

        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    PROFILE_IMAGE_DIRECTORY_NAME,
                    buildProfileImageFileName(publicKey),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();
            return file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetActorAssetProfileImageException(CantGetActorAssetProfileImageException.DEFAULT_MESSAGE, e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {
            throw new CantGetActorAssetProfileImageException(CantGetActorAssetProfileImageException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }
}
