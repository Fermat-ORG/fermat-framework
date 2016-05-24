package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.io.Serializable;


/**
 * Created by jorge on 28-10-2015.
 */
public interface CustomerBrokerNegotiationInformation extends NegotiationInformation, Serializable {
    ActorIdentity getCustomer();

    ActorIdentity getBroker();
}
