package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 26.01.15.
 */
public enum WalletModule implements FermatEnum {

    DEFAULT("DEF"),
    AGE_KIDS_ALL("AKA"),
    AGE_TEEN_ALL("ATA"),
    AGE_YOUNG_ALL("AYA"),
    AGE_ADULT_ALL("ADA"),
    AGE_SENIOR_ALL("ASA");

    private String code;

    WalletModule(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static WalletModule getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AKA": return WalletModule.AGE_KIDS_ALL;
            case "ATA": return WalletModule.AGE_TEEN_ALL;
            case "AYA": return WalletModule.AGE_YOUNG_ALL;
            case "ADA": return WalletModule.AGE_ADULT_ALL;
            case "ASA": return WalletModule.AGE_SENIOR_ALL;
            case "DEF": return WalletModule.DEFAULT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NicheWallet enum");
        }
        //return DEFAULT;
    }
}
