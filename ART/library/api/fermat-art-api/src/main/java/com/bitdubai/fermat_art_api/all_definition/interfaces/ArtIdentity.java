package com.bitdubai.fermat_art_api.all_definition.interfaces;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface ArtIdentity extends Serializable {

    String getAlias();
    /**
     * This method returns the identity public key.
     * @return
     */
    String getPublicKey();

    /**
     * This method returns the profile image.
     * @return
     */
    byte[] getProfileImage();

    /**
     * This method saves a profile image
     * @param imageBytes
     */
    void setNewProfileImage(final byte[] imageBytes);

    /**
     * This method returns the external platform username
     * @return
     */

    UUID getExternalIdentityID();

    /**
     * This method returns the external platform that the identity uses to redeem tokens.
     * @return
     */
    ArtExternalPlatform getExternalPlatform();

}
