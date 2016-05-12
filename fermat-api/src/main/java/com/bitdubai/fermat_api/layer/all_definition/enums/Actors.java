package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Actors</code>
 * Lists all the Actors available on Fermat.
 * <p/>
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */

public enum Actors implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    DEVICE_USER             ("DUS"),
    EXTRA_USER              ("EUS"),
    INTRA_USER              ("IUS"),
    SHOP                    ("SHP"),

    CBP_CRYPTO_BROKER       ("CBPCRBR"),
    CBP_CRYPTO_CUSTOMER     ("CBPCRCU"),

    CCM_INTRA_WALLET_USER   ("CCMIU"),
    CCP_INTRA_WALLET_USER   ("CCPIU"),

    DAP_ASSET_ISSUER        ("DAPASIS"),
    DAP_ASSET_REDEEM_POINT  ("DAPARP"),
    DAP_ASSET_USER          ("DAPASUS"),

    ART_ARTIST              ("AART"),
    ART_FAN                 ("AFAN"),

    CHAT                    ("CHT"),

    LOSS_PROTECTED_USER     ("LPU"),
    BITCOIN_BASIC_USER    ("BBW");


    private final String code;

    Actors(final String code) {
        this.code = code;
    }

    public static Actors getByCode(String code) throws IllegalArgumentException {

        switch (code) {
            case "DUS":     return DEVICE_USER;
            case "EUS":     return EXTRA_USER;
            case "IUS":     return INTRA_USER;
            case "SHP":     return SHOP;

            case "CBPCRBR": return CBP_CRYPTO_BROKER;
            case "CBPCRCU": return CBP_CRYPTO_CUSTOMER;

            case "CCMIU":   return CCM_INTRA_WALLET_USER;
            case "CCPIU":   return CCP_INTRA_WALLET_USER;

            case "DAPASIS": return DAP_ASSET_ISSUER;
            case "DAPARP":  return DAP_ASSET_REDEEM_POINT;
            case "DAPASUS": return DAP_ASSET_USER;

            case "AART":    return ART_ARTIST;
            case "AFAN":    return ART_FAN;

            case "CHT":      return CHAT;

            case "LPU":     return LOSS_PROTECTED_USER;
            case "BBW":     return BITCOIN_BASIC_USER;

            default:
                throw new IllegalArgumentException(
                        "The code"+code+" is not valid for the Actors enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}