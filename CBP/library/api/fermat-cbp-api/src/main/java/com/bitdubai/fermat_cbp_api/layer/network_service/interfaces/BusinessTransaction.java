package com.bitdubai.fermat_cbp_api.layer.network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.enums.BusinessTransactionTransactionType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface BusinessTransaction {

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
    ContractStatus getContractStatus();

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
}
