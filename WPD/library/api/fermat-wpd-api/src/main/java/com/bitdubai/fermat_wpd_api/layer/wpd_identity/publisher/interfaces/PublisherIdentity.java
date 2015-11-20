package com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantSingMessageException;

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
     * @return the URL SITE  of the represented Publisher
     */
    String getWebsiteurl();
    /**
     *
     * @param mensage unsigned
     * @return signed message
     */
    String createMessageSignature(String mensage) throws CantSingMessageException;
}
