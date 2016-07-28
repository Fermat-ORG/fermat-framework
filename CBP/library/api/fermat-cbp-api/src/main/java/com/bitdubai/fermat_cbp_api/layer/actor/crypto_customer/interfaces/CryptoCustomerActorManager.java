package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantClearAssociatedCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException;

import java.util.Collection;

/**
 * Created by Angel 17-11-15
 */
public interface CryptoCustomerActorManager extends FermatManager {

    /**
     * @param identity
     * @param wallet
     * @return
     * @throws CantCreateNewCustomerIdentityWalletRelationshipException
     */
    CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(ActorIdentity identity, String wallet) throws CantCreateNewCustomerIdentityWalletRelationshipException;

    /**
     * @param wallet
     * @return
     * @throws CantClearAssociatedCustomerIdentityWalletRelationshipException
     */
    void clearAssociatedCustomerIdentityWalletRelationship(String wallet) throws CantClearAssociatedCustomerIdentityWalletRelationshipException;

    /**
     * @return
     * @throws CantGetCustomerIdentityWalletRelationshipException
     */
    Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationship() throws CantGetCustomerIdentityWalletRelationshipException;

    /**
     * @param publicKey
     * @return
     * @throws CantGetCustomerIdentityWalletRelationshipException
     */
    CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetCustomerIdentityWalletRelationshipException;

    /**
     * Through this method you can get the relationship between a crypto customer wallet and a crypto customer identity having in coung the crypto customer wallet public key.
     *
     * @param walletPublicKey of the wallet in where we're looking the relationship.
     * @return an instance of CustomerIdentityWalletRelationship object with the information of the relationship.
     * @throws CantGetCustomerIdentityWalletRelationshipException if something goes wrong.
     * @throws RelationshipNotFoundException                      if we can't find a relationship for this wallet.
     */
    CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(

            String walletPublicKey

    ) throws CantGetCustomerIdentityWalletRelationshipException,
            RelationshipNotFoundException;

}
