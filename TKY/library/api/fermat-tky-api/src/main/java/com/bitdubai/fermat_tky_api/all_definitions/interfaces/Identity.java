package com.bitdubai.fermat_tky_api.all_definitions.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;

import java.util.UUID;

/**
 * Created by GAbriel Araujo 16/03/16.
 */
public interface Identity {

    /**
     * This method returns the identity alias.
     * @return
     */
    String getAlias();

    /**
     * This method returns the identity public key.
     * @return
     */
    UUID getId();

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
    String getExternalUsername();

    /**
     * This method returns the external access token
     * @return
     */
    String getExternalAccesToken();

    /**
     * This method returns the external platform that the identity uses to redeem tokens.
     * @return
     */
    ExternalPlatform getExternalPlatform();

}
