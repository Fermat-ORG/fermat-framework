package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by mati on 2016.02.04..
 */
public enum FermatApps implements FermatEnum {


    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITCOIN_REFERENCE_WALLET("BRW"),
    BITCOIN_LOST_PROTECTED_WALLET("BLPW"),
    INTRA_WALLET_USER_IDENTITY("IWUI"),
    INTRA_WALLET_USER_COMMUNITY("IWUC"),
    MAIN_DESKTOP("MD");
    ;

    private final String code;

    FermatApps(final String code) {
        this.code = code;
    }

    public static FermatApps getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "BRW":
                return BITCOIN_REFERENCE_WALLET;
            case "BLPW":
                return BITCOIN_LOST_PROTECTED_WALLET;
            case "IWUI":
                return INTRA_WALLET_USER_IDENTITY;
            case "IWUC":
                return INTRA_WALLET_USER_COMMUNITY;
            case "MD":
                return MAIN_DESKTOP;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the Platforms enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
