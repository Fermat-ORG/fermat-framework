package com.bitdubai.fermat_wpd_api.all_definition.events;

import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletInstalledEvent extends AbstractWPDEvent {

    private UUID walletId;
    private String publicKey;

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return this.walletId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public WalletInstalledEvent(EventType eventType) {
        super(eventType);
    }
}
