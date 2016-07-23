package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NegotiationTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantConstructNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.IncomingNotificationDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase.OutgoingNotificationDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yordin Alayn on 29.12.15.
 */
public class NegotiationTransmissionManagerImpl implements NegotiationTransmissionManager {

    private OutgoingNotificationDao outgoingNotificationDao;
    private IncomingNotificationDao incomingNotificationDao;

    private NegotiationTransmissionNetworkServicePluginRoot pluginRoot;

    ExecutorService executorService;

    public NegotiationTransmissionManagerImpl(
            OutgoingNotificationDao outgoingNotificationDao,
            IncomingNotificationDao incomingNotificationDao,
            NegotiationTransmissionNetworkServicePluginRoot pluginRoot
    ) {
        this.outgoingNotificationDao = outgoingNotificationDao;
        this.pluginRoot = pluginRoot;
        this.incomingNotificationDao = incomingNotificationDao;
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void sendNegotiationToCryptoCustomer(final NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoCustomerException {

        try {

            if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_NEW) {
                System.out.print("\n\n**** 7) MOCK NEGOTIATION TRANSACTION NEW - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO CUSTOMER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_UPDATE) {
                System.out.print("\n\n**** 9) MOCK NEGOTIATION TRANSACTION UPDATE - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO CUSTOMER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_CLOSE) {
                System.out.print("\n\n**** 9) MOCK NEGOTIATION TRANSACTION CLOSE - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO CUSTOMER ****\n");
            }

            System.out.print(new StringBuilder()
                            .append("\n--- 7/9) Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ")
                            .append(negotiationTransaction.getNegotiationId())
                            .append("\n- TransactionId = ")
                            .append(negotiationTransaction.getTransactionId())
                            .append("\n- CustomerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyCustomer())
                            .append("\n- BrokerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyBroker()).toString()
            );

            PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;
            final NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            outgoingNotificationDao.createNotification(negotiationTransmission, transmissionState);

            sendMessage(
                    negotiationTransmission.toJson(),
                    negotiationTransaction.getPublicKeyBroker(),
                    Actors.CBP_CRYPTO_BROKER,
                    negotiationTransaction.getPublicKeyCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER
            );

        } catch (CantConstructNegotiationTransmissionException e) {
            throw new CantSendNegotiationToCryptoCustomerException(CantSendNegotiationToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e) {
            throw new CantSendNegotiationToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }
    }

    @Override
    public void sendNegotiationToCryptoBroker(final NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoBrokerException {

        try {

            if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_NEW) {
                System.out.print("\n\n**** 7) MOCK NEGOTIATION TRANSACTION NEW - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO BROKER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_UPDATE) {
                System.out.print("\n\n**** 9) MOCK NEGOTIATION TRANSACTION UPDATE - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO BROKER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_CLOSE) {
                System.out.print("\n\n**** 9) MOCK NEGOTIATION TRANSACTION CLOSE - NEGOTIATION TRANSMISSION - MANAGER - SEND NEGOTIATION TO CRYPTO BROKER ****\n");
            }
            System.out.print(new StringBuilder()
                            .append("\n--- 9) Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ")
                            .append(negotiationTransaction.getNegotiationId())
                            .append("\n- TransactionId = ")
                            .append(negotiationTransaction.getTransactionId())
                            .append("\n- CustomerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyCustomer())
                            .append("\n- BrokerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyBroker()).toString()
            );

            PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;
            final NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            outgoingNotificationDao.createNotification(negotiationTransmission, transmissionState);

            sendMessage(
                    negotiationTransmission.toJson(),
                    negotiationTransaction.getPublicKeyCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER,
                    negotiationTransaction.getPublicKeyBroker(),
                    Actors.CBP_CRYPTO_BROKER
            );

        } catch (CantConstructNegotiationTransmissionException e) {
            throw new CantSendNegotiationToCryptoBrokerException(CantSendNegotiationToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e) {
            throw new CantSendNegotiationToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void sendConfirmNegotiationToCryptoCustomer(final NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoCustomerException {

        try {

            if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_NEW) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION NEW - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO CUSTOMER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_UPDATE) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION UPDATE - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO CUSTOMER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_CLOSE) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION CLOSE - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO CUSTOMER ****\n");
            }
            System.out.print(new StringBuilder()
                            .append("\n--- 24 Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ")
                            .append(negotiationTransaction.getNegotiationId())
                            .append("\n- TransactionId = ")
                            .append(negotiationTransaction.getTransactionId())
                            .append("\n- CustomerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyCustomer())
                            .append("\n- BrokerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyBroker()).toString()
            );

            PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;
            final NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            outgoingNotificationDao.createNotification(negotiationTransmission, transmissionState);

            sendMessage(
                    negotiationTransmission.toJson(),
                    negotiationTransaction.getPublicKeyBroker(),
                    Actors.CBP_CRYPTO_BROKER,
                    negotiationTransaction.getPublicKeyCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER
            );

        } catch (CantCreateNotificationException e) {
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (CantConstructNegotiationTransmissionException e) {
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e) {
            throw new CantSendConfirmToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void sendConfirmNegotiationToCryptoBroker(final NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoBrokerException {

        try {

            if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_NEW) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION NEW - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO BROKER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_UPDATE) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION UPDATE - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO BROKER ****\n");
            } else if (transactionType == NegotiationTransactionType.CUSTOMER_BROKER_CLOSE) {
                System.out.print("\n\n**** 24) MOCK NEGOTIATION TRANSACTION CLOSE - NEGOTIATION TRANSMISSION - MANAGER - SEND CONFIRMATION TO BROKER ****\n");
            }
            System.out.print(new StringBuilder()
                            .append("\n--- 24 Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ")
                            .append(negotiationTransaction.getNegotiationId())
                            .append("\n- TransactionId = ")
                            .append(negotiationTransaction.getTransactionId())
                            .append("\n- CustomerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyCustomer())
                            .append("\n- BrokerPublicKey = ")
                            .append(negotiationTransaction.getPublicKeyBroker()).toString()
            );

            PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;
            final NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            outgoingNotificationDao.createNotification(negotiationTransmission, transmissionState);

            sendMessage(
                    negotiationTransmission.toJson(),
                    negotiationTransaction.getPublicKeyCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER,
                    negotiationTransaction.getPublicKeyBroker(),
                    Actors.CBP_CRYPTO_BROKER
            );

        } catch (CantConstructNegotiationTransmissionException e) {
            throw new CantSendConfirmToCryptoBrokerException(CantSendConfirmToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e) {
            throw new CantSendConfirmToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void confirmNegotiation(final NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException {

        try {

            PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;
            final NegotiationTransmission negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            outgoingNotificationDao.createNotification(negotiationTransmission, transmissionState);

            sendMessage(
                    negotiationTransmission.toJson(),
                    negotiationTransaction.getPublicKeyCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER,
                    negotiationTransaction.getPublicKeyBroker(),
                    Actors.CBP_CRYPTO_BROKER
            );

        } catch (CantConstructNegotiationTransmissionException e) {
            throw new CantConfirmNegotiationException(CantConfirmNegotiationException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (Exception e) {
            throw new CantConfirmNegotiationException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND CONFIRM TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void confirmReception(UUID transmissionId) throws CantConfirmTransactionException {
        try {
            incomingNotificationDao.confirmReception(transmissionId);
            System.out.print("\n\n**** 19.2.1) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT, CONFIRM TRANSAMISSION ****\n");
        } catch (Exception e) {
            throw new CantConfirmTransactionException(e.getMessage(), FermatException.wrapException(e), "CAN'T CONFIRM THE RECEPTION OF NEGOTIATION TRANSMISSION", "ERROR SEND CONFIRM THE RECEPTION, UNKNOWN FAILURE.");
        }
    }

    @Override
    public List<Transaction<NegotiationTransmission>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<NegotiationTransmission>> pendingTransaction = new ArrayList<>();
        try {

//            List<NegotiationTransmission> negotiationTransmissionList = negotiationTransmissionNetworkServiceDatabaseDao.findAllByTransmissionState(NegotiationTransmissionState.PENDING_ACTION);
            List<NegotiationTransmission> negotiationTransmissionList = incomingNotificationDao.findAllByTransmissionState(NegotiationTransmissionState.PENDING_ACTION);
            if (!negotiationTransmissionList.isEmpty()) {

                for (NegotiationTransmission negotiationTransmission : negotiationTransmissionList) {
                    Transaction<NegotiationTransmission> transaction = new Transaction<>(
                            negotiationTransmission.getTransmissionId(),
                            negotiationTransmission,
                            Action.APPLY,
                            negotiationTransmission.getTimestamp());
                    pendingTransaction.add(transaction);
                }
            }
            return pendingTransaction;

        } catch (CantReadRecordDataBaseException e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS", e, "Negotiation Transmission network service", "database error");
        } catch (Exception e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS", e, "Negotiation Transmission network service", "database error");
        }
    }

    private NegotiationTransmission constructNegotiationTransmission(
            NegotiationTransaction negotiationTransaction,
            PlatformComponentType actorSendType,
            NegotiationTransactionType transactionType,
            NegotiationTransmissionType transmissionType
    ) throws CantConstructNegotiationTransmissionException {

        NegotiationTransmission negotiationTransmission = null;
        try {
            String publicKeyActorSend = null;
            String publicKeyActorReceive = null;
            PlatformComponentType actorReceiveType = null;
            Date time = new Date();

            UUID transmissionId = UUID.randomUUID();
            UUID transactionId = negotiationTransaction.getTransactionId();
            UUID negotiationId = negotiationTransaction.getTransactionId();
            NegotiationType negotiationType = negotiationTransaction.getNegotiationType();
            String negotiationXML = negotiationTransaction.getNegotiationXML();

            if (actorSendType.equals(PlatformComponentType.ACTOR_CRYPTO_CUSTOMER)) {
                publicKeyActorSend = negotiationTransaction.getPublicKeyCustomer();
                publicKeyActorReceive = negotiationTransaction.getPublicKeyBroker();
                actorReceiveType = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            } else {
                publicKeyActorSend = negotiationTransaction.getPublicKeyBroker();
                publicKeyActorReceive = negotiationTransaction.getPublicKeyCustomer();
                actorReceiveType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            }

            long timestamp = time.getTime();

            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;

            negotiationTransmission = new NegotiationTransmissionImpl(
                    transmissionId,
                    transactionId,
                    negotiationId,
                    transactionType,
                    publicKeyActorSend,
                    actorSendType,
                    publicKeyActorReceive,
                    actorReceiveType,
                    transmissionType,
                    transmissionState,
                    negotiationType,
                    negotiationXML,
                    timestamp
            );
        } catch (Exception e) {
            throw new CantConstructNegotiationTransmissionException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }
        return negotiationTransmission;

    }

    public void sendMessage(final String jsonMessage,
                            final String identityPublicKey,
                            final Actors identityType,
                            final String actorPublicKey,
                            final Actors actorType) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    ActorProfile sender = new ActorProfile();
                    sender.setActorType(identityType.getCode());
                    sender.setIdentityPublicKey(identityPublicKey);

                    ActorProfile receiver = new ActorProfile();
                    receiver.setActorType(actorType.getCode());
                    receiver.setIdentityPublicKey(actorPublicKey);

                    pluginRoot.sendNewMessage(
                            sender,
                            receiver,
                            jsonMessage
                    );
                } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }
}
