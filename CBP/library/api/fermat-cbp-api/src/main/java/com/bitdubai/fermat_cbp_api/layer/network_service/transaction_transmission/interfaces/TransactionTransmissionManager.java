package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;

import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface TransactionTransmissionManager extends FermatManager, TransactionProtocolManager<BusinessTransactionMetadata> {
    /**
     * Method that send Contract hash
     *
     * @param cryptoBrokerActorSenderPublicKey
     * @param cryptoCustomerActorReceiverPublicKey
     * @param transactionHash
     * @param negotiationId
     * @throws com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException
     */
    void sendContractHashToCryptoCustomer(UUID transactionId, String cryptoBrokerActorSenderPublicKey, String cryptoCustomerActorReceiverPublicKey, String transactionHash, String negotiationId) throws CantSendBusinessTransactionHashException;

    /**
     * Method that send Contract hash
     *
     * @param negotiationId
     * @param cryptoCustomerActorSenderPublicKey
     * @param cryptoCustomerBrokerReceiverPublicKey
     * @param transactionHash   @throws CantSendContractNewStatusNotificationException
     */
    void sendContractHashToCryptoBroker(UUID transactionId, String cryptoCustomerActorSenderPublicKey, String cryptoCustomerBrokerReceiverPublicKey, String transactionHash, String negotiationId) throws CantSendBusinessTransactionHashException;


    /**
     * Method that send the Contract New Status Notification
     *
     * @param transactionId
     * @param contractStatus
     */
    void sendContractStatusNotificationToCryptoCustomer(String cryptoBrokerActorSenderPublicKey, String cryptoCustomerActorReceiverPublicKey, String transactionHash, String transactionId, ContractTransactionStatus contractStatus) throws com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;

    /**
     * Method that send the a Contract New Status Notification
     *
     * @param transactionId
     * @param contractStatus
     */
    void sendContractStatusNotificationToCryptoBroker(String cryptoCustomerActorSenderPublicKey, String cryptoCustomerBrokerReceiverPublicKey, String transactionHash, String transactionId, ContractTransactionStatus contractStatus) throws com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;

    /**
     * Method that send the Contract New Status Notification
     *
     * @param transactionId
     */
    void confirmNotificationReception(String cryptoBrokerActorSenderPublicKey, String cryptoCustomerActorReceiverPublicKey, String contractHash, String transactionId) throws com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReception;

}
