package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 04.01.16.
 */
public class CustomerBrokerNewMock implements CustomerBrokerNew {

    @Override
    public UUID getTransactionId() {
        return UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    }

    @Override
    public UUID getNegotiationId() {
        return UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    }

    @Override
    public String getPublicKeyBroker() {
        return "getPublicKeyBroker";
    }

    @Override
    public String getPublicKeyCustomer() {
        return "getPublicKeyCustomer";
    }

    @Override
    public NegotiationTransactionStatus getStatusTransaction() {
        return NegotiationTransactionStatus.PENDING_SUBMIT;
    }

    @Override
    public NegotiationType getNegotiationType() {
        return NegotiationType.PURCHASE;
    }

    @Override
    public String getNegotiationXML() {
        return null;
    }

    @Override
    public long getTimestamp() {
        Date time = new Date();
        return time.getTime();
    }

}
