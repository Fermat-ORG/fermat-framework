package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications;

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
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantConfirmActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetProfileImageException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantPersistProfileImageException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantUpdateRecordDataBaseException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class IncomingNotificationDao {

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME = DeviceDirectory.LOCAL_USERS.getName() + "/DAP/actorAssetUserNSIncoming";
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
        return database.getTable(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);
    }

    public ActorAssetNetworkServiceRecord createNotification(final UUID notificationId,
                                                            final String senderPublicKey,
                                                            final Actors senderType,
                                                            final String destinationPublicKey,
                                                            final String senderAlias,
                                                            final String senderPhrase,
                                                            final byte[] senderProfileImage,
                                                            final Actors destinationType,
                                                            final AssetNotificationDescriptor assetNotificationDescriptor,
                                                            final long timestamp,
                                                            final ActorAssetProtocolState actorAssetProtocolState,
                                                            final boolean flagRead,
                                                            int sentCount,
                                                            BlockchainNetworkType blockchainNetworkType,
                                                            UUID responseToNotificationId) throws CantCreateActorAssetNotificationException {

        try {

            ActorAssetNetworkServiceRecord incomingNotificationRecord = null;
            if (!existNotification(notificationId)) {
                final DatabaseTable table = getDatabaseTable();

                final DatabaseTableRecord entityRecord = table.getEmptyRecord();


                incomingNotificationRecord = new ActorAssetNetworkServiceRecord(
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
                        flagRead,
                        0,
                        blockchainNetworkType,
                        responseToNotificationId, null

                );

                table.insertRecord(buildDatabaseRecord(entityRecord, incomingNotificationRecord));


            }
            return incomingNotificationRecord;

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
                DatabaseTable incomingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = incomingNotificationTable.getEmptyRecord();

                incomingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, assetUserNetworkServiceRecord));
            }


        } catch (CantInsertRecordException e) {

            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");

        } catch (CantGetActorAssetNotificationException e) {
            throw new CantCreateActorAssetNotificationException("", e, "Exception not handled by the plugin, there is a problem in database", "");

        }
    }

    public List<ActorNotification> listUnreadNotifications() throws CantGetActorAssetNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorNotification> cryptoPaymentList = new ArrayList<>();

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

    //    @Override
    public void markNotificationAsRead(final UUID notificationId) throws CantConfirmActorAssetNotificationException {
        try {
            ActorAssetNetworkServiceRecord assetUserNetworkServiceRecord = getNotificationById(notificationId);

            assetUserNetworkServiceRecord.setFlagRead(true);

            update(assetUserNetworkServiceRecord);

        } catch (CantGetActorAssetNotificationException e) {
            throw new CantConfirmActorAssetNotificationException(e, "notificationId:" + notificationId, "Error trying to get the notification.");
        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantConfirmActorAssetNotificationException(e, "notificationId:" + notificationId, "Error updating database.");
        }
    }

    //    @Override
    public void update(ActorAssetNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

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
            incomingNotificationtable.addUUIDFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, entity.getId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(incomingNotificationtable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + AssetUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        } catch (CantBuildDataBaseRecordException e) {
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");
            throw cantUpdateRecordDataBaseException;
        }
    }

    public ActorAssetNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetActorAssetNotificationException {

        // if (notificationId == null)
        //throw new CantGetRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildAssetUserNetworkServiceRecord(records.get(records.size()-1));
            else
                throw new CantGetActorAssetNotificationException("", null, "RequestID: " + notificationId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetActorAssetNotificationException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetActorAssetNotificationException("", exception, "Check the cause.", "");
        }

    }


    public boolean existNotification(final UUID notificationId) throws CantGetActorAssetNotificationException {


        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

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


    public void changeProtocolState(final UUID requestId,
                                    final ActorAssetProtocolState actorAssetProtocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorAssetNotificationException {

        if (requestId == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (actorAssetProtocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(records.size()-1);

                record.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorAssetNotificationException("RequestId: " + requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {
            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }

    public void changeActorAssetNotificationDescriptor(final String senderPublicKey,
                                                       final AssetNotificationDescriptor assetNotificationDescriptor)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorAssetNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null ", null);

        if (assetNotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(records.size()-1);

                record.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, assetNotificationDescriptor.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new CantGetActorAssetNotificationException("RequestId: " + senderPublicKey, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        }
    }

    public ActorAssetNetworkServiceRecord changeActorAssetNotificationDescriptor(final String senderPublicKey,
                                                                                final AssetNotificationDescriptor assetNotificationDescriptor,
                                                                                final ActorAssetProtocolState actorAssetProtocolState)
            throws CantUpdateRecordDataBaseException, CantUpdateRecordException, CantGetActorAssetNotificationException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("senderPublicKey null ", null);

        if (assetNotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(records.size()-1); //Last pending

                record.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, assetNotificationDescriptor.getCode());
                record.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);

                return buildAssetUserNetworkServiceRecord(record);
            } else {
                throw new CantGetActorAssetNotificationException("senderPublicKey: " + senderPublicKey, "Cannot find a connection request with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException("Exception not handled by the plugin, there is a problem in database and i cannot load the table.", e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.", exception);
        } catch (InvalidParameterException e) {
            throw new CantUpdateRecordDataBaseException("Cant get the updated record exception.", e);
        }
    }


    public List<ActorAssetNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorAssetProtocolState actorAssetProtocolState)
            throws CantGetActorAssetNotificationException {

        if (actorAssetProtocolState == null)
            throw new CantGetActorAssetNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode(), DatabaseFilterType.EQUAL);

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

    public List<ActorAssetNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorAssetProtocolState actorAssetProtocolState,
                                                                                  final AssetNotificationDescriptor assetNotificationDescriptor)
            throws CantGetActorAssetNotificationException {

        if (actorAssetProtocolState == null)
            throw new CantGetActorAssetNotificationException("protocolState null", null, "The protocolState is required, can not be null", "");

        if (assetNotificationDescriptor == null)
            throw new CantGetActorAssetNotificationException("type null", null, "The RequestType is required, can not be null", "");

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorAssetProtocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, assetNotificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

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


    private DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord dbRecord,
                                                    final ActorAssetNetworkServiceRecord record) throws CantBuildDataBaseRecordException {

        try {
            dbRecord.setUUIDValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, record.getId());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, record.getActorSenderAlias());

            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, record.getAssetNotificationDescriptor().getCode());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, record.getActorDestinationType().getCode());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, record.getActorSenderType().getCode());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, record.getActorSenderPublicKey());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, record.getActorDestinationPublicKey());
            dbRecord.setLongValue  (AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, record.getSentDate());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, record.getActorAssetProtocolState().getCode());
            dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(record.isFlagRead()));

            if (record.getBlockchainNetworkType() != null)
                dbRecord.setStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, record.getBlockchainNetworkType().getCode());
            if (record.getResponseToNotificationId() != null)
                dbRecord.setUUIDValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, record.getResponseToNotificationId());

            /**
             * Persist profile image on a file
             */
            if (record.getActorSenderProfileImage() != null && record.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(record.getActorSenderPublicKey(), record.getActorSenderProfileImage());

            return dbRecord;

        } catch (Exception e) {
            throw new CantBuildDataBaseRecordException(CantBuildDataBaseRecordException.DEFAULT_MESSAGE, e, "", "");
        }


    }

    private ActorAssetNetworkServiceRecord buildAssetUserNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
        try {
            BlockchainNetworkType blockchainNetworkType;

            UUID notificationId = record.getUUIDValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME);
            String senderAlias = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
            String descriptor = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);
            String destinationType = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME);
            String senderType = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
            String senderPublicKey = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
            String destinationPublicKey = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
            long timestamp = record.getLongValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
            String protocolState = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
            String flagRead = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);
            String blockChainNetwork = record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
            UUID responseToNotificationId = record.getUUIDValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);

            ActorAssetProtocolState actorAssetProtocolState = ActorAssetProtocolState.getByCode(protocolState);
            Boolean read = Boolean.valueOf(flagRead);
            AssetNotificationDescriptor assetNotificationDescriptor = AssetNotificationDescriptor.getByCode(descriptor);

            if (blockChainNetwork != null)
                blockchainNetworkType = BlockchainNetworkType.getByCode(blockChainNetwork);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

            Actors actorDestinationType = Actors.getByCode(destinationType);
            Actors actorSenderType = Actors.getByCode(senderType);

            byte[] profileImage;

            try {
                profileImage = getActorUserProfileImagePrivateKey(record.getStringValue(AssetUserNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new ActorAssetNetworkServiceRecord(
                    notificationId,
                    senderAlias,
//                    senderPhrase,
                    profileImage,
                    assetNotificationDescriptor,
                    actorDestinationType,
                    actorSenderType,
                    senderPublicKey,
                    destinationPublicKey,
                    timestamp,
                    actorAssetProtocolState,
                    read,
                    0,
                    blockchainNetworkType,
                    responseToNotificationId, null

            );
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
