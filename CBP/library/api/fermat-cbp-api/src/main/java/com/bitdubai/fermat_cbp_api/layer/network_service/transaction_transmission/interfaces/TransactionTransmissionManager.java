package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;

import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface TransactionTransmissionManager extends FermatManager, TransactionProtocolManager<BusinessTransactionMetadata> {

    /**
     * Method that send Contract hash
     * @param transactionId
     * @param senderPublicKey
     * @param receiverPublicKey
     * @param negotiationId
     * @throws CantSendBusinessTransactionHashException
     */
    void sendContractHash(
            UUID transactionId,
            String senderPublicKey,
            String receiverPublicKey,
            String contractHash,
            String negotiationId)  throws CantSendBusinessTransactionHashException;

    /**
     * Method that send the a Contract New Status Notification
     * @param senderPublicKey
     * @param receiverPublicKey
     * @param transactionHash
     * @param transactionId
     * @param contractStatus
     * @throws CantSendContractNewStatusNotificationException
     */
    void sendContractStatusNotification(
            String senderPublicKey,
            String receiverPublicKey,
            String transactionHash,
            String transactionId,
            ContractTransactionStatus contractStatus) throws CantSendContractNewStatusNotificationException;

    /**
     * Method that send the Contract New Status Notification
     *
     * @param transactionId
     */
    void confirmNotificationReception(String cryptoBrokerActorSenderPublicKey, String cryptoCustomerActorReceiverPublicKey, String contractHash, String transactionId) throws com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReception;

}
