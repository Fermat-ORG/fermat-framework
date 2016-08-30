package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;

/**
 * Created by franklin on 08/01/16.
 */
public class ContactImpl implements Contact {

    private String alias;
    private String remoteActorPublicKey;
    private byte[] image;
    private String contactStatus;

    public ContactImpl() {
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return this.remoteActorPublicKey;
    }

    @Override
    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return this.image;
    }

    @Override
    public void setProfileImage(byte[] profileImage) {
        this.image = profileImage;
    }

    @Override
    public String getContactStatus() {
        return contactStatus;
    }

    @Override
    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    @Override
    public String toString() {
        return "ContactImpl{" +
                "alias='" + alias + '\'' +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", image=" + (image != null) +
                ", contactStatus='" + contactStatus + '\'' +
                '}';
    }
}
