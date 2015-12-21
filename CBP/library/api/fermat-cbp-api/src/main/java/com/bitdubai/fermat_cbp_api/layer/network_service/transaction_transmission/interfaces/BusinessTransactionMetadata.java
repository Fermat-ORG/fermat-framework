package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;

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
    BusinessTransactionTransactionType getType() ;

    /**
     * Get the Timestamp
     * @return Long
     */
    Long getTimestamp();

    UUID getTransactionId();

    void setBusinessTransactionTransactionType(BusinessTransactionTransactionType businessTransactionTransactionType);

    void setState(com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates transactionTransmissionStates);

    com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates getState();

    boolean isPendingToRead();

    void confirmRead();

}
