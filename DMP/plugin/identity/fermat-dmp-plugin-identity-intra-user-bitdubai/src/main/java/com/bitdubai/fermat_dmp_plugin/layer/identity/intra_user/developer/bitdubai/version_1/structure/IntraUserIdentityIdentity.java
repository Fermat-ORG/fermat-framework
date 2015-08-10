package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSetNewProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSingIntraUserMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity</code>
 * implements the functionality of an IntraUserIdentity.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserIdentityIdentity implements IntraUserIdentity {

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public byte[] getProfileImage() throws CantShowProfileImageException {
        return new byte[0];
    }

    @Override
    public String createMessageSignature(String message) throws CantSingIntraUserMessageException {
        return null;
    }

    /**
     * Set a new profile image for an Intra-User Identity
     *
     * Overwrite the saved profile image of the user.
     *
     * The name of the profileImage file is defined like "$publicKey_profileImage"
     *
     * @param newProfileImage the new profile image to set
     * @throws CantSetNewProfileImageException
     */
    @Override
    public void setNewProfileImage(byte[] newProfileImage) throws CantSetNewProfileImageException {

    }

}