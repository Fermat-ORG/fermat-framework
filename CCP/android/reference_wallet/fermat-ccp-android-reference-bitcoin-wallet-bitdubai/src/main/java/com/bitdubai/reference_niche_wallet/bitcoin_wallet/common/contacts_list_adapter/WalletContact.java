package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter;

import java.util.UUID;

public class WalletContact {

    public UUID contactId;

    public String actorPublicKey;

    public String name;

    public String address;

    public WalletContact() {
    }

    public WalletContact(UUID contactId, String actorPublicKey, String name, String address) {
        this.contactId = contactId;
        this.actorPublicKey = actorPublicKey;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }


    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }
}