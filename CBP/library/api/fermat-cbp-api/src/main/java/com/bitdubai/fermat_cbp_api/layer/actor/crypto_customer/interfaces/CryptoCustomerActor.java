package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;

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
    CustomerIdentityWalletRelationship createCustomerIdentityWalletRelationship(String WalletPublicKey, String identityPublicKey) throws com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship updateCustomerIdentityWalletRelationship(UUID RelationshipId, String WalletPublicKey, String identityPublicKey) throws com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship deleteCustomerIdentityWalletRelationship(UUID RelationshipId) throws CantDeleteCustomerIdentiyWalletRelationshipException;
    Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationships(UUID RelationshipId) throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException;
    CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByWallet(String WalletPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException;

}
