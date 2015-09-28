package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 * 
 */
public enum Plugins {

    //Modified by Manuel Perez on 03/08/2015
    BITDUBAI_LICENSE_MANAGER("BLICM", Developers.BITDUBAI),
    BITDUBAI_BLOCKCHAIN_INFO_WORLD("BBLOCKIW", Developers.BITDUBAI),
    BITDUBAI_SHAPE_SHIFT_WORLD("BSHAPESW", Developers.BITDUBAI),
    BITDUBAI_COINAPULT_WORLD("BCOINAW", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_INDEX("BCRYPTOINW", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK("BBTCCNET", Developers.BITDUBAI),
    BITDUBAI_CLOUD_CHANNEL("BCLOUDC", Developers.BITDUBAI),

    BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER("BWSCCLSERVER", Developers.BITDUBAI),
    BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL("BWSCCLIENTCH", Developers.BITDUBAI),

    BITDUBAI_CLOUD_SERVER_COMMUNICATION("BCLOUSC", Developers.BITDUBAI),
    BITDUBAI_USER_NETWORK_SERVICE("BUSERNETS", Developers.BITDUBAI),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE("BTEMNETS", Developers.BITDUBAI),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE("BINUSERNS", Developers.BITDUBAI),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE("BAPPRUNM", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET("BDWALLBW", Developers.BITDUBAI),
    BITDUBAI_WALLET_RUNTIME_MODULE("BWALLRUNM", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MODULE("BWALLMANM", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MODULE("BWALLFACM", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_VAULT("BBTCCRYV", Developers.BITDUBAI),
    BITDUBAI_ASSETS_CRYPTO_VAULT("BASSTCRYV", Developers.BITDUBAI),
    BITDUBAI_INTRA_USER_FACTORY_MODULE("BINUSFACM", Developers.BITDUBAI),
    BITDUBAI_INTRA_USER_ACTOR("BINUSERA", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE("BBNWWM", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE("BCLPWWM", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_WALLET_WALLET_MODULE("BCWWM", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE("BDWWM", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE("BFOCLPWWM", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_WALLET_MODULE("BFOCWWM", Developers.BITDUBAI),
    BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE("BMAWWM", Developers.BITDUBAI),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION("BININUST", Developers.BITDUBAI),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION("BINDEVUT", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION("BODEVUST", Developers.BITDUBAI),
    BITDUBAI_INTER_WALLET_TRANSACTION("BINWALLT", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_MIDDLEWARE("BBNMIDD", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE("BBNNETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE("BWRNETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE("BWSTONETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE("BWCNETSER", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_ADDRESS_BOOK("BCADDB", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION("BOUEXUT", Developers.BITDUBAI),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION("BINEXUT", Developers.BITDUBAI),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION("BINCRYT", Developers.BITDUBAI),
    BITDUBAI_USER_DEVICE_USER("BUDEVU", Developers.BITDUBAI),
    BITDUBAI_ACTOR_EXTRA_USER("BAEXU", Developers.BITDUBAI),
    BITDUBAI_USER_INTRA_USER("BUINU", Developers.BITDUBAI),
    BITDUBAI_COINBASE_WORLD("BCOINW", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET("BBTCWBW", Developers.BITDUBAI),
    BITDUBAI_DEVICE_CONNECTIVITY("BBTCDEVC", Developers.BITDUBAI),
    BITDUBAI_LOCATION_WORLD("BLOCW", Developers.BITDUBAI),
    BITDUBAI_ACTOR_DEVELOPER("BACTORD", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MIDDLEWARE("BWFMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE("BWLMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MIDDLEWARE("BWMMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE("BWPMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_PUBLISHER_MODULE("BWPMOD", Developers.BITDUBAI),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE("BWSMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_MIDDLEWARE("BWSTMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE("BWNSMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_MODULE("BWSM", Developers.BITDUBAI),
    BITDUBAI_WALLET_SETTINGS_MIDDLEWARE("BWSEMIDD", Developers.BITDUBAI),
    BITDUBAI_SUB_APP_SETTINGS_MIDDLEWARE("BSASEMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE("BWSTANETSER", Developers.BITDUBAI),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE("BSRNETSER", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE("BCTNSER", Developers.BITDUBAI),
    BITDUBAI_REQUEST_MONEY_REQUEST("BRMR", Developers.BITDUBAI),
    BITDUBAI_DEVELOPER_IDENTITY("BDEVID", Developers.BITDUBAI),
    BITDUBAI_PUBLISHER_IDENTITY("BPUBID", Developers.BITDUBAI),
    BITDUBAI_TRANSLATOR_IDENTITY("BDTRAID", Developers.BITDUBAI),
    BITDUBAI_IDENTITY_MANAGER("BIDMAN", Developers.BITDUBAI),
    BITDUBAI_DESIGNER_IDENTITY("BDDESID", Developers.BITDUBAI),
    BITDUBAI_DEVELOPER_MODULE("BDEVMOD", Developers.BITDUBAI),
    BITDUBAI_MIDDLEWARE_NOTIFICATION("BDNOTMID", Developers.BITDUBAI),

    BITDUBAI_ASSET_ISSUING_TRANSACTION("BAIT", Developers.BITDUBAI),
    BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION("BADT", Developers.BITDUBAI),

    BITDUBAI_DAP_ISSUER_ACTOR_LAYER("BDAPIAL",Developers.BITDUBAI),
    BITDUBAI_DAP_USER_ACTOR_LAYER("BDAPUAL",Developers.BITDUBAI),
    BITDUBAI_DAP_REDEEM_POINT_ACTOR_LAYER("BDAPRPAL",Developers.BITDUBAI),

    BITDUBAI_DAP_ISSUER_IDENTITY_LAYER("BDAPIIL",Developers.BITDUBAI),
    BITDUBAI_DAP_USER_IDENTITY_LAYER("BDAPUIL",Developers.BITDUBAI),
    BITDUBAI_DAP_REDEEM_POINT_IDENTITY_LAYER("BDAPRPIL",Developers.BITDUBAI),

    BITDUBAI_ASSET_FACTORY("BASF", Developers.BITDUBAI),
    BITDUBAI_ASSET_FACTORY_MODULE("BASFM", Developers.BITDUBAI),
    BITDUBAI_ASSET_WALLET_ISSUER("BASWI", Developers.BITDUBAI),

    BITDUBAI_DESKTOP_RUNTIME("BDR",Developers.BITDUBAI),

    // Init CCP Plugins

    BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE("BCCPCANS", Developers.BITDUBAI),
    BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY      ("BCCPIUI" , Developers.BITDUBAI),
    BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE      ("BCCPWCM" , Developers.BITDUBAI),
    BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION("BCCPOIAT", Developers.BITDUBAI)

    // End  CCP Plugins


    ;

    private String code;
    private Developers developer;

    Plugins(String code, Developers developer) {
        this.code = code;
        this.developer = developer;
    }

    public String getKey() {
        return this.code;
    }

    public static Plugins getByKey(String code) throws InvalidParameterException {
        switch (code) {
            case "BLICM":
                return BITDUBAI_LICENSE_MANAGER;
            case "BBLOCKIW":
                return BITDUBAI_BLOCKCHAIN_INFO_WORLD;
            case "BSHAPESW":
                return BITDUBAI_SHAPE_SHIFT_WORLD;
            case "BCOINAW":
                return BITDUBAI_COINAPULT_WORLD;
            case "BCRYPTOINW":
                return BITDUBAI_CRYPTO_INDEX;
            case "BBTCCNET":
                return BITDUBAI_BITCOIN_CRYPTO_NETWORK;
            case "BCLOUDC":
                return BITDUBAI_CLOUD_CHANNEL;
            case "BWSCCLSERVER":
                return BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER;
            case "BWSCCLIENTCH":
                return BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL;
            case "BCLOUSC":
                return BITDUBAI_CLOUD_SERVER_COMMUNICATION;
            case "BUSERNETS":
                return BITDUBAI_USER_NETWORK_SERVICE;
            case "BAPPRUNM":
                return BITDUBAI_APP_RUNTIME_MIDDLEWARE;
            case "BDWALLBW":
                return BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET;
            case "BWALLRUNM":
                return BITDUBAI_WALLET_RUNTIME_MODULE;
            case "BWALLMANM":
                return BITDUBAI_WALLET_MANAGER_MODULE;
            case "BWALLFACM":
                return BITDUBAI_WALLET_FACTORY_MODULE;
            case "BBTCCRYV":
                return BITDUBAI_BITCOIN_CRYPTO_VAULT;
            case "BASSTCRYV":
                return BITDUBAI_ASSETS_CRYPTO_VAULT;
            case "BBNWWM":
                return BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE;
            case "BCLPWWM":
                return BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE;
            case "BCWWM":
                return BITDUBAI_CRYPTO_WALLET_WALLET_MODULE;
            case "BDWWM":
                return BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE;
            case "BFOCLPWWM":
                return BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE;
            case "BFOCWWM":
                return BITDUBAI_FIAT_OVER_CRYPTO_WALLET_WALLET_MODULE;
            case "BMAWWM":
                return BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE;
            case "BININUST":
                return BITDUBAI_INCOMING_INTRA_USER_TRANSACTION;
            case "BINDEVUT":
                return BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION;
            case "BODEVUST":
                return BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION;
            case "BINWALLT":
                return BITDUBAI_INTER_WALLET_TRANSACTION;
            case "BBNMIDD":
                return BITDUBAI_BANK_NOTES_MIDDLEWARE;
            case "BBNNETSER":
                return BITDUBAI_BANK_NOTES_NETWORK_SERVICE;
            case "BWRNETSER":
                return BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE;
            case "BWSTONETSER":
                return BITDUBAI_WALLET_STORE_NETWORK_SERVICE;
            case "BWSMIDD":
                return BITDUBAI_WALLET_STORE_MIDDLEWARE;
            case "BWSM":
                return BITDUBAI_WALLET_STORE_MODULE;
            case "BWFMIDD":
                return BITDUBAI_WALLET_FACTORY_MIDDLEWARE;
            case "BWLMIDD":
                return BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE;
            case "BWSTMIDD":
                return BITDUBAI_WALLET_SKIN_MIDDLEWARE;
            case "BWMMIDD":
                return BITDUBAI_WALLET_MANAGER_MIDDLEWARE;
            case "BWPMIDD":
                return BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE;
            case "BWPMOD":
                return BITDUBAI_WALLET_PUBLISHER_MODULE;
            case "BWCNETSER":
                return BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE;
            case "BCADDB":
                return BITDUBAI_CRYPTO_ADDRESS_BOOK;
            case "BOUEXUT":
                return BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION;
            case "BINEXUT":
                return BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION;
            case "BINCRYT":
                return BITDUBAI_INCOMING_CRYPTO_TRANSACTION;
            case "BUDEVU":
                return BITDUBAI_USER_DEVICE_USER;
            case "BAEXU":
                return BITDUBAI_ACTOR_EXTRA_USER;
            case "BUINU":
                return BITDUBAI_USER_INTRA_USER;
            case "BCOINW":
                return BITDUBAI_COINBASE_WORLD;
            case "BBTCWBW":
                return BITDUBAI_BITCOIN_WALLET_BASIC_WALLET;
            case "BBTCDEVC":
                return BITDUBAI_DEVICE_CONNECTIVITY;
            case "BLOCW":
                return BITDUBAI_LOCATION_WORLD;
            case "BACTORD":
                return BITDUBAI_ACTOR_DEVELOPER;
            case "BIDMAN":
                return BITDUBAI_IDENTITY_MANAGER;
            case "BDEVID":
                return BITDUBAI_DEVELOPER_IDENTITY;
            case "BPUBID":
                return BITDUBAI_PUBLISHER_IDENTITY;
            case "BDTRAID":
                return BITDUBAI_TRANSLATOR_IDENTITY;
            case "BTEMNETS":
                return BITDUBAI_TEMPLATE_NETWORK_SERVICE;
            case "BINUSERNS":
                return BITDUBAI_INTRAUSER_NETWORK_SERVICE;
            case "BINUSFACM":
                return BITDUBAI_INTRA_USER_FACTORY_MODULE;
            case "BINUSERA":
                return BITDUBAI_INTRA_USER_ACTOR;
            case "BWNSMIDD":
                return BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE;
            case "BWSEMIDD":
                return BITDUBAI_WALLET_SETTINGS_MIDDLEWARE;
            case "BSASEMIDD":
                return BITDUBAI_SUB_APP_SETTINGS_MIDDLEWARE;
            case "BWSTANETSER":
                return BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE;
            case "BSRNETSER":
                return BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE;
            case "BCTNSER":
                return BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE;
            case "BRMR":
                return BITDUBAI_REQUEST_MONEY_REQUEST;
            case "BDDESID":
                return BITDUBAI_DESIGNER_IDENTITY;
            case "BDEVMOD":
                return BITDUBAI_DEVELOPER_MODULE;
            case "BDNOTMID":
                return BITDUBAI_MIDDLEWARE_NOTIFICATION;
            case "BAIT":
                return BITDUBAI_ASSET_ISSUING_TRANSACTION;
            case "BADT":
                return BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION;
            case "BDAPIAL":
                return BITDUBAI_DAP_ISSUER_ACTOR_LAYER;
            case "BDAPUAL":
                return BITDUBAI_DAP_USER_ACTOR_LAYER;
            case "BDAPRPAL":
                return BITDUBAI_DAP_REDEEM_POINT_ACTOR_LAYER;
            case "BDAPIIL":
                return BITDUBAI_DAP_ISSUER_IDENTITY_LAYER;
            case "BDAPUIL":
                return BITDUBAI_DAP_USER_IDENTITY_LAYER;
            case "BDAPRPIL":
                return BITDUBAI_DAP_REDEEM_POINT_IDENTITY_LAYER;
            case "BASF":
                return BITDUBAI_ASSET_FACTORY;
            case "BASFM":
                return BITDUBAI_ASSET_FACTORY_MODULE;
            case "BASWI":
                return BITDUBAI_ASSET_WALLET_ISSUER;
            case "BDR":
                return BITDUBAI_DESKTOP_RUNTIME;

            // Init CCP Plugins

            case "BCCPCANS": return BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE;
            case "BCCPIUI":  return BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY      ;
            case "BCCPWCM":  return BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE      ;
            case "BCCPOIAT": return BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION;

            // End  CCP Plugins

            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Plugins enum");
        }
    }

    public Developers getDeveloper() {
        return this.developer;
    }


}
