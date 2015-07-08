package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common;

import java.util.UUID;

public class WalletContact {

    public UUID actorId;

    public String name;

    public String address;

    public WalletContact(String name, String address) {
        this.name = name;
        this.address = address;
    }

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