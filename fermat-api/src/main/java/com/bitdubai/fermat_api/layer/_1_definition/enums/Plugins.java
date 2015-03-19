package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Plugins {
    LICENSE_MANAGER ("license Manager"),
    BLOCKCHAIN_INFO_WORLD("Blockchain Info"),
    SHAPE_SHIFT_WORLD("Shape Shift"),
    COINAPULT_WORLD("Coinapult"),
    CRYPTO_INDEX ("Crypto Index"),
    BITCOIN_CRYPTO_NETWORK ("Bitcoin Crypto Network"),
    CLOUD_CHANNEL ("Cloud Channel"),
    USER_NETWORK_SERVICE ("user NetWork Service"),
    APP_RUNTIME_MIDDLEWARE ("App Runtime Middleware"),
    WALLET_MIDDLEWARE ("Wallet Middleware"),
    WALLET_RUNTIME_MODULE ("Wallet runtime Module"),
    WALLET_MANAGER_MODULE ("Wallet Manager Module"),
    INCOMING_INTRA_USER_TRANSACTION("Incoming Intra User Transaction"),
    OUTGOING_INTRA_USER_TRANSACTION("Outgoing Intra User Transaction"),
    INCOMING_DEVICE_USER_TRANSACTION("Incoming Device User Transaction"),
    OUTGOING_DEVICE_USER_TRANSACTION("Outgoing Device User Transaction"),
    INTER_WALLET_TRANSACTION("Inter Wallet Transaction"),
    BANK_NOTES_MIDDLEWARE("Bank Notes Middleware"),
    BANK_NOTES_NETWORK_SERVICE("Bank Notes Network Service"),
    WALLET_RESOURCES_NETWORK_SERVICE("Wallet Resources Network Service"),
    WALLET_STORE_NETWORK_SERVICE("Wallet Store Network Service"),
    WALLET_CONTACTS_MIDDLEWARE("Wallet Contacts Middleware"),
    WALLET_COMMUNITY_NETWORK_SERVICE("Wallet Community Network Service"), 
    ADDRESS_BOOK_CRYPTO("Address Book Crypto"),
    OUTGOING_EXTRA_USER_TRANSACTION("Outgoing Extra User Transaction"),
    INCOMING_EXTRA_USER_TRANSACTION("Incoming Extra User Transaction"),
    INCOMING_CRYPTO_TRANSACTION("Incoming Crypto Transaction");

    private final String key;

     Plugins(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }
}
