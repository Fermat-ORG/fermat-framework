package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Plugins {
    
    BITDUBAI_LICENSE_MANAGER("license Manager", Developers.BITDUBAI),
    BITDUBAI_BLOCKCHAIN_INFO_WORLD("Blockchain Info World", Developers.BITDUBAI),
    BITDUBAI_SHAPE_SHIFT_WORLD("Shape Shift World", Developers.BITDUBAI),
    BITDUBAI_COINAPULT_WORLD("Coinapult World", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_INDEX("Crypto Index World", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK("Bitcoin Crypto Network", Developers.BITDUBAI),
    BITDUBAI_CLOUD_CHANNEL("Cloud Channel", Developers.BITDUBAI),
    BITDUBAI_CLOUD_SERVER_COMMUNICATION("cloud Server Communication", Developers.BITDUBAI),
    BITDUBAI_USER_NETWORK_SERVICE("user NetWork Service", Developers.BITDUBAI),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE("App Runtime Middleware", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET("Discount Wallet Basic Wallet", Developers.BITDUBAI),
    BITDUBAI_WALLET_RUNTIME_MODULE("Wallet runtime Module", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MODULE("Wallet Manager Module", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MODULE("Wallet Factory Module", Developers.BITDUBAI),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION("Incoming Intra User Transaction", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION("Outgoing Intra User Transaction", Developers.BITDUBAI),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION("Incoming Device User Transaction", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION("Outgoing Device User Transaction", Developers.BITDUBAI),
    BITDUBAI_INTER_WALLET_TRANSACTION("Inter Wallet Transaction", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_MIDDLEWARE("Bank Notes Middleware", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE("Bank Notes Network Service", Developers.BITDUBAI),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE("Wallet Resources Network Service", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE("Wallet Store Network Service", Developers.BITDUBAI),
    BITDUBAI_WALLET_CONTACTS_MIDDLEWARE("Wallet Contacts Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE("Wallet Community Network Service", Developers.BITDUBAI), 
    BITDUBAI_ADDRESS_BOOK_CRYPTO("Address Book Crypto", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION("Outgoing Extra User Transaction", Developers.BITDUBAI),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION("Incoming Extra User Transaction", Developers.BITDUBAI),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION("Incoming Crypto Transaction", Developers.BITDUBAI),
    BITDUBAI_COINBASE_WORLD("Coinbase World", Developers.BITDUBAI);

    private final String key;
    private final Developers developer;

     Plugins(String key, Developers developer) {
         this.key = key;
         this.developer = developer;
    }

    public String getKey()   { return this.key; }

    public Developers getDeveloper()   { return this.developer; }


}
