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
            case "DUS":     return Actors.DEVICE_USER;
            case "IUS":     return Actors.INTRA_USER;
            case "EUS":     return Actors.EXTRA_USER;
            case "SHP":     return Actors.SHOP;
            case "DAPASIS": return Actors.DAP_ASSET_ISSUER;
            case "DAPASUS": return Actors.DAP_ASSET_USER;
            case "DAPARP":  return Actors.DAP_ASSET_REDEEM_POINT;
            case "CCPIU":   return Actors.CCP_INTRA_WALLET_USER;
            case "CCMIU":   return Actors.CCM_INTRA_WALLET_USER;
            //Modified by Manuel Perez on 03/08/2015
            //default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Actors enum");
        }
        /**
         * Return by default.
         */
        return Actors.DEVICE_USER;
    }
}
