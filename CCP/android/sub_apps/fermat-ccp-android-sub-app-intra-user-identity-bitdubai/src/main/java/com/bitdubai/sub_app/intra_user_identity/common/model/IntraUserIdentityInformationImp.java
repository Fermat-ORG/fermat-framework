package com.bitdubai.sub_app.intra_user_identity.common.model;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantSetNewProfileImageException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantSignIntraWalletUserMessageException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;

/**
 * Created by nelson on 09/10/15.
 */
public class IntraUserIdentityInformationImp implements IntraWalletUser {

    private String intraUserName;
    private byte[] profileImage;
    private String publicKey;

    public IntraUserIdentityInformationImp(String intraUserName, String publicKey,byte[] profileImage) {
        this.intraUserName = intraUserName;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
    }

    @Override
    public String getAlias() {
        return this.intraUserName ;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }



    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage)  {

    }

    @Override
    public String createMessageSignature(String message) {
        return null;
    }
}
