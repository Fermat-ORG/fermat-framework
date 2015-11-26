package com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public interface NegotiationTransaction {

    UUID getTransactionId();

    UUID getNegotiationId();

    String getPublicKeyBroker();

    String getPublicKeyCustomer();

    NegotiationStatus getStatusTransaction();

    long getTimestamp();
}
