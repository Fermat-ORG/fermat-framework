package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;

/**
 *The Class <code>CryptoCustomerActorManager</code>
 * indicates the functionality of a CryptoCustomerActorManager
 * <p/>
 * Created by jorge on 26-10-2015.
 * Updated by Yordin Alayn 21.11.2015.
 */
public interface CryptoCustomerActorManager extends FermatManager{

    /**
     * Throw the method <code>createNewCryptoCustomerActor</code> we can create a new actor crypto customer.
     *
     * @param identityPublicKey assigned to the new actor
     * @param actorName assigned to the new actor
     * @param actorPhoto assigned to the new actor
     * @return an instance of the new actor created.
     * @throws CantCreateCryptoCustomerActorException if something goes wrong
     */
    CryptoCustomerActor createNewCryptoCustomerActor(String identityPublicKey, String actorName, byte[] actorPhoto) throws CantCreateCryptoCustomerActorException;

    /**
     * Throw the method <code>createActor</code> we can create a new user (this, without picture).
     *
     * @param actorPublicKey assigned to the new actor
     * @return an instance of the actor we're looking for
     * @throws CantGetCryptoCustomerActorException if something goes wrong
     */
    CryptoCustomerActor getCryptoCustomerActor(String actorPublicKey) throws CantGetCryptoCustomerActorException;
}
