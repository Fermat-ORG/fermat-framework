package com.bitdubai.fermat_api.layer.identity.common;

/**
 * Created by Matias Furszyfer on 2015.10.15..
 */
public interface IdentityUserInformation {

    /**
     * The method <code>getPublicKet</code> returns the public key of the represented Intra User
     *
     * @return the public key of the intra user
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented intra user
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented intra user
     *
     * @return the profile image
     */
    byte[] getProfileImage();


}
