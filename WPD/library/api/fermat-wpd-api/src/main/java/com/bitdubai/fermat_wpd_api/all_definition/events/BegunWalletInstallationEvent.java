package com.bitdubai.fermat_wpd_api.all_definition.events;

import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;

import java.util.UUID;

/**
 * Created by Nerio on 18/02/16.
 */
public class BegunWalletInstallationEvent extends AbstractWPDEvent {

    private UUID walletId;

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return this.walletId;
    }

    public BegunWalletInstallationEvent(EventType eventType) {
        super(eventType);
    }


}