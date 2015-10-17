package com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.identity.common.IdentityUserInformation;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation</code>
 * provides the method to extract information about an intra user.
 */
public interface IntraUserInformation extends IdentityUserInformation {

    /**
     * The method <code>getPublicKet</code> returns the public key of the represented Intra User
     * @return the public key of the intra user
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented intra user
     *
     * @return the name of the intra user
     */
    public String getName();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented intra user
     *
     * @return the profile image
     */
    public byte[] getProfileImage();
}
