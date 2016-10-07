package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface BusinessTransactionMetadata {

    /**
     * Get the Contract Hash
     *
     * @return String
     */
    String getContractHash();

    /**
     * Get the Distribution Status
     *
     * @return ContractStatus
     */
    ContractTransactionStatus getContractTransactionStatus();

    /**
     * Get the Receiver Id
     *
     * @return String
     */
    String getReceiverId();

    /**
     * The platform component that this event is destinated for.
     *
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getReceiverType();

    /**
     * Get the Sender Id
     *
     * @return String
     */
    String getSenderId();

    /**
     * The platform component that send this event.
     *
     * @return {@link PlatformComponentType}
     */
    PlatformComponentType getSenderType();

    /**
     * Get the Contract Id
     *
     * @return String
     */
    String getContractId();

    /**
     * Get the Negotiation Id
     *
     * @return String
     */
    String getNegotiationId();

    /**
     * Get the Business Transaction Transaction Type
     *
     * @return BusinessTransactionTransactionType
     */
    BusinessTransactionTransactionType getType();

    /**
     * Get the Timestamp
     *
     * @return Long
     */
    Long getTimestamp();

    /**
     * This method returns the transaction id
     *
     * @return
     */
    UUID getTransactionId();

    /**
     * This method returns the transaction contract id
     *
     * @return
     */
    UUID getTransactionContractId();

    /**
     * This method sets the BusinessTransactionTransactionType
     *
     * @param businessTransactionTransactionType
     */
    void setBusinessTransactionTransactionType(BusinessTransactionTransactionType businessTransactionTransactionType);

    /**
     * This method returns the TransactionTransmissionStates
     *
     * @param transactionTransmissionStates
     */
    void setState(TransactionTransmissionStates transactionTransmissionStates);

    /**
     * This method returns the TransactionTransmissionStates
     *
     * @return
     */
    TransactionTransmissionStates getState();

    /**
     * This method returns the parameter isPendingToRead
     *
     * @return
     */
    boolean isPendingToRead();

    /**
     * This method sets the parameter isPendingToRead
     */
    void confirmRead();

    /**
     * This method sets the Remote Business Transaction.
     *
     * @return
     */
    Plugins getRemoteBusinessTransaction();

    /**
     * This method returns the Remote Business Transaction.
     */
    void setRemoteBusinessTransaction(Plugins remoteBusinessTransaction);

}
