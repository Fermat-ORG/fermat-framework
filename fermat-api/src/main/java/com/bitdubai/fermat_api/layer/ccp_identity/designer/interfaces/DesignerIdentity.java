package com.bitdubai.fermat_api.layer.ccp_identity.designer.interfaces;

import com.bitdubai.fermat_api.layer.ccp_identity.designer.exceptions.CantSingMessageException;

/**
 * This interface let you access to the Designer public Information
 *
 * @author natalia on 03/08/15.
 */

public interface DesignerIdentity {

    /**
     * Get the alias of the represented designer identity
     * @return String alias
     */
    public String getAlias();

    /**
     * Get the public key of the represented designer
     * @return String publicKey
     */
    public String getPublicKey();

    /**
     Sign a message with designer private key
     * @param  mensage to sign
     * @return string signed message
     * @throws CantSingMessageException
     */

    public String createMessageSignature(String mensage) throws CantSingMessageException;

}
