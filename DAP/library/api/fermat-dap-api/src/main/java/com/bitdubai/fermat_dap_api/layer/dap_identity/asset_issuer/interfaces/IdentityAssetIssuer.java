package com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.identities.ActiveIdentity;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantSingMessageException;

/**
 * Created by Nerio on 07/09/15.
 * Modified by Franklin on 02/11/2015
 */
public interface IdentityAssetIssuer extends ActiveIdentity {

    /**
     * The method <code>getAlias</code> returns the alias of the represented intra user
     *
     * @return the alias of the represented intra user
     */
    String getAlias();

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented intra user
     * @return the public key of the represented intra user
     */
    String getPublicKey();

    /**
     * The method <code>getProfileImage</code> gives us the profile image of the represented intra user
     * @return the profile image of the represented intra user
     */
    byte[] getProfileImage();

    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws
     */
    void setNewProfileImage(byte[] newProfileImage);

    /**
     * This method let an intra user sign a message with his unique private key
     * @param message the message to sign
     * @return the signature
     * @throws CantSingMessageException
     */
    String createMessageSignature(String message) /*throws CantSingMessageException*/;
}
