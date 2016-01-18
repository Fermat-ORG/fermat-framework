package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

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
    String getRemoteActorType();
    void setRemoteActorType(String remoteActorType);
    String getRemoteActorPublicKey();
    void setRemoteActorPublicKey(String remoteActorPublicKey);
    Date getCreationDate();
    void setCreationDate(Date creationDate);
}
