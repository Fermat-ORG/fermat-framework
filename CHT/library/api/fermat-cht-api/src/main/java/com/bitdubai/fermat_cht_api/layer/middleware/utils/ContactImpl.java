package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public class ContactImpl implements Contact {
    //Documentar
    private UUID contactId;
    private String alias;
    private String remoteActorPublicKey;
    private byte[] image;
    private String contactStatus;

    public ContactImpl() {
    }

    @Override
    public UUID getContactId() {
        return this.contactId;
    }

    @Override
    public void setContactId(UUID contactId) {
        this.contactId = contactId;
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
                "contactId=" + contactId +
                ", alias='" + alias + '\'' +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", image=" + Arrays.toString(image) +
                ", contactStatus='" + contactStatus + '\'' +
                '}';
    }
}
