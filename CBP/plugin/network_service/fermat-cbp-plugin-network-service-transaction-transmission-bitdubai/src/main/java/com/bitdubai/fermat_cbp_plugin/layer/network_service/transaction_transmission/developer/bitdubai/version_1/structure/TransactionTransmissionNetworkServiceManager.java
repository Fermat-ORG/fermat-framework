package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantGetTransactionTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class TransactionTransmissionNetworkServiceManager implements TransactionTransmissionManager {

    private final TransactionTransmissionNetworkServicePluginRoot pluginRoot;
    private final ErrorManager errorManager;
    private final TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;
    private final Gson gson;

    private final ExecutorService executorService;

    public TransactionTransmissionNetworkServiceManager(final TransactionTransmissionNetworkServicePluginRoot pluginRoot,
                                                        final ErrorManager errorManager,
                                                        final TransactionTransmissionContractHashDao transactionTransmissionContractHashDao) {

        this.pluginRoot = pluginRoot;
        this.errorManager = errorManager;
        this.transactionTransmissionContractHashDao = transactionTransmissionContractHashDao;
        this.gson = new Gson();

        this.executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void sendContractHash(
            UUID transactionId,
            String cryptoBrokerActorSenderPublicKey,
            String cryptoCustomerActorReceiverPublicKey,
            String transactionHash,
            String negotiationId,
            Plugins remoteBusinessTransaction,
            PlatformComponentType senderComponent,
            PlatformComponentType receiverComponent) throws CantSendBusinessTransactionHashException {

        //TODO: Check is contractId is necessary
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        BusinessTransactionMetadata businessTransactionMetadata = new BusinessTransactionMetadataRecord(
                transactionHash,
                ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION,
                cryptoBrokerActorSenderPublicKey,
                receiverComponent,
                cryptoCustomerActorReceiverPublicKey,
                senderComponent,
                null,
                negotiationId,
                BusinessTransactionTransactionType.TRANSACTION_HASH,
                timestamp.getTime(),
                transactionId,
                TransactionTransmissionStates.PRE_PROCESSING_SEND,
                remoteBusinessTransaction
        );
        try {
            System.out.print("\nTEST CONTRACT - NS - TRANSACTION TRANSMISSION - MANAGER - sendContractHash()\n");
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

            sendMessage(businessTransactionMetadata);

            /*businessTransactionMetadata.confirmRead();
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
            */

        } catch (Exception e) {
            throw new CantSendBusinessTransactionHashException(e,
                    "Cannot persists the contract hash in table",
                    "Unhandled Exception.");
        }

    }

    @Override
    public void sendContractStatusNotification(
            String senderPublicKey,
            String receiverPublicKey,
            String transactionHash,
            String transactionId,
            ContractTransactionStatus contractStatus,
            Plugins remoteBusinessTransaction,
            PlatformComponentType senderComponent,
            PlatformComponentType receiverComponent) throws CantSendContractNewStatusNotificationException {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        UUID uuidTransactionId = UUID.fromString(transactionId);

        BusinessTransactionMetadata businessTransactionMetadata = new BusinessTransactionMetadataRecord(
                transactionHash,
                contractStatus,
                senderPublicKey,
                receiverComponent,
                receiverPublicKey,
                senderComponent,
                null,
                null,
                BusinessTransactionTransactionType.CONTRACT_STATUS_UPDATE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.PRE_PROCESSING_SEND,
                remoteBusinessTransaction);

        try {
            System.out.print("\nTEST CONTRACT - NS - TRANSACTION TRANSMISSION - MANAGER - sendContractStatusNotification()" +remoteBusinessTransaction+"\n");
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

            sendMessage(businessTransactionMetadata);

        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendContractNewStatusNotificationException(
                    CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                    e,
                    "Cannot persists the contract hash in table",
                    "database corrupted");
        } catch (Exception e) {
            throw new CantSendContractNewStatusNotificationException(
                    CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Unhandled Exception.");
        }

    }

    @Override
    public void confirmNotificationReception(
            String senderPublicKey,
            String receiverPublicKey,
            String contractHash,
            String transactionId,
            Plugins remoteBusinessTransaction,
            PlatformComponentType senderComponent,
            PlatformComponentType receiverComponent) throws CantConfirmNotificationReceptionException {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        UUID uuidTransactionId = UUID.fromString(transactionId);

        //TODO: verificar los parametros del businessTransactionMetadata
        BusinessTransactionMetadata businessTransactionMetadata = new BusinessTransactionMetadataRecord(
                contractHash,
                ContractTransactionStatus.NOTIFICATION_CONFIRMED,
                senderPublicKey,
                receiverComponent,
                receiverPublicKey,
                senderComponent,
                null,
                null,
                BusinessTransactionTransactionType.CONFIRM_MESSAGE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.PRE_PROCESSING_SEND,
                remoteBusinessTransaction
        );

        try {
            System.out.print("\nTEST CONTRACT - NS - TRANSACTION TRANSMISSION - MANAGER - confirmNotificationReception()\n");
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

            sendMessage(businessTransactionMetadata);

        } catch (CantInsertRecordDataBaseException e) {
            throw new CantConfirmNotificationReceptionException(
                    CantConfirmNotificationReceptionException.DEFAULT_MESSAGE,
                    e,
                    "Cannot persists the contract hash in table",
                    "database corrupted");
        } catch (Exception e) {
                throw new CantConfirmNotificationReceptionException(
                    CantConfirmNotificationReceptionException.DEFAULT_MESSAGE,
                    e,
                    "Cannot persists the contract hash in table",
                    "Unhandled Exception.");
        }
    }


    public void ackConfirmNotificationReception(
            String cryptoBrokerActorSenderPublicKey,
            String cryptoCustomerActorReceiverPublicKey,
            String contractHash,
            String transactionId,
            Plugins remoteBusinessTransaction,
            PlatformComponentType senderComponent,
            PlatformComponentType receiverComponent) throws CantConfirmNotificationReceptionException {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        UUID uuidTransactionId = UUID.fromString(transactionId);

        //TODO: verificar los parametros del businessTransactionMetadata
        BusinessTransactionMetadata businessTransactionMetadata = new BusinessTransactionMetadataRecord(
                contractHash,
                ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED,
                cryptoBrokerActorSenderPublicKey,
                receiverComponent,
                cryptoCustomerActorReceiverPublicKey,
                senderComponent,
                null,
                null,
                BusinessTransactionTransactionType.ACK_CONFIRM_MESSAGE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.PRE_PROCESSING_SEND,
                remoteBusinessTransaction
        );

        try {
            System.out.print("\nTEST CONTRACT - NS - TRANSACTION TRANSMISSION - MANAGER - ackConfirmNotificationReception()\n");
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);

            sendMessage(businessTransactionMetadata);

        } catch (CantInsertRecordDataBaseException e) {
            throw new CantConfirmNotificationReceptionException(
                    CantConfirmNotificationReceptionException.DEFAULT_MESSAGE,
                    e,
                    "Cannot persists the contract hash in table",
                    "database corrupted");
        } catch (Exception e) {
            throw new CantConfirmNotificationReceptionException(
                    CantConfirmNotificationReceptionException.DEFAULT_MESSAGE,
                    e,
                    "Cannot persists the contract hash in table",
                    "Unhandled Exception.");
        }
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {
            System.out.print("\n1)transactionId: "+ transactionID+"\n");
            this.transactionTransmissionContractHashDao.confirmReception(transactionID);

        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantConfirmTransactionException(
                    CantUpdateRecordDataBaseException.DEFAULT_MESSAGE,
                    e,
                    "Confirm reception",
                    "Cannot update the flag in database");
        } catch (PendingRequestNotFoundException e) {
            throw new CantConfirmTransactionException(
                    PendingRequestNotFoundException.DEFAULT_MESSAGE,
                    e, "Confirm reception",
                    "Cannot find the transaction id in database\n" + transactionID);
        } catch (CantGetTransactionTransmissionException e) {
            throw new CantConfirmTransactionException(
                    CantGetTransactionTransmissionException.DEFAULT_MESSAGE,
                    e,
                    "Confirm reception",
                    "Cannot get the business transaction record from the database");
        } catch (Exception e) {
            throw new CantConfirmTransactionException(
                    CantConfirmTransactionException.DEFAULT_MESSAGE,
                    e,
                    "Confirm reception",
                    "Cannot get the business transaction record from the database");
        }
    }

    @Override
    public List<Transaction<BusinessTransactionMetadata>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<BusinessTransactionMetadata>> pendingTransaction = new ArrayList<>();
        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, "false");

            List<BusinessTransactionMetadata> businessTransactionMetadataList = transactionTransmissionContractHashDao.findAllToReceive(filters);
            if (!businessTransactionMetadataList.isEmpty()) {

                for (BusinessTransactionMetadata businessTransactionMetadata : businessTransactionMetadataList) {
                    Transaction<BusinessTransactionMetadata> transaction = new Transaction<>(
                            businessTransactionMetadata.getTransactionId(),
                            businessTransactionMetadata,
                            Action.APPLY,
                            businessTransactionMetadata.getTimestamp());

                    pendingTransaction.add(transaction);
                }

            }
            return pendingTransaction;

        } catch (CantReadRecordDataBaseException e) {
            throw new CantDeliverPendingTransactionsException(
                    "CAN'T GET PENDING METADATA NOTIFICATIONS",
                    e,
                    "Transaction Transmission network service",
                    "database error");
        } catch (Exception e) {
            throw new CantDeliverPendingTransactionsException(
                    "CAN'T GET PENDING METADATA NOTIFICATIONS",
                    e,
                    "Transaction Transmission network service",
                    "Unhandled Exception.");

        }
    }

    public void sendMessage(final BusinessTransactionMetadata metadata) {

        final PlatformComponentProfile senderProfile = pluginRoot.getProfileSenderToRequestConnection(metadata.getSenderId(),
                NetworkServiceType.TRANSACTION_TRANSMISSION, metadata.getSenderType());

        final PlatformComponentProfile destinationProfile = pluginRoot.getProfileDestinationToRequestConnection(metadata.getReceiverId(),
                NetworkServiceType.TRANSACTION_TRANSMISSION, metadata.getReceiverType());

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pluginRoot.sendNewMessage(senderProfile, destinationProfile, gson.toJson(metadata));
                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(pluginRoot.getPluginVersionReference(),
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }
}
