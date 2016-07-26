package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2/14/15.
 */
public enum SubApps {

    CWP_WALLET_MANAGER("CWM"),
    CWP_WALLET_RUNTIME("CWR"),
    CWP_WALLET_STORE("CWS"),
    CWP_WALLET_FACTORY("CWF"),
    CWP_DEVELOPER_APP("CDA"),
    CWP_WALLET_PUBLISHER("CWP"),
    CWP_INTRA_USER_IDENTITY("CIUI"),
    CWP_SHELL("CS"),
    CBP_CRYPTO_BROKER_IDENTITY("CBPCBI"),
    CBP_CRYPTO_BROKER_COMMUNITY("CBPCBC"),
    CBP_CRYPTO_CUSTOMER_IDENTITY("CBPCCI"),
    CBP_CRYPTO_CUSTOMER_COMMUNITY("CBPCCC"),
    CCP_INTRA_USER_COMMUNITY("CCPIUC"),
    CBP_CUSTOMERS("CBPC"),
    DAP_ASSETS_FACTORY("DAPAF"),
    DAP_ASSETS_COMMUNITY_USER("DAPCU"),
    DAP_ASSETS_COMMUNITY_ISSUER("DAPCI"),
    DAP_ASSETS_COMMUNITY_REDEEM_POINT("DAPCRP"),
    DAP_ASSETS_IDENTITY_ISSUER("DAPAII"),
    DAP_ASSETS_IDENTITY_USER("DAPAIU"),
    DAP_ASSETS_REDEEM_POINT_IDENTITY("DAPRPI"),

    //ART sub apps
    ART_MUSIC_PLAYER("AMP"),
    ART_ARTIST_COMMUNITY("AACA"),
    ART_ARTIST_IDENTITY("AAIA"),
    ART_FAN_COMMUNITY("AFC"),
    ART_FAN_IDENTITY("AFI"),

    //TKY sub apps
    TKY_ARTIST_IDENTITY("TAI"),
    TKY_FAN_IDENTITY_SUB_APP("TFISP"),
    TKY_ARTIST_IDENTITY_SUB_APP("TAISA"),


    CHT_CHAT("CHTCHAT"),
    CHT_CHAT_IDENTITY("CHTCHATI"),
    CHT_COMMUNITY("CHTCOM"),
    Scanner("S"),
    SETTINGS("SET"), REPORT_EXCEPTION("RE");


    private final String code;

    SubApps(String code) {
        this.code = code;
    }

    public static SubApps getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "TAISA":
                return SubApps.TKY_ARTIST_IDENTITY_SUB_APP;
            case "AAIA":
                return SubApps.ART_ARTIST_IDENTITY;
            case "AACA":
                return SubApps.ART_ARTIST_COMMUNITY;
            case "CBPCBC":
                return SubApps.CBP_CRYPTO_BROKER_COMMUNITY;
            case "CBPCBI":
                return SubApps.CBP_CRYPTO_BROKER_IDENTITY;
            case "CBPCCC":
                return SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY;
            case "CBPCCI":
                return SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY;
            case "CBPC":
                return SubApps.CBP_CUSTOMERS;
            case "CCPIUC":
                return SubApps.CCP_INTRA_USER_COMMUNITY;
            case "CDA":
                return SubApps.CWP_DEVELOPER_APP;
            case "CIUI":
                return SubApps.CWP_INTRA_USER_IDENTITY;
            case "CS":
                return SubApps.CWP_SHELL;
            case "CWM":
                return SubApps.CWP_WALLET_MANAGER;
            case "CWR":
                return SubApps.CWP_WALLET_RUNTIME;
            case "CWF":
                return SubApps.CWP_WALLET_FACTORY;
            case "CWP":
                return SubApps.CWP_WALLET_PUBLISHER;
            case "CWS":
                return SubApps.CWP_WALLET_STORE;
            case "DAPCI":
                return SubApps.DAP_ASSETS_COMMUNITY_ISSUER;
            case "DAPCU":
                return SubApps.DAP_ASSETS_COMMUNITY_USER;
            case "DAPCRP":
                return SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT;
            case "DAPAF":
                return SubApps.DAP_ASSETS_FACTORY;
            case "DAPAII":
                return SubApps.DAP_ASSETS_IDENTITY_ISSUER;
            case "DAPAIU":
                return SubApps.DAP_ASSETS_IDENTITY_USER;
            case "DAPRPI":
                return SubApps.DAP_ASSETS_REDEEM_POINT_IDENTITY;
            case "CHTCHAT":
                return SubApps.CHT_CHAT;
            case "CHTCHATI":
                return SubApps.CHT_CHAT_IDENTITY;
            case "CHTCOM":
                return SubApps.CHT_COMMUNITY;
            case "S":
                return Scanner;
            case "SET":
                return SETTINGS;
            case "TFISP":
                return TKY_FAN_IDENTITY_SUB_APP;
            case "AFC":
                return ART_FAN_COMMUNITY;
            case "AMP":
                return ART_MUSIC_PLAYER;
            case "RE":
                return REPORT_EXCEPTION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the SubApps enum");

        }

        /**
         * Return by default.
         */
        //return null;
    }

    public String getCode() {
        return this.code;
    }
}
