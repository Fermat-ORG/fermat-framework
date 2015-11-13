package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantSendActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantReceiveActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateStatusPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 11.11.2015
 */
public interface CryptoCustomerActor extends Actor {

    Collection<ActorIdentity> getConnectedBrokers();

    CustomerIdentityWalletRelationship createBrokerIdentityWalletRelationship(CryptoCustomerIdentity identity, UUID Wallet);
    CustomerIdentityWalletRelationship updateBrokerIdentityWalletRelationship(UUID RelationshipId, CryptoCustomerIdentity identity, UUID Wallet);
    CustomerIdentityWalletRelationship deleteBrokerIdentityWalletRelationship(UUID RelationshipId);
    Collection<CustomerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationships();
    CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationships(UUID RelationshipId);
    CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationshipsByIdentity(CryptoCustomerIdentity identity);
    CustomerIdentityWalletRelationship getAllBrokerIdentityWalletRelationshipsByWallet(UUID Wallet);

    CustomerBrokerNegotiation createNegotiationPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseNegotiationException;
    CustomerBrokerNegotiation getNegotiationPurchase(UUID negotiationId) throws CantGetPurchaseNegotiationException;
    CustomerBrokerNegotiation updateNegotiationPurchase(CustomerBrokerNegotiation negotiation) throws CantUpdatePurchaseNegotiationException;
    CustomerBrokerNegotiation closeNegotiationPurchase(UUID negotiationId) throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases() throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases(NegotiationStatus status) throws CantGetPurchaseNegotiationException;
    void sendActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation) throws CantSendActorNetworkServiceException;
    void receiveActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation) throws CantReceiveActorNetworkServiceException;

    CustomerBrokerPurchase createContractPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseContractException;
    CustomerBrokerPurchase getContractPurchase(UUID negotiationId) throws CantGetPurchaseContractException;
    CustomerBrokerPurchase updateStatusContractPurchase(UUID negotiationId) throws CantUpdateStatusPurchaseContractException;
    Collection<CustomerBrokerPurchase> getContractPurchases() throws CantGetPurchaseContractException;
    Collection<CustomerBrokerPurchase> getContractPurchases(NegotiationStatus status) throws CantGetPurchaseContractException;
    void sendActorNetworkServiceContractPurchases(CustomerBrokerNegotiation negotiation) throws CantSendActorNetworkServiceException;
    void receiveActorNetworkServiceContractPurchases(CustomerBrokerNegotiation negotiation) throws CantReceiveActorNetworkServiceException;

}
