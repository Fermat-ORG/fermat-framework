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
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantBuildDataBaseRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUserProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class OutgoingNotificationDao implements com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.DAO {



    private Database database;

    private static final String PROFILE_IMAGE_DIRECTORY_NAME   = DeviceDirectory.LOCAL_USERS.getName() + "/CCP/intraWalletUserNS";
    private static final String PROFILE_IMAGE_FILE_NAME_PREFIX = "profileImage";
    private final PluginFileSystem pluginFileSystem    ;
    private final UUID                 pluginId            ;

    public OutgoingNotificationDao(Database database,
                                   final PluginFileSystem pluginFileSystem,
                                   final UUID pluginId) {

        this.database = database         ;
        this.pluginFileSystem     = pluginFileSystem    ;
        this.pluginId             = pluginId            ;
    }

    public DatabaseTable getDatabaseTable(){
        return database.getTable(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
    }

    public ActorNetworkServiceRecord createNotification(        UUID                        notificationId        ,
                                           String                      senderPublicKey,
                                           Actors                      senderType     ,
                                           String                      destinationPublicKey   ,
                                           String                      senderAlias,
                                           String                      senderPhrase,
                                           byte[]                      senderProfileImage,
                                           Actors                      destinationType        ,
                                           NotificationDescriptor descriptor      ,
                                           long                        timestamp   ,
                                           ActorProtocolState          protocolState    ,
                                           boolean                     flagReaded,
                                           int sentCount,
                                           UUID responseToNotificationId ,
                                           String city, String country) throws CantCreateNotificationException {

        try {

            ActorNetworkServiceRecord connectionRequestRecord = null;
            if(! existNotification(notificationId))
            {
                DatabaseTable outgoingNotificationTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = outgoingNotificationTable.getEmptyRecord();


                 connectionRequestRecord = new ActorNetworkServiceRecord(
                        notificationId        ,
                        senderAlias,
                        senderPhrase,
                        senderProfileImage     ,
                        descriptor   ,
                        destinationType        ,
                        senderType      ,
                        senderPublicKey    ,
                        destinationPublicKey           ,
                        timestamp   ,
                        protocolState             ,
                        flagReaded,
                        sentCount,
                         responseToNotificationId,
                         city,
                         country

                );


                outgoingNotificationTable.insertRecord(buildDatabaseRecord(entityRecord, connectionRequestRecord));


            }

            return connectionRequestRecord;
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

            if(!existNotification(actorNetworkServiceRecord.getId())) {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

                cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, actorNetworkServiceRecord));
            }


        } catch (CantInsertRecordException e) {

            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        } catch (CantBuildDataBaseRecordException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");

        } catch (CantGetNotificationException e) {
            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database.","");

        }
    }

    public ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetNotificationException, NotificationNotFoundException {

        if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

            try {

                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                cryptoPaymentRequestTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

                cryptoPaymentRequestTable.loadToMemory();

                List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


                if (!records.isEmpty())
                    return buildActorNetworkServiceRecord(records.get(0));
                else
                    throw new NotificationNotFoundException("",null, "RequestID: "+notificationId, "Can not find an intra user request with the given request id.");


            } catch (CantLoadTableToMemoryException exception) {

                throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
            } catch (InvalidParameterException exception) {

                throw new CantGetNotificationException("",exception, "Check the cause."                                                                                ,"");
            }
        return null;
    }

    public List<ActorNetworkServiceRecord> getNotificationByDestinationPublicKey(final String destinationPublicKey) throws CantGetNotificationException, NotificationNotFoundException {


            try {

                List<ActorNetworkServiceRecord> actorNetworkServiceRecordList = new ArrayList<>();

                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey, DatabaseFilterType.EQUAL);

                cryptoPaymentRequestTable.loadToMemory();

                List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

                for (DatabaseTableRecord record : records) {

                    actorNetworkServiceRecordList.add(buildActorNetworkServiceRecord(record));
                }

                return actorNetworkServiceRecordList;

            } catch (CantLoadTableToMemoryException exception) {

                throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
            } catch (InvalidParameterException exception) {

                throw new CantGetNotificationException("",exception, "Check the cause."                                                                                ,"");
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

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+senderPublicKey, "Cannot find a intra user request with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException( "Exception not handled by the plugin, there is a problem in database and i cannot load the table.",e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }


    public void changeProtocolState(final UUID                 notitficationId    ,
                                    final ActorProtocolState protocolState) throws Exception {

        if (notitficationId == null)
            throw new CantUpdateRecordDataBaseException("notification id null "   , null);

        if (protocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notitficationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new Exception("Notification: "+notitficationId,new Throwable("Cannot find a intra user request with the given id."));
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException( "Exception not handled by the plugin, there is a problem in database and i cannot load the table.",e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }


    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndNotDone(ActorProtocolState protocolState) throws CantListIntraWalletUsersException {
        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

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




    public void changeStatusNotSentMessage() throws CantListIntraWalletUsersException {


        try {
            DatabaseTable intraActorRequestTable = getDatabaseTable();

            intraActorRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            intraActorRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            intraActorRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = intraActorRequestTable.getRecords();

            for (DatabaseTableRecord record : records) {
                    //update record

             record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME,  ActorProtocolState.PROCESSING_SEND.getCode());
                intraActorRequestTable.updateRecord(record);

            }
        } catch (CantLoadTableToMemoryException e) {

            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (CantUpdateRecordException e) {
            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");

        }
    }



    public void changeStatusNotSentMessage(String receiveIdentityKey) throws CantListIntraWalletUsersException {



        try {
            DatabaseTable intraActorRequestTable = getDatabaseTable();

            intraActorRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);
            intraActorRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, ActorProtocolState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);

            intraActorRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, receiveIdentityKey, DatabaseFilterType.EQUAL);

            intraActorRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = intraActorRequestTable.getRecords();


            for (DatabaseTableRecord record : records) {

                //update record

                record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME,  ActorProtocolState.PROCESSING_SEND.getCode());

                intraActorRequestTable.updateRecord(record);
            }


        } catch (CantLoadTableToMemoryException e) {

            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (CantUpdateRecordException e) {
            throw new CantListIntraWalletUsersException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");

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

            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.addStringFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            List<com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord> cryptoPaymentList = new ArrayList<>();

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
    public List<IntraUserNotification> listUnreadNotifications() throws CantListIntraWalletUsersException {
        return null;
    }

    @Override
    public void markNotificationAsRead(UUID notificationId) {

    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                      record                    ,
                                                    com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord connectionRequestRecord) throws CantBuildDataBaseRecordException {

        try {
            record.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getId());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, connectionRequestRecord.getActorSenderAlias());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME, connectionRequestRecord.getActorSenderPhrase());

            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME     , connectionRequestRecord.getNotificationDescriptor().getCode()                                 );
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorDestinationType().getCode());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, connectionRequestRecord.getActorSenderType().getCode());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorSenderPublicKey());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, connectionRequestRecord.getActorDestinationPublicKey());
            record.setLongValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, connectionRequestRecord.getSentDate());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, connectionRequestRecord.getActorProtocolState().getCode());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(connectionRequestRecord.isFlagReadead()));
            record.setIntegerValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, connectionRequestRecord.getSentCount());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_CITY_COLUMN_NAME, connectionRequestRecord.getCity());
            record.setStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_COUNTRY_COLUMN_NAME, connectionRequestRecord.getCountry());

            if(connectionRequestRecord.getResponseToNotificationId()!=null)
            record.setUUIDValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, connectionRequestRecord.getResponseToNotificationId());

            /**
             * Persist profile image on a file
             */
            if(connectionRequestRecord.getActorSenderProfileImage()!=null && connectionRequestRecord.getActorSenderProfileImage().length > 0)
                persistNewUserProfileImage(connectionRequestRecord.getActorSenderPublicKey(), connectionRequestRecord.getActorSenderProfileImage());


            return record;
        }
        catch(Exception e)
        {
            throw new CantBuildDataBaseRecordException(CantBuildDataBaseRecordException.DEFAULT_MESSAGE,e,"","");
        }

    }

    private ActorNetworkServiceRecord buildActorNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {
        try
        {
        UUID   notificationId            = record.getUUIDValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME);
        String senderAlias    = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        String senderPhase   = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME      );
        String descriptor       = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME   );
        String destinationType      = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME         );
        String senderType          = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        String senderPublicKey  = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String destinationPublicKey = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        long timestamp           = record.getLongValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        String protocolState         = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        String flagReaded  = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
            String city   = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_CITY_COLUMN_NAME      );
            String country   = record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_COUNTRY_COLUMN_NAME      );

            int sentCount =  record.getIntegerValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME);
            UUID   responseToNotificationId            = record.getUUIDValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);




            ActorProtocolState  actorProtocolState = ActorProtocolState .getByCode(protocolState);
        Boolean readed =Boolean.valueOf(flagReaded);
        NotificationDescriptor notificationDescriptor = NotificationDescriptor.getByCode(descriptor);

        Actors actorDestinationType = Actors.getByCode(destinationType);
        Actors actorSenderType    = Actors.getByCode(senderType);

        byte[] profileImage;

        try {
            profileImage = getIntraUserProfileImagePrivateKey(record.getStringValue(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME));
        } catch(FileNotFoundException e) {
            profileImage = new  byte[0];
        }

        return new ActorNetworkServiceRecord(
                notificationId        ,
                senderAlias,
                senderPhase,
                profileImage,
                notificationDescriptor,
                actorDestinationType        ,
                actorSenderType      ,
                senderPublicKey    ,
                destinationPublicKey           ,
                timestamp   ,
                actorProtocolState             ,
                readed,
                sentCount,
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

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ActorNetworkServiceRecord to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(ActorNetworkServiceRecord entity) throws CantUpdateRecordDataBaseException {

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

            outgoingNotificationTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME,entity.getId(),DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(outgoingNotificationTable, entityRecord);
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

    public void delete(UUID notificationId) throws CantDeleteRecordException {

        try {

            DatabaseTable table = getDatabaseTable();
            table.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();


            for (DatabaseTableRecord record : records) {
                table.deleteRecord(record);
            }


        } catch (CantDeleteRecordException e) {

            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch(CantLoadTableToMemoryException exception){

            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Exception invalidParameterException.","");
        }

    }


    public boolean existNotification(final UUID notificationId) throws CantGetNotificationException {


            try {

                DatabaseTable outgoingTable = getDatabaseTable();

                outgoingTable.addUUIDFilter(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

                if(outgoingTable.numRecords()== 0)
                    return false;
                else
                    return true;



            } catch (Exception exception) {

                throw new CantGetNotificationException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
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

            throw new CantPersistProfileImageException(CantPersistProfileImageException.DEFAULT_MESSAGE,FermatException.wrapException(e), "", "");
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
