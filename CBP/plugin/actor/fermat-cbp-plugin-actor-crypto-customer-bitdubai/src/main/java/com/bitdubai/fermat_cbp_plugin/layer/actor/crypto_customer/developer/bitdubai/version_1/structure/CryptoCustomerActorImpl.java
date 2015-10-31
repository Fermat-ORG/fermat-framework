package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantListPurchaseNegotianionsException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoCustomerActorImpl implements CryptoCustomerActor {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 7841;
    private static final int HASH_PRIME_NUMBER_ADD = 1831;

    private final ActorIdentity identity;
    private final CustomerBrokerPurchaseNegotiationManager negotiationManager;

    public CryptoCustomerActorImpl(final ActorIdentity identity, final CustomerBrokerPurchaseNegotiationManager negotiationManager){
        this.identity = identity;
        this.negotiationManager = negotiationManager;
    }

    @Override
    public Collection<ActorIdentity> getConnectedBrokers() {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation createPurchase(final ActorIdentity cryptoBroker) throws CantCreatePurchaseNegotiationException {
        try {
            return negotiationManager.createCustomerBrokerPurchaseNegotiation(identity.getPublicKey(), cryptoBroker.getPublicKey());
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantCreatePurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiation getPurchase(final UUID negotiationId) throws CantGetPurchaseNegotiationException {
        try {
            for(CustomerBrokerNegotiation purchase : negotiationManager.getNegotiationsByCustomer(identity)){
                if(negotiationId.equals(purchase.getNegotiationId()))
                    return purchase;
            }
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, null, "", "");
        } catch (CantListPurchaseNegotianionsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getPurchases() throws CantGetPurchaseNegotiationException {
        try {
            HashSet<CustomerBrokerNegotiation> purchases = new HashSet<>();
            purchases.addAll(negotiationManager.getNegotiationsByCustomer(identity));
            return purchases;
        } catch (CantListPurchaseNegotianionsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getPurchases(final NegotiationStatus status) throws CantGetPurchaseNegotiationException {
        try {
            HashSet<CustomerBrokerNegotiation> purchases = new HashSet<>();
            for(CustomerBrokerNegotiation purchase : negotiationManager.getNegotiationsByCustomer(identity)){
                if(purchase.getStatus() == status)
                    purchases.add(purchase);
            }
            return purchases;
        } catch (CantListPurchaseNegotianionsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public ActorIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean equals(final Object o){
        if(!(o instanceof CryptoCustomerActor))
            return false;
        CryptoCustomerActor compare = (CryptoCustomerActor) o;
        return this.identity.equals(compare.getIdentity());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += identity.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
