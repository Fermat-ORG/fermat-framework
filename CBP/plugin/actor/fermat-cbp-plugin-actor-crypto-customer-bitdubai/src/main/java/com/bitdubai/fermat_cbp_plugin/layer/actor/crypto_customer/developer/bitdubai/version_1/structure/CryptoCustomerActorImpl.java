package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantClosePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateStatusPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
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

    //CONNECTED BROKERS
    @Override
    public Collection<ActorIdentity> getConnectedBrokers() {
        return null;
    }

    //RELATIONSHIP IDENTIDAD-WALLET
    public CustomerIdentityWalletRelationship createBrokerIdentityWalletRelationship(CryptoCustomerIdentity identity, UUID Wallet){
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship updateBrokerIdentityWalletRelationship(UUID RelationshipId, CryptoCustomerIdentity identity, UUID Wallet){
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship deleteBrokerIdentityWalletRelationship(UUID RelationshipId){
        return null;
    }

    @Override
    public Collection<CustomerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationships(){
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationships(UUID RelationshipId){
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationshipsByIdentity(CryptoCustomerIdentity identity){
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationshipsByWallet(UUID Wallet){
        return null;
    }

    //NEGOTIATION
    @Override
    public CustomerBrokerNegotiation createNegotiationPurchase(final ActorIdentity cryptoBroker,final Collection<Clause> clauses) throws CantCreatePurchaseNegotiationException {
        try {
            return negotiationManager.createCustomerBrokerPurchaseNegotiation(identity.getPublicKey(), cryptoBroker.getPublicKey());
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantCreatePurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public CustomerBrokerNegotiation updateNegotiationPurchase(CustomerBrokerNegotiation negotiation) throws CantUpdatePurchaseNegotiationException{
        return null;
    }

    @Override
    public CustomerBrokerNegotiation closeNegotiationPurchase(UUID negotiationId) throws CantClosePurchaseNegotiationException {
        return null;
    }

    @Override
    public CustomerBrokerNegotiation getNegotiationPurchase(final UUID negotiationId) throws CantGetPurchaseNegotiationException {
        try {
            for(CustomerBrokerNegotiation purchase : negotiationManager.getNegotiationsByCustomer(identity)){
                if(negotiationId.equals(purchase.getNegotiationId()))
                    return purchase;
            }
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, null, "", "");
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getNegotiationPurchases() throws CantGetPurchaseNegotiationException {
        try {
            HashSet<CustomerBrokerNegotiation> purchases = new HashSet<>();
            purchases.addAll(negotiationManager.getNegotiationsByCustomer(identity));
            return purchases;
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public Collection<CustomerBrokerNegotiation> getNegotiationPurchases(final NegotiationStatus status) throws CantGetPurchaseNegotiationException {
        try {
            HashSet<CustomerBrokerNegotiation> purchases = new HashSet<>();
            for(CustomerBrokerNegotiation purchase : negotiationManager.getNegotiationsByCustomer(identity)){
                if(purchase.getStatus() == status)
                    purchases.add(purchase);
            }
            return purchases;
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantGetPurchaseNegotiationException(CantCreatePurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void sendActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation){

    }

    @Override
    public void receiveActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation){

    }

    //CONTRACT
    @Override
    public CustomerBrokerContractPurchase createContractPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseContractException{
        return null;
    }

    @Override
    public CustomerBrokerContractPurchase getContractPurchase(UUID negotiationId) throws CantGetPurchaseContractException{
        return null;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getContractPurchases() throws CantGetPurchaseContractException{
        return null;
    }

    @Override
    public Collection<CustomerBrokerContractPurchase> getContractPurchases(NegotiationStatus status) throws CantGetPurchaseContractException{
        return null;
    }

    @Override
    public void sendActorNetworkServiceContractPurchases(CustomerBrokerNegotiation negotiation){

    }

    @Override
    public void receiveActorNetworkServiceContractPurchases(CustomerBrokerNegotiation negotiation){

    }

    //OTHERS
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
