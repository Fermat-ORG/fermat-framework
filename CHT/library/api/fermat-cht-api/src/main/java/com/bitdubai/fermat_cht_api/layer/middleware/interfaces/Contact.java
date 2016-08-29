package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Contact { //TODO: agregar imagen
    //Documentar
    UUID getContactId();

    void setContactId(UUID contactId);

    String getAlias();

    void setAlias(String alias);

    String getRemoteActorPublicKey();

    void setRemoteActorPublicKey(String remoteActorPublicKey);

    byte[] getProfileImage();

    void setProfileImage(byte[] profileImage);

    String getContactStatus();

    void setContactStatus(String contactStatus);
}
