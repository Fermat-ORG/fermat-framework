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
 * Modified by Yordin Alayn 11.11.2015
 */
public class CryptoCustomerActorImpl implements CryptoCustomerActor {

    //TODO: CAMBIAR NUMEROS PRIMOS
    private static final int HASH_PRIME_NUMBER_PRODUCT = 7841;
    private static final int HASH_PRIME_NUMBER_ADD = 1831;

    private final ActorIdentity identity;
    private final CryptoCustomerActorDatabaseDao databaseDao;

    public CryptoCustomerActorImpl(final ActorIdentity identity, CryptoCustomerActorDatabaseDao databaseDao){
        this.identity = identity;
        this.databaseDao = databaseDao;
    }

    //RELATIONSHIP IDENTIDAD-WALLET
    public CustomerIdentityWalletRelationship createCustomerIdentityWalletRelationship(String walletPublicKey, String identityPublicKey) throws CantCreateCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.createRegisterCustomerIdentityWalletRelationship(walletPublicKey,identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship updateCustomerIdentityWalletRelationship(UUID relationshipId, String walletPublicKey, String identityPublicKey) throws CantUpdateCustomerIdentiyWalletRelationshipException {
        try {
            return databaseDao.updateRegisterCustomerIdentityWalletRelationship(relationshipId, walletPublicKey, identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantUpdateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T UPDATE RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantUpdateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T UPDATE RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public void deleteCustomerIdentityWalletRelationship(UUID relationshipId) throws CantDeleteCustomerIdentiyWalletRelationshipException {
        try {
            databaseDao.deleteRegisterCustomerIdentityWalletRelationship(relationshipId);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantDeleteCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T DELETE RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantDeleteCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T DELETE RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException {
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationships();
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationships(UUID relationshipId) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationships(relationshipId);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByWallet(String walletPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(walletPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
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
