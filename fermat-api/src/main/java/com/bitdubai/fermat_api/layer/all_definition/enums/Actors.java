package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import java.io.Serializable;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Actors</code>
 * Lists all the Actors available on Fermat.
 * <p/>
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */

public enum Actors implements FermatEnum, Serializable {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    DEVICE_USER("DUS", null),
    EXTRA_USER("EUS", null),
    INTRA_USER("IUS", null),
    SHOP("SHP", null),

    CBP_CRYPTO_BROKER("CBPCRBR", "Broker Wallet"),
    CBP_CRYPTO_CUSTOMER("CBPCRCU", null),

    CCM_INTRA_WALLET_USER("CCMIU", null),
    CCP_INTRA_WALLET_USER("CCPIU", null),

    DAP_ASSET_ISSUER("DAPASIS", null),
    DAP_ASSET_REDEEM_POINT("DAPARP", null),
    DAP_ASSET_USER("DAPASUS", null),

    ART_ARTIST("AART", null),
    ART_FAN("AFAN", null),

    CHAT("CHT", null),

    LOSS_PROTECTED_USER("LPU", "Loss Protected Wallet"),
    BITCOIN_BASIC_USER("BBW", "Bitcoin Wallet");


    private final String code;
    private final String actorsDefaultWalletName;

    Actors(final String code, final String actorsDefaultWalletName) {
        this.code = code;
        this.actorsDefaultWalletName = actorsDefaultWalletName;
    }

    public static Actors getByCode(String code) throws IllegalArgumentException {

        switch (code) {
            case "DUS":
                return DEVICE_USER;
            case "EUS":
                return EXTRA_USER;
            case "IUS":
                return INTRA_USER;
            case "SHP":
                return SHOP;

            case "CBPCRBR":
                return CBP_CRYPTO_BROKER;
            case "CBPCRCU":
                return CBP_CRYPTO_CUSTOMER;

            case "CCMIU":
                return CCM_INTRA_WALLET_USER;
            case "CCPIU":
                return CCP_INTRA_WALLET_USER;

            case "DAPASIS":
                return DAP_ASSET_ISSUER;
            case "DAPARP":
                return DAP_ASSET_REDEEM_POINT;
            case "DAPASUS":
                return DAP_ASSET_USER;

            case "AART":
                return ART_ARTIST;
            case "AFAN":
                return ART_FAN;

            case "CHT":
                return CHAT;

            case "LPU":
                return LOSS_PROTECTED_USER;
            case "BBW":
                return BITCOIN_BASIC_USER;

            default:
                throw new IllegalArgumentException(
                        new StringBuilder().append("The code").append(code).append(" is not valid for the Actors enum.").toString()
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getActorsDefaultWalletName() {
        return this.actorsDefaultWalletName;
    }
}