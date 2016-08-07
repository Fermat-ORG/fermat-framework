package com.bitdubai.fermat_wpd_api.all_definition.events;

import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletUninstalledEvent extends AbstractWPDEvent {

    private UUID walletId;

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return this.walletId;
    }

    public WalletUninstalledEvent(EventType eventType) {
        super(eventType);
    }

}
