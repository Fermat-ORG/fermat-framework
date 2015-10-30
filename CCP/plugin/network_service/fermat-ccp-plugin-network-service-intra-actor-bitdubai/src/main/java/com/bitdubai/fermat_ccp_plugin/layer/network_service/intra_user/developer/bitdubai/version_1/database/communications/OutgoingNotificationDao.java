package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.16..
 */
public class OutgoingNotificationDao implements com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.DAO {
       ;


    private Database database;

    public OutgoingNotificationDao(Database database) {

        this.database = database         ;
    }

    public DatabaseTable getDatabaseTable(){
        return database.getTable(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
    }

    public ActorNetworkServiceRecord createNotification(        UUID                        notificationId        ,
                                           String                      senderPublicKey,
                                           Actors                      senderType     ,
                                           String                      destinationPublicKey   ,
                                           String                      senderAlias,
                                           byte[]                      senderProfileImage,
                                           Actors                      destinationType        ,
                                           NotificationDescriptor descriptor      ,
                                           long                        timestamp   ,
                                           ActorProtocolState          protocolState    ,
                                           boolean                     flagReaded      ) throws CantCreateNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();


            ActorNetworkServiceRecord cryptoPaymentRequestRecord = new ActorNetworkServiceRecord(
                    notificationId        ,
                    senderAlias,
                    senderProfileImage     ,
                    descriptor   ,
                    destinationType        ,
                    senderType      ,
                    senderPublicKey    ,
                    destinationPublicKey           ,
                    timestamp   ,
                    protocolState             ,
                    flagReaded

            );

            cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, cryptoPaymentRequestRecord));

            return cryptoPaymentRequestRecord;

        } catch (CantInsertRecordException e) {

            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }
    }
    public void createNotification(ActorNetworkServiceRecord actorNetworkServiceRecord) throws CantCreateNotificationException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

            cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, actorNetworkServiceRecord));

        } catch (CantInsertRecordException e) {

            throw new CantCreateNotificationException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }
    }

    public ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetNotificationException, NotificationNotFoundException {

        if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

            try {

                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                cryptoPaymentRequestTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

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
        return null;
    }

    public void changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                                      final NotificationDescriptor notificationDescriptor) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {

        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (notificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, notificationDescriptor.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+senderPublicKey, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException( "Exception not handled by the plugin, there is a problem in database and i cannot load the table.",e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }


    public void changeProtocolState(final UUID                 requestId    ,
                                    final ActorProtocolState protocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {

        if (requestId == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (protocolState == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException( "Exception not handled by the plugin, there is a problem in database and i cannot load the table.",e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        }
    }

    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

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

    public List<ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState,
                                                                              final NotificationDescriptor notificationDescriptor) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");

        if (notificationDescriptor == null)
            throw new CantListIntraWalletUsersException("type null"         ,null, "The RequestType is required, can not be null" ,"" );

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME     , notificationDescriptor.getCode(), DatabaseFilterType.EQUAL);

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
                                                    com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, cryptoPaymentRequestRecord.getId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, cryptoPaymentRequestRecord.getActorSenderAlias()                              );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_IMAGE_COLUMN_NAME          , (cryptoPaymentRequestRecord.getActorSenderProfileImage() != null) ? cryptoPaymentRequestRecord.getActorSenderProfileImage() .toString() : null);
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME     , cryptoPaymentRequestRecord.getNotificationDescriptor().getCode()                                 );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorDestinationType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorSenderType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME    , cryptoPaymentRequestRecord.getActorSenderPublicKey()                 );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME    , cryptoPaymentRequestRecord.getActorDestinationPublicKey());
        record.setLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, cryptoPaymentRequestRecord.getSentDate());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorProtocolState().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME           , String.valueOf(cryptoPaymentRequestRecord.isFlagReadead())                  );

        return record;
    }

    private ActorNetworkServiceRecord buildActorNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   notificationId            = record.getUUIDValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME);
        String senderAlias    = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        String senderProfileImage   = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_IMAGE_COLUMN_NAME      );
        String descriptor       = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME   );
        String destinationType      = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME         );
        String senderType          = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        String senderPublicKey  = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String destinationPublicKey = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        long timestamp           = record.getLongValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        String protocolState         = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        String flagReaded  = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);




        ActorProtocolState  actorProtocolState = ActorProtocolState .getByCode(protocolState);
        Boolean readed =Boolean.valueOf(flagReaded);
        NotificationDescriptor notificationDescriptor = NotificationDescriptor.getByCode(descriptor);

        Actors actorDestinationType = Actors.getByCode(destinationType);
        Actors actorSenderType    = Actors.getByCode(senderType   );

        return new ActorNetworkServiceRecord(
                notificationId        ,
                senderAlias,
                senderProfileImage.getBytes()     ,
                notificationDescriptor,
                actorDestinationType        ,
                actorSenderType      ,
                senderPublicKey    ,
                destinationPublicKey           ,
                timestamp   ,
                actorProtocolState             ,
                readed

        );
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


            DatabaseTableRecord emptyRecord = getDatabaseTable().getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = buildDatabaseRecord(emptyRecord,entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
        }
    }
}
