package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerIdentityWalletRelationshipException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoCustomerActorImpl implements CryptoCustomerActor {

    //TODO: CAMBIAR NUMEROS PRIMOS
    private static final int HASH_PRIME_NUMBER_PRODUCT = 7841;
    private static final int HASH_PRIME_NUMBER_ADD = 1831;

    private final ActorIdentity identity;
    private final CustomerBrokerPurchaseNegotiationManager negotiationManager;
    private final CryptoCustomerActorDatabaseDao databaseDao;

    public CryptoCustomerActorImpl(final ActorIdentity identity, final CustomerBrokerPurchaseNegotiationManager negotiationManager, CryptoCustomerActorDatabaseDao databaseDao){
        this.identity = identity;
        this.negotiationManager = negotiationManager;
        this.databaseDao = databaseDao;
    }

    //CONNECTED BROKERS
    @Override
    public Collection<ActorIdentity> getConnectedBrokers() {
        return null;
    }

    //RELATIONSHIP IDENTIDAD-WALLET
    public CustomerIdentityWalletRelationship createCustomerIdentityWalletRelationship(String walletPublicKey, String identityPublicKey) throws CantCreateCustomerIdentiyWalletRelationshipException{
        try {
            databaseDao.createRegisterCustomerIdentityWalletRelationship(walletPublicKey,identityPublicKey);
            return new CryptoCustomerIdentityWalletRelationshipImpl(walletPublicKey, identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO BROKER WALLET", e, "CAN'T CREATE NEW TRANSACTION CRYPTO BROKER WALLET", "");
        } catch (Exception e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO BROKER WALLET", e, "CAN'T CREATE NEW TRANSACTION CRYPTO BROKER WALLET", "");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship updateCustomerIdentityWalletRelationship(UUID RelationshipId, String WalletPublicKey, String identityPublicKey) throws CantUpdateCustomerIdentiyWalletRelationshipException {
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship deleteCustomerIdentityWalletRelationship(UUID RelationshipId) throws CantDeleteCustomerIdentiyWalletRelationshipException {
        return null;
    }

    @Override
    public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException {
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationships(UUID RelationshipId) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByWallet(String WalletPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
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
