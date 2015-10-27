package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
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

    public IncomingNotificationDao(Database database) {

        this.database = database         ;
    }


    public DatabaseTable getDatabaseTable(){
        return database.getTable(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);
    }

    public com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord createNotification(        UUID                        notificationId        ,
                                           String                      senderPublicKey,
                                           Actors                      senderType     ,
                                           String                      destinationPublicKey   ,
                                           String                      senderAlias,
                                           byte[]                      senderProfileImage,
                                           Actors                      destinationType        ,
                                           IntraUserNotificationDescriptor descriptor      ,
                                           long                        timestamp   ,
                                           ActorProtocolState          protocolState    ,
                                           boolean                     flagReaded      ) throws CantCreateIntraUserException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();


                com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord cryptoPaymentRequestRecord = new com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord(
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

            throw new CantCreateIntraUserException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }
    }
    public void createNotification(com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord actorNetworkServiceRecord) throws CantCreateIntraUserException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

            cryptoPaymentRequestTable.insertRecord(buildDatabaseRecord(entityRecord, actorNetworkServiceRecord));

        } catch (CantInsertRecordException e) {

            throw new CantCreateIntraUserException( "",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }
    }

//    public void createCryptoPaymentInformation(UUID                 requestId    ,
//                                               RequestType direction    ,
//                                               RequestAction        action       ,
//                                               RequestProtocolState protocolState) throws CantCreateCryptoPaymentRequestException {
//
//        try {
//            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);
//
//            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();
//
//            entityRecord.setUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME    , requestId              );
//            entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DIRECTION_COLUMN_NAME     , direction.    getCode());
//            entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());
//            entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME        , action       .getCode());
//
//            cryptoPaymentRequestTable.insertRecord(entityRecord);
//
//        } catch (CantInsertRecordException e) {
//
//            throw new CantCreateCryptoPaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
//        }
//    }

