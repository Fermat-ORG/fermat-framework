package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.enums.ActorProtocolState;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by José Vilchez on 15/02/16.
 */
public class OutgoingNotificationDao {
    private Database database;

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
        return database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
    }

    public void createNotification(NegotiationTransmission negotiationTransmission) throws CantCreateNotificationException {

        try {

            if (!existNotification(negotiationTransmission)) {
                DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

                DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

                cryptoPaymentRequestTable.insertRecord(loadRecordAsSendNegotiationTransmission(entityRecord, negotiationTransmission));
            }


        } catch (CantInsertRecordException e) {
            throw new CantCreateNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        }
    }

    public void createNotification(NegotiationTransmission negotiationTransmission, NegotiationTransmissionState negotiationTransmissionState) throws CantCreateNotificationException {

        try {

//            if (!existNotification(negotiationTransmission)) {
            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            DatabaseTableRecord entityRecord = cryptoPaymentRequestTable.getEmptyRecord();

            cryptoPaymentRequestTable.insertRecord(loadRecordAsSendNegotiationTransmission(entityRecord, negotiationTransmission, negotiationTransmissionState));
//            }


        } catch (CantInsertRecordException e) {
            throw new CantCreateNotificationException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        }
    }

    public boolean existNotification(final NegotiationTransmission negotiationTransmission) throws CantCreateNotificationException {


        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId(), DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty())
                return true;
            else
                return false;

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantCreateNotificationException();
        }

    }

    /*CONFIRM RECEPTION*/
    public void confirmReception(UUID transactionId) throws CantConfirmNotificationException {
        try {

            NegotiationTransmission negotiationTransmission = getNotificationById(transactionId);
            negotiationTransmission.setTransmissionState(NegotiationTransmissionState.DONE);
            update(negotiationTransmission);
            System.out.print("\n\n**** 19.2.2) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT, CONFIRM TRANSAMISSION ****\n");

        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantConfirmNotificationException(e, e.getContext(), "ERROR UPDATING DATABASE. CHECK UPDATE METHOD");
        }
    }

    public NegotiationTransmission getNotificationById(final UUID transactionId) {

        NegotiationTransmission negotiationTransmission = null;
        if (transactionId == null)
            return null;

        try {

            DatabaseTable cryptoPaymentRequestTable = getDatabaseTable();

            cryptoPaymentRequestTable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();


            if (!records.isEmpty()) {
                negotiationTransmission = buildNegotiationTransmission(records.get(0));
            }


        } catch (CantLoadTableToMemoryException e) {

            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return negotiationTransmission;

    }

    public void update(NegotiationTransmission entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTable outgoingNotificationtable = getDatabaseTable();

            DatabaseTableRecord emptyRecord = getDatabaseTable().getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = loadRecordAsSendNegotiationTransmission(emptyRecord, entity);

            /**
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = database.newTransaction();

            //Set filter
            outgoingNotificationtable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME, entity.getTransmissionId(), DatabaseFilterType.EQUAL);
//            outgoingNotificationtable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME, entity.getTransactionId(), DatabaseFilterType.EQUAL);
//            outgoingNotificationtable.addUUIDFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME, entity.getNegotiationId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(outgoingNotificationtable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            // Register the failure.
            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Database Name: ").append(NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuilder.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;
//        } catch (CantBuildDataBaseRecordException e) {
//            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");
//            throw cantUpdateRecordDataBaseException;
        }
    }

    public List<NegotiationTransmission> findAllByTransmissionState(NegotiationTransmissionState negotiationTransmissionState) throws CantReadRecordDataBaseException {

        try {

            if (negotiationTransmissionState == null)
                throw new IllegalArgumentException("The filters are required, can not be null or empty");

            List<NegotiationTransmission> list = new ArrayList<>();

            List<DatabaseTableRecord> records;
            DatabaseTable table = this.database.getTable(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
            if (table == null)
                throw new CantReadRecordDataBaseException("Cant check if negotiation_transmission_network_service exists", "Network Service - Negotiation Transmission", "");

            table.addStringFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmissionState.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            records = table.getRecords();
            if (records.isEmpty())
                return list;

            for (DatabaseTableRecord record : records) {
                list.add(buildNegotiationTransmission(record));
            }

            return list;

        } catch (CantLoadTableToMemoryException e) {
            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Table Name: ").append(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);
            throw new CantReadRecordDataBaseException(CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, contextBuilder.toString(), "The data no exist");
        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException(CantRegisterSendNegotiationTransmissionException.DEFAULT_MESSAGE, e, "", "Invalid parameter");
        }
    }

    private NegotiationTransmission buildNegotiationTransmission(DatabaseTableRecord record) throws InvalidParameterException {
        try {

            UUID transmissionId = record.getUUIDValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME);
            UUID transactionId = record.getUUIDValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME);
            UUID negotiationId = record.getUUIDValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME);
            String negotiationTransactionType = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME);
            String publicKeyActorSend = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME);
            String actorSendType = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME);
            String publicKeyActorReceive = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME);
            String actorReceiveType = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME);
            String transmissionType = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME);
            String transmissionState = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME);
            String negotiationType = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME);
            String negotiationXML = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME);
            long timestamp = record.getLongValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME);
            String pendingFlag = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME);
            String flagRead = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME);
            String protocolState = record.getStringValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME);
            int sentCount = 0;
            UUID responseToNotificationId = record.getUUIDValue(com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME);

            ActorProtocolState actorProtocolState = null;
            if (protocolState != null) {
                actorProtocolState = ActorProtocolState.getByCode(protocolState);
            }

            return new NegotiationTransmissionImpl(
                    transmissionId,
                    transactionId,
                    negotiationId,
                    NegotiationTransactionType.getByCode(negotiationTransactionType),
                    publicKeyActorSend,
                    PlatformComponentType.getByCode(actorSendType),
                    publicKeyActorReceive,
                    PlatformComponentType.getByCode(actorReceiveType),
                    NegotiationTransmissionType.getByCode(transmissionType),
                    NegotiationTransmissionState.getByCode(transmissionState),
                    NegotiationType.getByCode(negotiationType),
                    negotiationXML,
                    timestamp,
                    Boolean.valueOf(pendingFlag),
                    Boolean.valueOf(flagRead),
                    actorProtocolState,
                    sentCount,
                    responseToNotificationId
            );
        } catch (Exception e) {
            throw new InvalidParameterException();
        }
    }

    private DatabaseTableRecord loadRecordAsSendNegotiationTransmission(DatabaseTableRecord record, NegotiationTransmission negotiationTransmission, NegotiationTransmissionState negotiationTransmissionState) {
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME, negotiationTransmission.getTransactionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME, negotiationTransmission.getNegotiationId());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationTransactionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME, negotiationTransmission.getPublicKeyActorSend());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME, negotiationTransmission.getActorSendType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME, negotiationTransmission.getPublicKeyActorReceive());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME, negotiationTransmission.getActorReceiveType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmissionState.getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME, negotiationTransmission.getNegotiationXML());
        record.setLongValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, negotiationTransmission.getTimestamp());
//        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, negotiationTransmission.getActorProtocolState().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(negotiationTransmission.isFlagRead()));
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME, String.valueOf(negotiationTransmission.isPendingFlag()));
        record.setIntegerValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, negotiationTransmission.getSentCount());
        if (negotiationTransmission.getResponseToNotificationId() != null)
            record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, negotiationTransmission.getResponseToNotificationId());
        return record;
    }

    private DatabaseTableRecord loadRecordAsSendNegotiationTransmission(DatabaseTableRecord record, NegotiationTransmission negotiationTransmission) {
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME, negotiationTransmission.getTransactionId());
        record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME, negotiationTransmission.getNegotiationId());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationTransactionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME, negotiationTransmission.getPublicKeyActorSend());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME, negotiationTransmission.getActorSendType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME, negotiationTransmission.getPublicKeyActorReceive());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME, negotiationTransmission.getActorReceiveType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmission.getTransmissionState().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME, negotiationTransmission.getNegotiationType().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME, negotiationTransmission.getNegotiationXML());
        record.setLongValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, negotiationTransmission.getTimestamp());
//        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, negotiationTransmission.getActorProtocolState().getCode());
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, String.valueOf(negotiationTransmission.isFlagRead()));
        record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME, String.valueOf(negotiationTransmission.isPendingFlag()));
        record.setIntegerValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, negotiationTransmission.getSentCount());
        if (negotiationTransmission.getResponseToNotificationId() != null)
            record.setUUIDValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, negotiationTransmission.getResponseToNotificationId());
        return record;
    }

    public void changeStatusNotSentMessage() throws CantReadRecordDataBaseException {

        try {
            DatabaseTable negotiationTransmissionTable = getDatabaseTable();

            negotiationTransmissionTable.addStringFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, NegotiationTransmissionState.PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            negotiationTransmissionTable.addStringFilter(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, NegotiationTransmissionState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);

            negotiationTransmissionTable.loadToMemory();


            for (DatabaseTableRecord record : negotiationTransmissionTable.getRecords()) {
                record.setStringValue(NegotiationTransmissionNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME, NegotiationTransmissionState.PROCESSING_SEND.getCode());
                negotiationTransmissionTable.updateRecord(record);
            }


        } catch (CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (CantUpdateRecordException e) {
            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "Cant get negotiation transmission record data.");

        }

    }
}
