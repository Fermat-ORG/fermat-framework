package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter;

import java.util.UUID;

public class WalletContact {

    public UUID actorId;

    public String name;

    public String address;

    public WalletContact(String name, String address, UUID actorId) {
        this.name = name;
        this.address = address;
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return name;
    }
}