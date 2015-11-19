package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantClosePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateStatusPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantSendActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantReceiveActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 11.11.2015
 */
public interface CryptoCustomerActor extends Actor {

    //CONNECTED BROKERS
    Collection<ActorIdentity> getConnectedBrokers();

    // TODO OUR IDENTITY IS NOT AN ACTOR. PLEASE CHECK IF THIS IS OK

    //RELATIONSHIP IDENTIDAD-WALLET
    CustomerIdentityWalletRelationship createCustomerIdentityWalletRelationship(String WalletPublicKey, String identityPublicKey) throws CantCreateCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship updateCustomerIdentityWalletRelationship(UUID RelationshipId, String WalletPublicKey, String identityPublicKey) throws CantUpdateCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship deleteCustomerIdentityWalletRelationship(UUID RelationshipId) throws CantDeleteCustomerIdentiyWalletRelationshipException;
    Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationships(UUID RelationshipId) throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByWallet(String WalletPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException;

    //NEGOTIATION
    CustomerBrokerNegotiation createNegotiationPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseNegotiationException;
    CustomerBrokerNegotiation updateNegotiationPurchase(CustomerBrokerNegotiation negotiation) throws CantUpdatePurchaseNegotiationException;
    CustomerBrokerNegotiation closeNegotiationPurchase(UUID negotiationId) throws CantClosePurchaseNegotiationException;
    CustomerBrokerNegotiation getNegotiationPurchase(UUID negotiationId) throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases() throws CantGetPurchaseNegotiationException;
    Collection<CustomerBrokerNegotiation> getNegotiationPurchases(NegotiationStatus status) throws CantGetPurchaseNegotiationException;
    void sendActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation) throws CantSendActorNetworkServiceException;
    void receiveActorNetworkServiceNegotiationPurchases(CustomerBrokerNegotiation negotiation) throws CantReceiveActorNetworkServiceException;

    //CONTRACT
    CustomerBrokerContractPurchase createContractPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseContractException;
    CustomerBrokerContractPurchase updateContractPurchase(UUID contractId) throws CantUpdateStatusPurchaseContractException;
    CustomerBrokerContractPurchase getContractPurchase(UUID negotiationId) throws CantGetPurchaseContractException;
    Collection<CustomerBrokerContractPurchase> getContractPurchases() throws CantGetPurchaseContractException;
    Collection<CustomerBrokerContractPurchase> getContractPurchases(ContractStatus status) throws CantGetPurchaseContractException;
    void sendActorNetworkServiceContractPurchases(CustomerBrokerContractPurchase contract) throws CantSendActorNetworkServiceException;
    void receiveActorNetworkServiceContractPurchases(CustomerBrokerContractPurchase contract) throws CantReceiveActorNetworkServiceException;

}