//    public void updateCryptoPaymentInformation(final UUID                 notificatioId    ,
//                                               final RequestType          type         ,
//                                               final RequestAction        action       ,
//                                               final RequestProtocolState protocolState) throws CantCreateCryptoPaymentRequestException,
//            RequestNotFoundException               {
//
//        try {
//
//            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);
//
//            cryptoPaymentRequestTable.setUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);
//
//            cryptoPaymentRequestTable.loadToMemory();
//
//            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();
//
//
//            if (!records.isEmpty()) {
//
//                DatabaseTableRecord entityRecord = records.get(0);
//
//                entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DIRECTION_COLUMN_NAME     , type         .getCode());
//                entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());
//                entityRecord.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME        , action       .getCode());
//
//                cryptoPaymentRequestTable.updateRecord(entityRecord);
//
//            } else
//                throw new RequestNotFoundException(null, "RequestID: "+requestId, "Can not find an crypto payment request with the given request id.");
//
//        } catch (CantUpdateRecordException e) {
//
//            throw new CantCreateCryptoPaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
//        } catch (CantLoadTableToMemoryException e) {
//
//            throw new CantCreateCryptoPaymentRequestException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
//        }
//    }


    @Override
    public List<ActorNetworkServiceRecord> listNotificationsUnreaded() throws CantListIntraWalletUsersException {

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(false), DatabaseFilterType.EQUAL);

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
    public void markReadedNotification(UUID notificationId) {

        try {
            ActorNetworkServiceRecord actorNetworkServiceRecord = getNotificationById(notificationId);

            actorNetworkServiceRecord.setFlagReadead(true);

            update(actorNetworkServiceRecord);

        } catch (CantGetIntraUserException e) {
            e.printStackTrace();
        } catch (IntraUserNotFoundException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }


    }

    @Override
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

    public com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord getNotificationById(final UUID notificationId) throws CantGetIntraUserException, IntraUserNotFoundException {

       // if (notificationId == null)
            //throw new CantGetRequestException("", "requestId, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, notificationId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return buildActorNetworkServiceRecord(records.get(0));
            else
                throw new IntraUserNotFoundException("",null, "RequestID: "+notificationId, "Can not find an crypto payment request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetIntraUserException( "",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {

            throw new CantGetIntraUserException("",exception, "Check the cause."                                                                                ,"");
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

            cryptoPaymentRequestTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

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

    public void changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                    final IntraUserNotificationDescriptor intraUserNotificationDescriptor) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {


        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (intraUserNotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, intraUserNotificationDescriptor.getCode());

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

    public ActorNetworkServiceRecord changeIntraUserNotificationDescriptor(final String                 senderPublicKey    ,
                                                      final IntraUserNotificationDescriptor intraUserNotificationDescriptor,
                                                      final ActorProtocolState actorProtocolState) throws CantUpdateRecordDataBaseException, CantUpdateRecordException, RequestNotFoundException {


        if (senderPublicKey == null)
            throw new CantUpdateRecordDataBaseException("requestId null "   , null);

        if (intraUserNotificationDescriptor == null)
            throw new CantUpdateRecordDataBaseException("protocolState null", null);

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, senderPublicKey, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, intraUserNotificationDescriptor.getCode());
                record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, actorProtocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);

                return buildActorNetworkServiceRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+senderPublicKey, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException( "Exception not handled by the plugin, there is a problem in database and i cannot load the table.",e);
        } catch (CantUpdateRecordException exception) {

            throw new CantUpdateRecordDataBaseException("Cant update record exception.",exception);
        } catch (InvalidParameterException e) {
            throw new CantUpdateRecordDataBaseException("Cant get the updated record exception.",e);
        }
    }


    public List<com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");


        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);

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

    public List<com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord> listRequestsByProtocolStateAndType(final ActorProtocolState protocolState,
                                                                         final IntraUserNotificationDescriptor intraUserNotificationDescriptor) throws CantListIntraWalletUsersException {

        if (protocolState == null)
            throw new CantListIntraWalletUsersException("protocolState null",null, "The protocolState is required, can not be null","");

        if (intraUserNotificationDescriptor == null)
            throw new CantListIntraWalletUsersException("type null"         ,null, "The RequestType is required, can not be null" ,"" );

        try {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode(), DatabaseFilterType.EQUAL);
            cryptoPaymentRequestTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME     , intraUserNotificationDescriptor         .getCode(), DatabaseFilterType.EQUAL);

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


    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                      record                    ,
                                                    com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, cryptoPaymentRequestRecord.getId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, cryptoPaymentRequestRecord.getActorSenderAlias()                              );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_IMAGE_COLUMN_NAME          , cryptoPaymentRequestRecord.getActorSenderProfileImage() .toString()                 );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME     , cryptoPaymentRequestRecord.getIntraUserNotificationDescriptor().getCode()                                 );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorDestinationType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorSenderType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME    , cryptoPaymentRequestRecord.getActorSenderPublicKey()                 );
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME    , cryptoPaymentRequestRecord.getActorDestinationPublicKey());
        record.setLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, cryptoPaymentRequestRecord.getSentDate());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, cryptoPaymentRequestRecord.getActorProtocolState().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME           , String.valueOf(cryptoPaymentRequestRecord.isFlagReadead())                  );

        return record;
    }

    private com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord buildActorNetworkServiceRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   notificationId            = record.getUUIDValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME);
        String senderAlias    = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME);
        String senderProfileImage   = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_IMAGE_COLUMN_NAME      );
        String descriptor       = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME   );
        String destinationType      = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME         );
        String senderType          = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME);
        String senderPublicKey  = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String destinationPublicKey = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        long timestamp           = record.getLongValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
        String protocolState         = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
        String flagReaded  = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME);




        ActorProtocolState  actorProtocolState = ActorProtocolState .getByCode(protocolState);
        Boolean readed =Boolean.valueOf(flagReaded);
        IntraUserNotificationDescriptor intraUserNotificationDescriptor = IntraUserNotificationDescriptor.getByCode(descriptor);

        Actors actorDestinationType = Actors.getByCode(destinationType);
        Actors actorSenderType    = Actors.getByCode(senderType   );

        return new com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord(
                notificationId        ,
                senderAlias,
                senderProfileImage.getBytes()     ,
                intraUserNotificationDescriptor   ,
                actorDestinationType        ,
                actorSenderType      ,
                senderPublicKey    ,
                destinationPublicKey           ,
                timestamp   ,
                actorProtocolState             ,
                readed

        );
    }
}
