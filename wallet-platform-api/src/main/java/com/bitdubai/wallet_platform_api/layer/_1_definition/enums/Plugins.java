package com.bitdubai.wallet_platform_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Plugins {
    LICENSE_MANAGER (501),
    CRYPTO_INDEX (601),
    BITCOIN_CRYPTO_NETWORK (701),
    CLOUD_CHANNEL (801),
    USER_NETWORK_SERVICE (901),
    APP_RUNTIME_MIDDLEWARE (1002),
    WALLET_MIDDLEWARE (1001),
    WALLET_RUNTIME_MODULE (1101),
    WALLET_MANAGER_MODULE (1102);

    private final int index;

    Plugins(int index) {
        this.index = index;
    }

    public int getIndex()   { return this.index; }
}
