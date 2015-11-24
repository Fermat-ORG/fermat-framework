package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

public enum Actors implements FermatEnum {

    DEVICE_USER("DUS"),
    INTRA_USER("IUS"),
    EXTRA_USER("EUS"),
    SHOP("SHP"),

    DAP_ASSET_ISSUER("DAPASIS"),
    DAP_ASSET_USER("DAPASUS"),
    DAP_ASSET_REDEEM_POINT("DAPARP"),

    CCP_INTRA_WALLET_USER("CCPIU"),
    CCM_INTRA_WALLET_USER("CCMIU"),

    CBP_CRYPTO_BROKER   ("CBPCRBR"),
    CBP_CRYPTO_CUSTOMER ("CBPCRCU"),

    ;

    private String code;

    Actors(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Actors getByCode(String code)/*throws InvalidParameterException*/ {

        switch (code) {
            case "DUS":     return DEVICE_USER;
            case "IUS":     return INTRA_USER;
            case "EUS":     return EXTRA_USER;
            case "SHP":     return SHOP;

            case "DAPASIS": return DAP_ASSET_ISSUER;
            case "DAPASUS": return DAP_ASSET_USER;
            case "DAPARP":  return DAP_ASSET_REDEEM_POINT;

            case "CCPIU":   return CCP_INTRA_WALLET_USER;
            case "CCMIU":   return CCM_INTRA_WALLET_USER;

            case "CBPCRBR": return CBP_CRYPTO_BROKER;
            case "CBPCRCU": return CBP_CRYPTO_CUSTOMER;

            default:
                throw new IllegalArgumentException("The code"+code+" is not valid for the Actors enum.");

        }
    }
}
