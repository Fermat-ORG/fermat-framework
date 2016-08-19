package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantBuildDataBaseRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUserProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class IncomingNotificationDao implements DAO {

    ;

    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME   = DeviceDirectory.LOCAL_USERS.getName() + "/CCP/intraWalletUserNSIncoming";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";
    private final PluginFileSystem pluginFileSystem    ;
    private final UUID                 pluginId;

    public IncomingNotificationDao(Database database,
                                   final PluginFileSystem pluginFileSystem,
                                   final UUID pluginId) {

        this.database = database         ;
        this.pluginFileSystem     = pluginFileSystem    ;
        this.pluginId             = pluginId            ;
    }


    public DatabaseTable getDatabaseTable(){
        return database.getTable(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);
    }

    public ActorNetworkServiceRecord createNotification(final UUID                            notificationId      ,
                                                        final String                          senderPublicKey     ,
                                                        final Actors                          senderType          ,
                                                        final String                          destinationPublicKey,
                                                        final String                          senderAlias         ,
                                                        final String                          senderPhrase         ,
                                                        final byte[]                          senderProfileImage  ,
                                                        final Actors                          destinationType     ,
                                                        final NotificationDescriptor descriptor          ,
                                                        final long                            timestamp           ,
                                                        final ActorProtocolState              protocolState       ,
                                                        final boolean                         flagReaded ,
                                                        int sentCount,
                                                        UUID responseToNotificationId,
                                                        String city, String country) throws CantCreateNotificationException {

        try {

            ActorNetworkServiceRecord incomingNotificationRecord = null;
            if(!existNotification(notificationId))
            {
                final DatabaseTable table = getDatabaseTable();

                final DatabaseTableRecord entityRecord = table.getEmptyRecord();


                incomingNotificationRecord = new ActorNetworkServiceRecord(
                        notificationId      ,
                        senderAlias         ,
                        senderPhrase,
                        senderProfileImage  ,
                        descriptor          ,
                        destinationType     ,
                        senderType          ,
                        senderPublicKey     ,
                        destinationPublicKey,
                        timestamp           ,
                        protocolState       ,
                        flagReaded,
                        0,
                        responseToNotificationId,
                        city,
                        country

                );

                table.insertRecord(buildDatabaseRecord(entityRecord, incomingNotificationRecord));


            }
            return incomingNotificationRecord;

        } catch (CantInsertRecordException e) {

            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");

        } catch (CantGetNotificationException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database","");

        }
    }
    public void createNotification(ActorNetworkServiceRecord actorNetworkServiceRecord) throws CantCreateNotificationException {

        try {
            if(!existNotification(actorNetworkServiceRecord.getId()))
            {
                DatabaseTable incomingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = incomingNotificationTable.getEmptyRecord();

                incomingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, actorNetworkServiceRecord));
            }



        } catch (CantInsertRecordException e) {

            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");

        }
        catch (CantGetNotificationException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database","");

        }
    }

    public List<IntraUserNotification> listUnreadNotifications() throws CantListIntraWalletUsersException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<IntraUserNotification> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildActorNetworkServiceRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(InvalidParameterException exception){

            throw new CantListIntraWalletUsersException("",exception, "Exception invalidParameterException.","");
        }
    }

    @Override
    public void markNotificationAsRead(final UUID notificationId) throws CantConfirmNotificationException {

        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = getNotificationById(notificationId);

            actorNetworkServiceRecord.setFlagReadead(true);

            update(actorNetworkServiceRecord);

        } catch (CantGetNotificationException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+notificationId, "Error trying to get the notification.");
        } catch (NotificationNotFoundException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+notificationId, "Notification not found.");
        } catch (CantUpdateRecordDataBaseException e) {

            throw new CantConfirmNotificationException(e, "notificationId:"+notificationId, "Error updating database.");
        }
    }

    @Override
    public void update(ActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

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
            incomingNotificationtable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME,entity.getId(),DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(incomingNotificationtable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        } catch (CantBuildDataBaseRecordException e) {
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");
            throw cantUpdateRecordDataBaseException;
        }
    }

    public ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetNotificationException, NotificationNotFoundException {

       // if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildActorNetworkServiceRecord(records.get(0));
            else
                throw new NotificationNotFoundException("",null, "RequestID: "+notificationId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {

            throw new CantGetNotificationException("",exception, "Check the cause."                                                                                ,"");
        }

    }



    public boolean existNotification(final UUID notificationId) throws CantGetNotificationException {


        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            return !records.isEmpty();

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        }

    }


    public void changeProtocolState(final UUID               requestId    ,
                                    final ActorProtocolState protocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {

        if (requestId == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (protocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = cryptoPaymentRequestTable.getEmptyRecord();

           record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

            cryptoPaymentRequestTable.updateRecord(record);


       } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }

    public void changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                    final NotificationDescriptor notificationDescriptor) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {


        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (notificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

           DatabaseTableRecord record = cryptoPaymentRequestTable.getEmptyRecord();

          record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode());

           cryptoPaymentRequestTable.updateRecord(record);

        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }

    public ActorNetworkServiceRecord changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                                                           final NotificationDescriptor notificationDescriptor,
                                                                           final ActorProtocolState actorProtocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {


        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("senderPublicKey null "   , null);

        if (notificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            DatabaseTableRecord record = cryptoPaymentRequestTable.getEmptyRecord();


            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorProtocolState.getCode());

            cryptoPaymentRequestTable.updateRecord(record);

            //search this record to return data
            cryptoPaymentRequestTable.loadToMemory();
            return buildActorNetworkServiceRecord(cryptoPaymentRequestTable.getRecords().get(0));



        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        } catch (InvalidParameterException e) {
            throw new CantUpdateRecordDataBaseException("Cant get the updated record exception.",e);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordDataBaseException("Cant get the updated record exception. Load Data",e);

        }
    }


    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildActorNetworkServiceRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(InvalidParameterException exception){

            throw new CantListIntraWalletUsersException("",exception, "Exception invalidParameterException.","");
        }
    }

    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState,
                                                                         final NotificationDescriptor notificationDescriptor) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");

        if (notificationDescriptor == null)
            throw new CantListIntraWalletUsersException("type null"         ,null, "The RequestType is required, can not be null" ,"" );

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<ActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                cryptoPaymentList.add(buildActorNetworkServiceRecord(record));
            }
            return cryptoPaymentList;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(InvalidParameterException exception){

            throw new CantListIntraWalletUsersException("",exception, "Exception invalidParameterException.","");
        }
    }


    private DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord       dbRecord,
                                                    final ActorNetworkServiceRecord record  ) throws CantBuildDataBaseRecordException {

        try {
            dbRecord.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, record.getId());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, record.getActorSenderAlias());

            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, record.getNotificationDescriptor().getCode());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, record.getActorDestinationType().getCode());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, record.getActorSenderType().getCode());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, record.getActorSenderPublicKey());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, record.getActorDestinationPublicKey());
            dbRecord.setLongValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, record.getSentDate());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, record.getActorProtocolState().getCode());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(record.isFlagReadead()));
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME          , record.getActorSenderPhrase());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_COUNTRY_COLUMN_NAME          , record.getCountry());
            dbRecord.setStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_CITY_COLUMN_NAME          , record.getCity());

            if(record.getResponseToNotificationId()!=null)
            dbRecord.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, record.getResponseToNotificationId());


            /**
             * Persist profile image on a file
             */
            if(record.getActorSenderProfileImage()!=null && record.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(record.getActorSenderPublicKey(), record.getActorSenderProfileImage());


            return dbRecord;

        } catch(Exception e)
        {
            throw new CantBuildDataBaseRecordException(CantBuildDataBaseRecordException.DEFAULT_MESSAGE,e,"","");
        }


    }

    private ActorNetworkServiceRecord buildActorNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
    try
    {
        UUID   notificationId            = record.getUUIDValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME);
        String senderAlias    = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
         String descriptor       = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME   );
        String destinationType      = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME         );
        String senderType          = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        String senderPublicKey  = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String destinationPublicKey = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        long timestamp           = record.getLongValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        String protocolState         = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        String flagReaded  = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);
        String senderPhrase = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME);
        UUID   responseToNotificationId            = record.getUUIDValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);

        String city = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_CITY_COLUMN_NAME);
        String country = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_COUNTRY_COLUMN_NAME);

        ActorProtocolState  actorProtocolState = ActorProtocolState .getByCode(protocolState);
        Boolean readed =Boolean.valueOf(flagReaded);
        NotificationDescriptor notificationDescriptor = NotificationDescriptor.getByCode(descriptor);

        Actors actorDestinationType = (destinationType == null) ? Actors.INTRA_USER : Actors.getByCode(destinationType) ;
        Actors actorSenderType    = (senderType == null) ? Actors.INTRA_USER : Actors.getByCode(senderType);

        byte[] profileImage;

        try {
            profileImage = getIntraUserProfileImagePrivateKey(record.getStringValue(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
        } catch(FileNotFoundException e) {
            profileImage = new  byte[0];
        }

        return new ActorNetworkServiceRecord(
                notificationId        ,
                senderAlias,
                senderPhrase,
                profileImage    ,
                notificationDescriptor,
                actorDestinationType        ,
                actorSenderType      ,
                senderPublicKey    ,
                destinationPublicKey           ,
                timestamp   ,
                actorProtocolState             ,
                readed,
                0,
                responseToNotificationId,
                city,
                country


        );
        }
        catch(Exception e)
        {
            throw  new InvalidParameterException();
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
            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,e, "Error persist file.", null);

        } catch (CantCreateFileException e) {

            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,e, "Error creating file.", null);
        } catch (Exception e) {

            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }


    private byte[] getIntraUserProfileImagePrivateKey(final String publicKey) throws CantGetIntraUserProfileImageException,FileNotFoundException {

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
            throw new CantGetIntraUserProfileImageException(CantGetIntraUserProfileImageException.DEFAULT_MESSAGE,e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {

            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {

            throw new CantGetIntraUserProfileImageException(CantGetIntraUserProfileImageException.DEFAULT_MESSAGE,FermatException.wrapException(e), "", "");
        }
    }

    private String buildProfileImageFileName(final String publicKey) {
        return PROFILE_IMAGE_FILE_NAME_PREFIX + "_" + publicKey;
    }
}
