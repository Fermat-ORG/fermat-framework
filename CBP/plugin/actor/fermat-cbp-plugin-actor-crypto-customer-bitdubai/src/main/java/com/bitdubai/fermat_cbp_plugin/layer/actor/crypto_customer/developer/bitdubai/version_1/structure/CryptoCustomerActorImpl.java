package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoCustomerActorImpl implements CryptoCustomerActor {


    @Override
    public Collection<ActorIdentity> getConnectedBrokers() {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation createPurchase(ActorIdentity cryptoBroker) throws CantCreatePurchaseException {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation getPurchase(UUID negotiationId) {
        return null;
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getPurchases() {
        return null;
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getPurchases(NegotiationStatus status) {
        return null;
    }

    @Override
    public ActorIdentity getIdentity() {
        return null;
    }
}
