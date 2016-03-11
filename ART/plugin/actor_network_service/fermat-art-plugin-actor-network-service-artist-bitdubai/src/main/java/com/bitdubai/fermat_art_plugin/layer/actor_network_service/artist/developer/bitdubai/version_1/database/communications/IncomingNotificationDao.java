package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
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
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantConfirmActorNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantBuildFromDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantCreateActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantGetActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class IncomingNotificationDao {

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = DeviceDirectory.LOCAL_USERS.getName() + "/ART/actorNSIncoming";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;

    public IncomingNotificationDao(Database database,
                                   final PluginFileSystem pluginFileSystem,
                                   final UUID pluginId) {

        this.database = database;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }


    public DatabaseTable getDatabaseTable() {
        return database.getTable(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);
    }

    public ArtistActorNetworkServiceRecord createNotification(final UUID notificationId,
                                                            final String senderPublicKey,
                                                            final PlatformComponentType senderType,
                                                            final String destinationPublicKey,
                                                            final String senderAlias,
                                                            final String senderPhrase,
                                                            final byte[] senderProfileImage,
                                                            final PlatformComponentType destinationType,
                                                            final NotificationDescriptor NotificationDescriptor,
                                                            final long timestamp,
                                                            final ProtocolState ProtocolState,
                                                            final boolean flagRead,
                                                            int sentCount,
                                                            UUID responseToNotificationId) throws CantCreateActorArtistNotificationException {

        try {

            ArtistActorNetworkServiceRecord incomingNotificationRecord = null;
            if (!existNotification(notificationId)) {
                final DatabaseTable table = getDatabaseTable();

                final DatabaseTableRecord entityRecord = table.getEmptyRecord();


                incomingNotificationRecord = new ArtistActorNetworkServiceRecord(
                        notificationId,
                        senderAlias,
//                        senderPhrase,
                        senderProfileImage,
                        NotificationDescriptor,
                        destinationType,
                        senderType,
                        senderPublicKey,
                        destinationPublicKey,
                        timestamp,
                        ProtocolState,
                        flagRead,
                        0,
                        responseToNotificationId, null

                );

                table.insertRecord(buildDatabaseRecord(entityRecord, incomingNotificationRecord));


            }
            return incomingNotificationRecord;

        } catch (CantInsertRecordException e) {

            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildFromDatabaseException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");

        } catch (CantGetActorArtistNotificationException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database", "");

        }
    }

    public void createNotification(ArtistActorNetworkServiceRecord assetUserNetworkServiceRecord) throws CantCreateActorArtistNotificationException {

        try {
            if (!existNotification(assetUserNetworkServiceRecord.getId())) {
                DatabaseTable incomingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = incomingNotificationTable.getEmptyRecord();

                incomingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, assetUserNetworkServiceRecord));
            }


        } catch (CantInsertRecordException e) {

            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildFromDatabaseException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");

        } catch (CantGetActorArtistNotificationException e) {
            throw new CantCreateActorArtistNotificationException("", e, "Exception not handled by the plugin, there is a problem in database", "");

        }
    }

    public List<ActorNotification> listUnreadNotifications() throws CantGetActorArtistNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorNotification> cryptoPaymentList = new ArrayList<>();

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

    //    @Override
    public void markNotificationAsRead(final UUID notificationId) throws CantConfirmActorNotificationException {
        try {
            ArtistActorNetworkServiceRecord assetUserNetworkServiceRecord = getNotificationById(notificationId);

            assetUserNetworkServiceRecord.setFlagRead(true);

            update(assetUserNetworkServiceRecord);

        } catch (CantGetActorArtistNotificationException e) {
            throw new CantConfirmActorNotificationException(e, "notificationId:" + notificationId, "Error trying to get the notification.");
        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantConfirmActorNotificationException(e, "notificationId:" + notificationId, "Error updating database.");
        }
    }

    //    @Override
    public void update(ArtistActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTable incomingNotificationtable = getDatabaseTable();

            DatabaseTableRecord emptyRecord = getDatabaseTable().getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = buildDatabaseRecord(emptyRecord, entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = database.newTransaction();

            //Set filter
            incomingNotificationtable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, entity.getId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(incomingNotificationtable, entityRecord);
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

    public ArtistActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetActorArtistNotificationException {

        // if (notificationId == null)
        //throw new CantGetRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildUserNetworkServiceRecord(records.get(0));
            else
                throw new CantGetActorArtistNotificationException("", null, "RequestID: " + notificationId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetActorArtistNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetActorArtistNotificationException("", exception, "Check the cause.", "");
        }

    }


    public boolean existNotification(final UUID notificationId) throws CantGetActorArtistNotificationException {


        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

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


    public void changeProtocolState(final UUID requestId,
                                    final ProtocolState ProtocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorArtistNotificationException {

        if (requestId == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (ProtocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorArtistNotificationException(new Exception(),"RequestId: " + requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }

    public void changeActorNotificationDescriptor(final String senderPublicKey,
                                                       final NotificationDescriptor NotificationDescriptor)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorArtistNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (NotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, NotificationDescriptor.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorArtistNotificationException(new Exception(),"RequestId: " + senderPublicKey, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }

    public ArtistActorNetworkServiceRecord changeActorNotificationDescriptor(final String senderPublicKey,
                                                                                final NotificationDescriptor NotificationDescriptor,
                                                                                final ProtocolState ProtocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorArtistNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("senderPublicKey null ", null);

        if (NotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(records.size()-1);

                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, NotificationDescriptor.getCode());
                record.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);

                return buildUserNetworkServiceRecord(record);
            } else {
                throw new CantGetActorArtistNotificationException(new Exception(),"senderPublicKey: " + senderPublicKey, "Cannot find a connection request with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        } catch (InvalidParameterException e) {
            throw new CantUpdateRecordDataBaseException("Cant get the updated record exception.", e);
        }
    }


    public List<ArtistActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ProtocolState ProtocolState)
            throws CantGetActorArtistNotificationException {

        if (ProtocolState == null)
            throw new CantGetActorArtistNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode(), DatabaseFilterType.EQUAL);

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

    public List<ArtistActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ProtocolState ProtocolState,
                                                                                  final NotificationDescriptor NotificationDescriptor)
            throws CantGetActorArtistNotificationException {

        if (ProtocolState == null)
            throw new CantGetActorArtistNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        if (NotificationDescriptor == null)
            throw new CantGetActorArtistNotificationException("type null", null, "The RequestType is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ProtocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, NotificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

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


    private DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord dbRecord,
                                                    final ArtistActorNetworkServiceRecord record) throws CantBuildFromDatabaseException {

        try {
            dbRecord.setUUIDValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, record.getId());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, record.getActorSenderAlias());

            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, record.getNotificationDescriptor().getCode());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, record.getActorDestinationType().getCode());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, record.getActorSenderType().getCode());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, record.getActorSenderPublicKey());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, record.getActorDestinationPublicKey());
            dbRecord.setLongValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, record.getSentDate());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, record.getProtocolState().getCode());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(record.isFlagRead()));
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_USER_NAME_COLUMN_NAME, record.getExternalUsername());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_ACCESS_TOKEN_COLUMN_NAME, record.getExternalAccesToken());
            dbRecord.setStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_PLATFORM_COLUMN_NAME, record.getExternalPlatform().getCode());
            if (record.getResponseToNotificationId() != null)
                dbRecord.setUUIDValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, record.getResponseToNotificationId());

            /**
             * Persist profile image on a file
             */
            if (record.getActorSenderProfileImage() != null && record.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(record.getActorSenderPublicKey(), record.getActorSenderProfileImage());

            return dbRecord;

        } catch (Exception e) {
            throw new CantBuildFromDatabaseException(CantBuildFromDatabaseException.DEFAULT_MESSAGE, e, "", "");
        }


    }

    private ArtistActorNetworkServiceRecord buildUserNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
        try {
            ArtistActorNetworkServiceRecord artistActorNetworkServiceRecord = new ArtistActorNetworkServiceRecord();
            artistActorNetworkServiceRecord.setId(record.getUUIDValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME));
            artistActorNetworkServiceRecord.setActorSenderAlias(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME));
            artistActorNetworkServiceRecord.setNotificationDescriptor(NotificationDescriptor.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorDestinationType(PlatformComponentType.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorSenderType(PlatformComponentType.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setActorSenderPublicKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            artistActorNetworkServiceRecord.setActorDestinationPublicKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME));
            long timestamp = record.getLongValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
            artistActorNetworkServiceRecord.setSentDate(timestamp);
            artistActorNetworkServiceRecord.setProtocolState(ProtocolState.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setFlagRead(Boolean.valueOf(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME)));
            artistActorNetworkServiceRecord.setResponseToNotificationId(record.getUUIDValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalUsername(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_USER_NAME_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalAccesToken(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_ACCESS_TOKEN_COLUMN_NAME));
            artistActorNetworkServiceRecord.setExternalPlatform(ExternalPlatform.getByCode(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_EXTERNAL_PLATFORM_COLUMN_NAME)));

            byte[] profileImage;

            try {
                profileImage = getActorUserProfileImagePrivateKey(record.getStringValue(ArtistActorNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }
            artistActorNetworkServiceRecord.setNewProfileImage(profileImage);
            return artistActorNetworkServiceRecord;
            
        } catch (Exception e) {
            throw new InvalidParameterException();
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
