package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

/**
 * Created by franklin on 08/01/16.
 */
public interface Contact {

    String getAlias();

    void setAlias(String alias);

    String getRemoteActorPublicKey();

    void setRemoteActorPublicKey(String remoteActorPublicKey);

    byte[] getProfileImage();

    void setProfileImage(byte[] profileImage);

    String getContactStatus();

    void setContactStatus(String contactStatus);
}
