package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
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
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.NotificationDescriptor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorNotification;
import com.bitdubai.fermat_art_api.layer.actor_network_service.util.ArtistActorNetworkServiceRecord;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantBuildFromDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantCreateActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantGetActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantPendingRequestActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class OutgoingNotificationDao {

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = DeviceDirectory.LOCAL_USERS.getName() + "/ART/ActorNSOutgoing";
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
        return database.getTable(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
    }

    public ArtistActorNetworkServiceRecord createNotification(UUID notificationId,
                                                            String senderPublicKey,
                                                              PlatformComponentType senderType,
                                                            String destinationPublicKey,
                                                            String senderAlias,
//                                                            String senderPhrase,
                                                            byte[] senderProfileImage,
                                                            PlatformComponentType destinationType,
                                                            NotificationDescriptor notificationDescriptor,
                                                            long timestamp,
                                                            ProtocolState protocolState,
                                                            boolean flagRea,
                                                            int sentCount,
                                                            UUID responseToNotificationId) throws CantCreateActorArtistNotificationException {

        try {
            ArtistActorNetworkServiceRecord connectionRequestRecord = null;
            if (!existNotification(notificationId)) {
                DatabaseTable outgoingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = outgoingNotificationTable.getEmptyRecord();

                connectionRequestRecord = new ArtistActorNetworkServiceRecord(
                        notificationId,
                        senderAlias,
//                        senderPhrase,
                        senderProfileImage,
                        notificationDescriptor,
                        destinationType,
                        senderType,
                        senderPublicKey,
                        destinationPublicKey,
                        timestamp,
                        protocolState,
                        flagRea,
                        sentCount,
                        responseToNotificationId,
                        null
                );

                outgoingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, connectionRequestRecord));
            }

            return connectionRequestRecord;
        } catch (CantInsertRecordException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantGetActorArtistNotificationException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildFromDatabaseException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        }
    }

    public void createNotification(ArtistActorNetworkServiceRecord assetUserNetworkServiceRecord) throws CantCreateActorArtistNotificationException {
        try {
            if (!existNotification(assetUserNetworkServiceRecord.getId())) {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

                cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, assetUserNetworkServiceRecord));
            }
        } catch (CantInsertRecordException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantGetActorArtistNotificationException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildFromDatabaseException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        }
    }

    public ArtistActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetActorArtistNotificationException {

        if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

            try {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

                cryptoPaymentRequestTable.loadToMemory();

                List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

                if (!records.isEmpty())
                    return buildUserNetworkServiceRecord(records.get(0));
                else
                    throw new CantGetActorArtistNotificationException("", null, "RequestID: " + notificationId, "Can not find an actor aset user request with the given request id.");
            } catch (CantLoadTableToMemoryException exception) {
                throw new CantGetActorArtistNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
            } catch (InvalidParameterException exception) {
                throw new CantGetActorArtistNotificationException("", exception, "Check the cause.", "");
            }
        return null;
    }

    public List<ArtistActorNetworkServiceRecord> getNotificationByDestinationPublicKey(final String destinationPublicKey) throws CantGetActorArtistNotificationException {
        try {

            List<ArtistActorNetworkServiceRecord> assetUserNetworkServiceRecordList = new ArrayList<>();

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            for (DatabaseTableRecord record : records) {
                assetUserNetworkServiceRecordList.add(buildUserNetworkServiceRecord(record));
            }
            return assetUserNetworkServiceRecordList;

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetActorArtistNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {
            throw new CantGetActorArtistNotificationException("", exception, "Check the cause.", "");
        }
    }

    public PlatformComponentType getActorTypeFromRequest(String actorPublicKeySender) throws CantPendingRequestActorArtistNotificationException {
        try {

            DatabaseTable actorRequestTable = getDatabaseTable();

            actorRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeySender, DatabaseFilterType.EQUAL);
            actorRequestTable.loadToMemory();
            List<DatabaseTableRecord> records = actorRequestTable.getRecords();

            if (!records.isEmpty())
                return buildUserNetworkServiceRecord(records.get(0)).getActorSenderType();
            else{
                actorRequestTable.clearAllFilters();
                actorRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeySender, DatabaseFilterType.EQUAL);
                actorRequestTable.loadToMemory();
                List<DatabaseTableRecord> records1 = actorRequestTable.getRecords();
                if (!records1.isEmpty())
                    return buildUserNetworkServiceRecord(records1.get(0)).getActorDestinationType();
                else
                    throw new CantPendingRequestActorArtistNotificationException(new Exception(), "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantPendingRequestActorArtistNotificationException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantPendingRequestActorArtistNotificationException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (Exception e) {
            throw new CantPendingRequestActorArtistNotificationException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public PlatformComponentType getActorTypeToRequest(String actorPublicKeyDestination) throws CantPendingRequestActorArtistNotificationException {
        try {

            DatabaseTable actorToRequestTable = getDatabaseTable();

            actorToRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeyDestination, DatabaseFilterType.EQUAL);
            actorToRequestTable.loadToMemory();
            List<DatabaseTableRecord> records = actorToRequestTable.getRecords();

            if (!records.isEmpty())
                return buildUserNetworkServiceRecord(records.get(0)).getActorDestinationType();
            else{
                actorToRequestTable.clearAllFilters();
                actorToRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, actorPublicKeyDestination, DatabaseFilterType.EQUAL);
                actorToRequestTable.loadToMemory();
                List<DatabaseTableRecord> records1 = actorToRequestTable.getRecords();
                if (!records1.isEmpty())
                    return buildUserNetworkServiceRecord(records1.get(0)).getActorSenderType();
                else
                    throw new CantPendingRequestActorArtistNotificationException(new Exception(), "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
            }
        } catch (CantLoadTableToMemoryException exception) {

            throw new CantPendingRequestActorArtistNotificationException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantPendingRequestActorArtistNotificationException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (Exception e) {
            throw new CantPendingRequestActorArtistNotificationException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        }
    }

    public void changeActorUserNotificationDescriptor(final String senderPublicKey,
                                                      final NotificationDescriptor notificationDescriptor)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorArtistNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (notificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode());
                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorArtistNotificationException(new Exception(),"RequestId: " + senderPublicKey, "Cannot find a actor  user request with the given id.");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }


    public void changeProtocolState(final UUID notificationId,
                                    final ProtocolState protocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorArtistNotificationException {

        if (notificationId == null)
            throw new CantUpdateRecordDataBaseException("notification id null ", null);

        if (protocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());
                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorArtistNotificationException(new Exception(),"Notification: " + notificationId, "Cannot find a actor  user request with the given id.");
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }


    public List<ArtistActorNetworkServiceRecord> listRequestsByProtocolStateAndNotDone(ProtocolState ProtocolState)
            throws CantGetActorArtistNotificationException {

        if (ProtocolState == null)
            throw new CantGetActorArtistNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ArtistActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildUserNetworkServiceRecord(record));
            }
            return cryptoPaymentList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {
            throw new CantGetActorArtistNotificationException("", exception, "Exception invalidParameterException.", "");
        }
    }


    public void changeStatusNotSentMessage() throws CantGetActorArtistNotificationException {
        try {
            DatabaseTable actorStatusTable = getDatabaseTable();

            actorStatusTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.loadToMemory();

            List<DatabaseTableRecord> records = actorStatusTable.getRecords();

            for (DatabaseTableRecord record : records) {
                //update record
                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.PROCESSING_SEND.getCode());
                actorStatusTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantUpdateRecordException e) {
            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }


    public void changeStatusNotSentMessage(String receiveIdentityKey) throws CantGetActorArtistNotificationException {

        try {
            DatabaseTable actorStatusTable = getDatabaseTable();

            actorStatusTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            actorStatusTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);

            actorStatusTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, receiveIdentityKey, DatabaseFilterType.EQUAL);

            actorStatusTable.loadToMemory();

            List<DatabaseTableRecord> records = actorStatusTable.getRecords();


            for (DatabaseTableRecord record : records) {
                //update record
                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.PROCESSING_SEND.getCode());
                actorStatusTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantUpdateRecordException e) {
            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }

    public List<ArtistActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ProtocolState ProtocolState,
                                                                                  final NotificationDescriptor NotificationDescriptor)
            throws CantGetActorArtistNotificationException {

        if (ProtocolState == null)
            throw new CantGetActorArtistNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        if (NotificationDescriptor == null)
            throw new CantGetActorArtistNotificationException("type null", null, "The RequestType is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, NotificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ArtistActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildUserNetworkServiceRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantGetActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetActorArtistNotificationException("", exception, "Exception invalidParameterException.", "");
        }
    }

    public List<ActorNotification> listUnreadNotifications() throws CantGetActorArtistNotificationException {
        return null;
    }

    public void markNotificationAsRead(UUID notificationId) {

    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    ArtistActorNetworkServiceRecord connectionRequestRecord) throws CantBuildFromDatabaseException {

        try {
            record.setUUIDValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getId());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, connectionRequestRecord.getActorSenderAlias());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, connectionRequestRecord.getNotificationDescriptor().getCode());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorDestinationType().getCode());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorSenderType().getCode());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorSenderPublicKey());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorDestinationPublicKey());
            record.setLongValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, connectionRequestRecord.getSentDate());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, connectionRequestRecord.getProtocolState().getCode());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(connectionRequestRecord.isFlagRead()));
            record.setIntegerValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, connectionRequestRecord.getSentCount());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_USER_NAME_COLUMN_NAME, connectionRequestRecord.getExternalUsername());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_ACCESS_TOKEN_COLUMN_NAME, connectionRequestRecord.getExternalAccesToken());
            record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_PLATFORM_COLUMN_NAME, connectionRequestRecord.getExternalPlatform().getCode());
            if (connectionRequestRecord.getResponseToNotificationId() != null)
                record.setUUIDValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getResponseToNotificationId());

            /**
             * Persist profile image on a file
             */
            if (connectionRequestRecord.getActorSenderProfileImage() != null && connectionRequestRecord.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(connectionRequestRecord.getActorSenderPublicKey(), connectionRequestRecord.getActorSenderProfileImage());
            return record;
        } catch (Exception e) {
            throw new CantBuildFromDatabaseException(CantBuildFromDatabaseException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private ArtistActorNetworkServiceRecord buildUserNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
        try {

            ArtistActorNetworkServiceRecord artistActorNetworkServiceRecord = new ArtistActorNetworkServiceRecord();
            artistActorNetworkServiceRecord.setId(record.getUUIDValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME));
            artistActorNetworkServiceRecord.setActorSenderAlias(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME));
            artistActorNetworkServiceRecord.setNotificationDescriptor(NotificationDescriptor.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorDestinationType(PlatformComponentType.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorSenderType(PlatformComponentType.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorSenderPublicKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            artistActorNetworkServiceRecord.setActorDestinationPublicKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME));
            long timestamp = record.getLongValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
            artistActorNetworkServiceRecord.setSentDate(timestamp);
            artistActorNetworkServiceRecord.setProtocolState(ProtocolState.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setFlagRead(Boolean.valueOf(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setSentCount(record.getIntegerValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME));
            artistActorNetworkServiceRecord.setResponseToNotificationId(record.getUUIDValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalUsername(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_USER_NAME_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalAccesToken(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_ACCESS_TOKEN_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalPlatform(ExternalPlatform.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_EXTERNAL_PLATFORM_COLUMN_NAME)));
            byte[] profileImage;

            try {
                profileImage = getActorUserProfileImagePrivateKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }
            artistActorNetworkServiceRecord.setNewProfileImage(profileImage);
            return artistActorNetworkServiceRecord;
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
    public void update(ArtistActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

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

            outgoingNotificationTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, entity.getId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(outgoingNotificationTable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + ArtistActorNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        } catch (CantBuildFromDatabaseException e) {
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");
            throw cantUpdateRecordDataBaseException;
        }
    }

    public void delete(UUID notificationId) throws CantDeleteRecordException {

        try {
            DatabaseTable table = getDatabaseTable();
            table.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

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


    public boolean existNotification(final UUID notificationId) throws CantGetActorArtistNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty())
                return true;
            else
                return false;

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetActorArtistNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
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


    private byte[] getActorUserProfileImagePrivateKey(final String publicKey) throws CantGetProfileImageException, FileNotFoundException {

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
            throw new CantGetProfileImageException(CantGetProfileImageException.DEFAULT_MESSAGE, e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {
            throw new CantGetProfileImageException(CantGetProfileImageException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }
}
