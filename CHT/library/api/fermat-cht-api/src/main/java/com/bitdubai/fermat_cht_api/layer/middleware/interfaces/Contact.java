package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;

import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Contact { //TODO: agregar imagen
    //Documentar
    UUID getContactId();

    void setContactId(UUID contactId);

    String getRemoteName();

    void setRemoteName(String remoteName);

    String getAlias();

    void setAlias(String alias);

    PlatformComponentType getRemoteActorType();

    void setRemoteActorType(PlatformComponentType remoteActorType);

    String getRemoteActorPublicKey();

    void setRemoteActorPublicKey(String remoteActorPublicKey);

    long getCreationDate();

    void setCreationDate(long creationDate);

    byte[] getProfileImage();

    void setProfileImage(byte[] profileImage);

    String getContactStatus();

    void setContactStatus(String contactStatus);
}
