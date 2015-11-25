package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendContractNewStatusNotificationException;

import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface TransactionTransmissionManager extends TransactionProtocolManager<BusinessTransaction> {
    /**
     * Method that send Contract hash
     *
     * @param cryptoBrokerActorSender
     * @param cryptoCustomerActorReceiver
     * @param transactionHash
     * @param negotiationId
     * @throws CantSendContractNewStatusNotificationException
     */
    void sendContractHashToCryptoCustomer(UUID transactionId, CryptoBrokerActor cryptoBrokerActorSender, CryptoCustomerActor cryptoCustomerActorReceiver, String transactionHash, String negotiationId) throws CantSendBusinessTransactionHashException;

    /**
     * Method that send Contract hash
     *
     * @param cryptoCustomerActorSender
     * @param cryptoCustomerBrokerReceiver
     * @param transactionHash
     * @throws CantSendContractNewStatusNotificationException
     */
    void sendContractHashToCryptoBroker(UUID transactionId, CryptoCustomerActor cryptoCustomerActorSender, CryptoBrokerActor cryptoCustomerBrokerReceiver, String transactionHash, String negotiationId) throws CantSendBusinessTransactionHashException;


    /**
     * Method that send the Contract New Status Notification
     *
     * @param transactionId
     * @param contractStatus
     */
    void sendContractNewStatusNotification(CryptoBrokerActor cryptoBrokerActorSender, CryptoCustomerActor cryptoCustomerActorReceiver, String transactionId, ContractStatus contractStatus) throws CantSendBusinessTransactionHashException;

    /**
     * Method that send the a Contract New Status Notification
     *
     * @param transactionId
     * @param contractStatus
     */
    void sendTransactionNewStatusNotification(CryptoCustomerActor cryptoCustomerActorSender, CryptoBrokerActor cryptoCustomerBrokerReceiver, String transactionId, ContractStatus contractStatus) throws CantSendBusinessTransactionHashException;

    /**
     * Method that send the Contract New Status Notification
     *
     * @param transactionId
     */
    void confirmNotificationReception(CryptoBrokerActor cryptoBrokerActorSender, CryptoCustomerActor cryptoCustomerActorReceiver, String transactionId) throws CantSendBusinessTransactionHashException;

    /**
     * Method that send the a confirm answer
     *
     * @param transactionId
     */
    void confirmNotificationReception(CryptoCustomerActor cryptoCustomerActorSender, CryptoBrokerActor cryptoCustomerBrokerReceiver, String transactionId) throws CantSendBusinessTransactionHashException;


}
