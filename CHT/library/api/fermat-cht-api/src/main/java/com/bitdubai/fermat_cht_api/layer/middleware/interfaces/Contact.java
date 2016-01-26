package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Contact {
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
    Date getCreationDate();
    void setCreationDate(Date creationDate);
}
