package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by natalia on 18/02/16.
 */
public enum SubAppsPublicKeys implements FermatEnum {

    //TODO BNK Platform

    //TODO CHT Platform
    CHT_OPEN_CHAT("public_key_cht_chat"),
    CHT_CHAT_IDENTITY("public_key_cht_identity_chat"),
    CHT_COMMUNITY("public_key_cht_community"),

    //TODO CBP Platform
    CBP_BROKER_COMMUNITY("public_key_crypto_broker_community"),
    CBP_BROKER_IDENTITY("sub_app_crypto_broker_identity"),
    CBP_CUSTOMER_COMMUNITY("public_key_crypto_customer_community"),
    CBP_CUSTOMER_IDENTITY("sub_app_crypto_customer_identity"),
    //TODO CCP Platform
    CCP_COMMUNITY("public_key_intra_user_commmunity"),
    CCP_IDENTITY("public_key_ccp_intra_user_identity"),
    //TODO CWP Platform
    CWP_FACTORY("public_key_factory"),
    CWP_PUBLISHER("public_key_publisher"),
    CWP_MANAGER("public_key_wallet_manager"),
    CWP_RUNTIME("public_key_runtime"),
    CWP_SHEL_LOGIN("public_key_shell"),
    CWP_STORE("public_key_store"),
    //TODO DAP Platform
    DAP_COMMUNITY_ISSUER("public_key_dap_issuer_community"),
    DAP_COMMUNITY_USER("public_key_dap_user_community"),
    DAP_COMMUNITY_REDEEM("public_key_dap_redeem_point_community"),
    DAP_IDENTITY_ISSUER("public_key_dap_asset_issuer_identity"),
    DAP_IDENTITY_USER("public_key_dap_asset_user_identity"),
    DAP_IDENTITY_REDEEM("public_key_dap_redeem_point_identity"),
    DAP_FACTORY("public_key_dap_factory"),
    //TODO PIP Platform
    PIP_DEVELOPER("public_key_pip_developer_sub_app"),
    SETTINGS("public_key_settings"),
    //TODO ART PLATFORM
    ART_ARTIST_IDENTITY("public_key_art_artist_identity"),
    ART_FAN_IDENTITY("public_key_art_fan_identity"),
    ART_FAN_COMMUNITY("public_key_art_fan_community"),
    ART_ARTIST_COMMUNITY("sub_app_art_artist_community"),
    ART_MUSIC_PLAYER("public_key_art_music_player"),

    //TODO TKY PLATFORM
    TKY_ARTIST_IDENTITY("public_key_tky_artist_identity"),
    TKY_FAN_IDENTITY("sub_app_tky_fan_create_identity"),;

    private String code;

    SubAppsPublicKeys(String code) {
        this.code = code;
    }

    public static SubAppsPublicKeys getByCode(String code) throws InvalidParameterException {

        switch (code) {

            //TODO CHT Platform
            case "public_key_cht_chat":
                return CHT_OPEN_CHAT;
            case "public_key_cht_identity_chat":
                return CHT_CHAT_IDENTITY;
            case "public_key_cht_community":
                return CHT_COMMUNITY;
            //TODO CBP Platform
            case "sub_app_crypto_broker_identity":
                return CBP_BROKER_IDENTITY;
            case "sub_app_crypto_customer_identity":
                return CBP_CUSTOMER_IDENTITY;
            case "public_key_crypto_broker_community":
                return CBP_BROKER_COMMUNITY;
            case "public_key_crypto_customer_community":
                return CBP_CUSTOMER_COMMUNITY;
            //TODO CCP Platform
            case "public_key_intra_user_commmunity":
                return CCP_COMMUNITY;
            case "public_key_intra_user_identity":
                return CCP_IDENTITY;
            //TODO CWP Platform
            case "public_key_factory":
                return CWP_FACTORY;
            case "public_key_publisher":
                return CWP_PUBLISHER;
            case "public_key_wallet_manager":
                return CWP_MANAGER;
            case "public_key_runtime":
                return CWP_RUNTIME;
            case "public_key_shell":
                return CWP_SHEL_LOGIN;
            case "public_key_store":
                return CWP_STORE;
            //TODO DAP Platform
            case "public_key_dap_issuer_community":
                return DAP_COMMUNITY_ISSUER;
            case "public_key_dap_user_community":
                return DAP_COMMUNITY_USER;
            case "public_key_dap_redeem_point_community":
                return DAP_COMMUNITY_REDEEM;
            case "public_key_dap_asset_issuer_identity":
                return DAP_IDENTITY_ISSUER;
            case "public_key_dap_asset_user_identity":
                return DAP_IDENTITY_USER;
            case "public_key_dap_redeem_point_identity":
                return DAP_IDENTITY_REDEEM;
            case "public_key_dap_factory":
                return DAP_FACTORY;
            //TODO PIP Platform
            case "public_key_pip_developer_sub_app":
                return PIP_DEVELOPER;
            case "public_key_settings":
                return SETTINGS;
            //TODO ART PLATAFORM
            case "public_key_art_artist_identity":
                return ART_ARTIST_IDENTITY;
            case "public_key_art_fan_identity":
                return ART_FAN_IDENTITY;
            case "sub_app_art_artist_community":
                return ART_ARTIST_COMMUNITY;
            case "public_key_art_fan_community":
                return ART_FAN_COMMUNITY;
            case "public_key_art_music_player":
                return ART_MUSIC_PLAYER;

            //TODO TKY PLATAFORM
            case "public_key_tky_artist_identity":
                return TKY_ARTIST_IDENTITY;
            case "sub_app_tky_fan_create_identity":
                return TKY_FAN_IDENTITY;


            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}


