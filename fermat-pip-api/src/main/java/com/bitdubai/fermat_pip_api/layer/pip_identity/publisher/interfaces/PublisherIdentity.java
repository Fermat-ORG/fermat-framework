package com.bitdubai.fermat_pip_api.layer.pip_identity.publisher.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_identity.publisher.exceptions.CantSingMessageException;

/**
 *  This interface let you access to the Publisher public Information
 *
 * Created by Nerio on 13/08/15.
 */
public interface PublisherIdentity {

    /**
     *
     * @return the alias of the represented Publisher identity
     */
    String getAlias();

    /**
     *
     * @return the public key of the represented Publisher
     */
    String getPublicKey();

    /**
     *
     * @param mensage unsigned
     * @return signed message
     */
    String createMessageSignature(String mensage) throws CantSingMessageException;
}
