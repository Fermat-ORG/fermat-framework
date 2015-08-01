package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

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
    BITDUBAI_USER_NETWORK_SERVICE("User NetWork Service", Developers.BITDUBAI),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE("Template NetWork Service", Developers.BITDUBAI),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE("Intra User NetWork Service", Developers.BITDUBAI),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE("App Runtime Middleware", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET("Discount Wallet Basic Wallet", Developers.BITDUBAI),
    BITDUBAI_WALLET_RUNTIME_MODULE("Wallet runtime Module", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MODULE("Wallet Manager Module", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MODULE("Wallet Factory Module", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_VAULT("Bitcoin Crypto Vault", Developers.BITDUBAI),
    BITDUBAI_INTRA_USER_FACTORY_MODULE("IntraUser Factory Module", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE("Bank Notes Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE("Crypto Loss Protected Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE("Crypto Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE("Discount Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE("Fiat Over Crypto Loss Protected Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE("Fiat Over Crypto Wallet Niche Wallet Type", Developers.BITDUBAI),
    BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE("Multi Account Wallet Niche Wallet Type", Developers.BITDUBAI),
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
    BITDUBAI_USER_ADDRESS_BOOK_CRYPTO("User Address Book Crypto", Developers.BITDUBAI),
    BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO("Wallet Address Book Crypto", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION("Outgoing Extra User Transaction", Developers.BITDUBAI),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION("Incoming Extra User Transaction", Developers.BITDUBAI),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION("Incoming Crypto Transaction", Developers.BITDUBAI),
    BITDUBAI_USER_DEVICE_USER("User Device User", Developers.BITDUBAI),
    BITDUBAI_USER_EXTRA_USER("User Extra User", Developers.BITDUBAI),
    BITDUBAI_USER_INTRA_USER("User Intra User", Developers.BITDUBAI),
    BITDUBAI_COINBASE_WORLD("Coinbase World", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET("Bitcoin Wallet Basic Wallet", Developers.BITDUBAI ),
    BITDUBAI_DEVICE_CONNECTIVITY("Bitcoin Device Connectivity", Developers.BITDUBAI ),
    BITDUBAI_LOCATION_WORLD("Location World", Developers.BITDUBAI),
    BITDUBAI_ACTOR_DEVELOPER("Actor Developer", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MIDDLEWARE("Wallet Factory Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE("Wallet Language Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MIDDLEWARE("Wallet Manager Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE("Wallet Publisher Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE("Wallet Skin Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_MIDDLEWARE("Wallet Store Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_SETTINGS_MIDDLEWARE("Wallet Settings Middleware", Developers.BITDUBAI),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE("Wallet Statistics Network Service", Developers.BITDUBAI),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE("SubApp Resources Network Service", Developers.BITDUBAI),

    BITDUBAI_DEVELOPER_IDENTITY("Developer Identity", Developers.BITDUBAI),
    BITDUBAI_IDENTITY_MANAGER("Identity Managers", Developers.BITDUBAI),
    BITDUBAI_DEVELOPER_MODULE("Developer Module", Developers.BITDUBAI);


    private final String key;
    private final Developers developer;

    Plugins(String key, Developers developer) {
        this.key = key;
        this.developer = developer;
    }

    public static Plugins getByKey(String key) throws InvalidParameterException {
        switch(key){
            case "license Manager":
                return Plugins.BITDUBAI_LICENSE_MANAGER;
            case "Blockchain Info World":
                return Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD;
            case "Shape Shift World":
                return Plugins.BITDUBAI_SHAPE_SHIFT_WORLD;
            case "Coinapult World":
                return Plugins.BITDUBAI_COINAPULT_WORLD;
            case "Crypto Index World":
                return Plugins.BITDUBAI_CRYPTO_INDEX;
            case "Bitcoin Crypto Network":
                return Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK;
            case "Cloud Channel":
                return Plugins.BITDUBAI_CLOUD_CHANNEL;
            case "cloud Server Communication":
                return Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION;
            case "user NetWork Service":
                return Plugins.BITDUBAI_USER_NETWORK_SERVICE;
            case "App Runtime Middleware":
                return Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE;
            case "Discount Wallet Basic Wallet":
                return Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET;
            case "Wallet runtime Module":
                return Plugins.BITDUBAI_WALLET_RUNTIME_MODULE;
            case "Wallet Manager Module":
                return Plugins.BITDUBAI_WALLET_MANAGER_MODULE;
            case "Wallet Factory Module":
                return Plugins.BITDUBAI_WALLET_FACTORY_MODULE;
            case "Bitcoin Crypto Vault":
                return Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT;
            case "Bank Notes Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE;
            case "Crypto Loss Protected Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE;
            case "Crypto Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE;
            case "Discount Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE;
            case "Fiat Over Crypto Loss Protected Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE;
            case "Fiat Over Crypto Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE;
            case "Multi Account Wallet Niche Wallet Type":
                return Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE;
            case "Incoming Intra User Transaction":
                return Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION;
            case "Outgoing Intra User Transaction":
                return Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION;
            case "Incoming Device User Transaction":
                return Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION;
            case "Outgoing Device User Transaction":
                return Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION;
            case "Inter Wallet Transaction":
                return Plugins.BITDUBAI_INTER_WALLET_TRANSACTION;
            case "Bank Notes Middleware":
                return Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE;
            case "Bank Notes Network Service":
                return Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE;
            case "Wallet Resources Network Service":
                return Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE;
            case "Wallet Store Network Service":
                return Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE;
            case "Wallet Store Middleware":
                return Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE;
            case "Wallet Factory Middleware":
                return Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE;
            case "Wallet Language Middleware":
                return Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE;
            case "Wallet Skin Middleware":
                return Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE;
            case "Wallet Manager Middleware":
                return Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE;
            case "Wallet Publisher Middleware":
                return Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE;
            case "Wallet Contacts Middleware":
                return Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE;
            case "Wallet Community Network Service":
                return Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE;
            case "User Address Book Crypto":
                return Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO;
            case "Wallet Address Book Crypto":
                return Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO;
            case "Outgoing Extra User Transaction":
                return Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION;
            case "Incoming Extra User Transaction":
                return Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION;
            case "Incoming Crypto Transaction":
                return Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION;
            case "User Device User":
                return Plugins.BITDUBAI_USER_DEVICE_USER;
            case "User Extra User":
                return Plugins.BITDUBAI_USER_EXTRA_USER;
            case "User Intra User":
                return Plugins.BITDUBAI_USER_INTRA_USER;
            case "Coinbase World":
                return Plugins.BITDUBAI_COINBASE_WORLD;
            case "Bitcoin Wallet Basic Wallet":
                return Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET;
            case "Bitcoin Device Connectivity":
                return Plugins.BITDUBAI_DEVICE_CONNECTIVITY;
            case "Location World":
                return Plugins.BITDUBAI_LOCATION_WORLD;
            case "Actor Developer":
                return Plugins.BITDUBAI_ACTOR_DEVELOPER;
            case "Identity Managers":
                return Plugins.BITDUBAI_IDENTITY_MANAGER;
        }

        throw new InvalidParameterException(key);
    }

    public String getKey()   { return this.key; }

    public Developers getDeveloper()   { return this.developer; }


}
