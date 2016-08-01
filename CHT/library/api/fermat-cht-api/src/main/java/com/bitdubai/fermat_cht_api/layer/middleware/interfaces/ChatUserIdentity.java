package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

/**
 * Created by franklin on 01/03/16.
 */
public interface ChatUserIdentity extends ActiveActorIdentityInformation {
    /**
     * The method <code>getPhrase</code> returns the phrase created by the intra user
     *
     * @return string phrase object
     */

    String getPhrase();

    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws
     */
    void setNewProfileImage(byte[] newProfileImage);

    /**
     * This method let an intra user sign a message with his unique private key
     *
     * @param message the message to sign
     * @return the signature
     * @throws
     */
    String createMessageSignature(String message);

    /**
     * This method let an intra user sign a message with his unique private key
     *
     * @param
     * @return the platformComponentType
     * @throws
     */
    PlatformComponentType getPlatformComponentType();
}
