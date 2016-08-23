package com.bitdubai.fermat_wpd_api.all_definition.events;

import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;

/**
 * Created by loui on 05/02/15.
 */
public class WalletOpenedEvent extends AbstractWPDEvent {

    private String publicKey;

    public void setWalletPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getWalletPublicKey() {
        return this.publicKey;
    }

    public WalletOpenedEvent(EventType eventType) {
        super(eventType);
    }
}
