package com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces;


/**
 * This interface let you access to the Developer public Information
 *
 * @author Ezequiel Postan
 */
public interface DeveloperIdentity {

    /**
     *
     * @return the alias of the represented developer identity
     */
    String getAlias();

    /**
     *
     * @return the public key of the represented developer
     */
    String getPublicKey();

    /**
     *
     * @param mensage unsigned
     * @return signed message
     */
    String createMessageSignature(String mensage) throws com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantSingMessageException;
}
