package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerIdentityWalletRelationshipRecord;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerIdentityWalletRelationshipException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 * Modified by Yordin Alayn 11.11.2015
 */
public class CryptoCryptoCustomerIdentityWalletRelationshipImpl implements CryptoCustomerIdentityWalletRelationship {

    private final CryptoCustomerActorDatabaseDao databaseDao;

    public CryptoCryptoCustomerIdentityWalletRelationshipImpl(CryptoCustomerActorDatabaseDao databaseDao){
        this.databaseDao = databaseDao;
    }

    //RELATIONSHIP IDENTIDAD-WALLET
    public CryptoCustomerIdentityWalletRelationshipRecord createCustomerIdentityWalletRelationship(String walletPublicKey, String identityPublicKey) throws CantCreateCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.createRegisterCustomerIdentityWalletRelationship(walletPublicKey, identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantCreateCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CryptoCustomerIdentityWalletRelationshipRecord updateCustomerIdentityWalletRelationship(UUID relationshipId, String walletPublicKey, String identityPublicKey) throws CantUpdateCustomerIdentiyWalletRelationshipException {
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
    public Collection<CryptoCustomerIdentityWalletRelationshipRecord> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException {
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationships();
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CryptoCustomerIdentityWalletRelationshipRecord getAllCustomerIdentityWalletRelationships(UUID relationshipId) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationships(relationshipId);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CryptoCustomerIdentityWalletRelationshipRecord getAllCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(identityPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }

    @Override
    public CryptoCustomerIdentityWalletRelationshipRecord getAllCustomerIdentityWalletRelationshipsByWallet(String walletPublicKey) throws CantGetCustomerIdentiyWalletRelationshipException{
        try {
            return databaseDao.getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(walletPublicKey);
        } catch (CantRegisterCryptoCustomerIdentityWalletRelationshipException e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCustomerIdentiyWalletRelationshipException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET RELATIONSHIP IDENTITY WALLET CRYPTO CUSTOMER ACTOR", "");
        }
    }
}
