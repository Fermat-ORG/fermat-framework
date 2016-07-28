package com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces;

import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException;

/**
 * This interface let you access to the Translator public Information
 *
 * @author natalia on 03/08/15.
 */

public interface TranslatorIdentity {

    /**
     * Get the alias of the represented translator identity
     *
     * @return String Alias
     */
    String getAlias();

    /**
     * Get the public key of the represented developer
     *
     * @return string pulic key
     */
    String getPublicKey();

    /**
     * Sign a message with translator private key
     *
     * @param mensage to sign
     * @return string signed message
     * @throws CantSingMessageException
     */
    String createMessageSignature(String mensage) throws CantSingMessageException;

}
