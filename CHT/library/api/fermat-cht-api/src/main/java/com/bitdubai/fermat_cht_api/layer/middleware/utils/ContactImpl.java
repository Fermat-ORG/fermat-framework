package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public class ContactImpl implements Contact {
    //Documentar
    private UUID contactId;
    private String remoteName;
    private String alias;
    private String remoteActorType;
    private String remoteActorPublicKey;
    private Date creationDate;

    public ContactImpl(){};
    public ContactImpl(UUID contactId,
                       String remoteName,
                       String alias,
                       String remoteActorType,
                       String remoteActorPublicKey,
                       Date creationDate)
    {
        this.contactId            = contactId;
        this.remoteName           = remoteName;
        this.alias                = alias;
        this.remoteActorType      = remoteActorType;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.creationDate         = creationDate;
    };
    @Override
    public UUID getContactId() {
        return this.contactId;
    }

    @Override
    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    @Override
    public String getRemoteName() {
        return this.remoteName;
    }

    @Override
    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
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
    public String getRemoteActorType() {
        return this.remoteActorType;
    }

    @Override
    public void setRemoteActorType(String remoteActorType) {
        this.remoteActorType = remoteActorType;
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
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
