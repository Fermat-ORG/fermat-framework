package com.bitdubai.wallet_platform_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Plugins {
    LICENSE_MANAGER ("license Manager"),
    CRYPTO_INDEX ("Crypto Index"),
    BITCOIN_CRYPTO_NETWORK ("Bitcoin Crypto Network"),
    CLOUD_CHANNEL ("Cloud Channel"),
    USER_NETWORK_SERVICE ("user NetWork Service"),
    APP_RUNTIME_MIDDLEWARE ("App Runtime Middleware"),
    WALLET_MIDDLEWARE ("Wallet Middleware"),
    WALLET_RUNTIME_MODULE ("Wallet runtime Module"),
    WALLET_MANAGER_MODULE ("Wallet Manager Module"),
    FROM_EXTRA_USER_TRANSACTION("From Extra User Transaction"),
    INTER_USER_TRANSACTION("Inter User Transaction"),
    INTER_WALLET_TRANSACTION("Inter Wallet Transaction"),
    TO_EXTRA_USER_TRANSACTION("To Extra User Transaction"),
    BANK_NOTES_MIDDLEWARE("Bank Notes Middleware"),
    BANK_NOTES_NETWORK_SERVICE("Bank Notes Network Service"),
    WALLET_RESOURCES_NETWORK_SERVICE("Wallet Resources Network Service"),
    WALLET_STORE_NETWORK_SERVICE("Wallet Store Network Service"),
    WALLET_CONTACTS_MIDDLEWARE("Wallet Contacts Middleware"),
    WALLET_COMMUNITY_NETWORK_SERVICE("Wallet Community Network Service");

    private final String key;

     Plugins(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }
}
