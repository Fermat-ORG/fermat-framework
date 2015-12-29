package com.bitdubai.sub_app.intra_user_identity.common.model;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PhotoType;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;

/**
 * Created by nelson on 09/10/15.
 */
public class IntraUserIdentityInformationImp implements IntraWalletUserIdentity {

    private String intraUserName;
    private String intraUserPhrase;
    private byte[] profileImage;
    private String publicKey;
    private PhotoType photoType;

    public IntraUserIdentityInformationImp(String intraUserName, String intraUserPhrase,String publicKey,byte[] profileImage,PhotoType photoType) {
        this.intraUserName = intraUserName;
        this.intraUserPhrase = intraUserPhrase;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.photoType = photoType;
    }

    @Override
    public String getAlias() {
        return this.intraUserName ;
    }

    @Override
    public byte[] getImage() {
        return profileImage;
    }


    @Override
    public String getPhrase() {
        return this.intraUserPhrase ;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Actors getActorType() {
        return null;
    }

    public PhotoType getPhotoType() { return photoType; }

    @Override
    public void setNewProfileImage(byte[] newProfileImage)  {

    }

    @Override
    public String createMessageSignature(String message) {
        return null;
    }
}
