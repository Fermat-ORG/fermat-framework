package org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantSingMessageException;

import java.io.Serializable;

/**
 * Created by Nerio on 07/09/15.
 * Modified by Franklin on 03/11/2015
 */
public interface IdentityAssetUser extends ActiveActorIdentityInformation, Serializable {

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
    String createMessageSignature(String message) ;
}
