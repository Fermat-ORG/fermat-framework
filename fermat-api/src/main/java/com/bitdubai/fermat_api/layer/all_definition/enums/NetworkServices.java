package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/22/15.
 */
public enum NetworkServices implements FermatEnum {

    //Modified by Manuel Perez on 03/08/2015
    TEMPLATE("TEMPLATE"),
    UNDEFINED("UNDEF"),
    BANK_NOTES("BNOTES"),
    CRYPTO_ADDRESSES("CRYPTADD"),
    INTRA_USER("IUS"),
    MONEY("MONEY"),
    WALLET_COMMUNITY("WALLCOMM"),
    WALLET_RESOURCES("WALLRES"),
    WALLET_STORE("WALLSTO");

    private String code;

    NetworkServices(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NetworkServices getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "TEMPLATE": return NetworkServices.TEMPLATE;
            case "UNDEF":    return NetworkServices.UNDEFINED;
            case "BNOTES":   return NetworkServices.BANK_NOTES;
            case "CRYPTADD": return NetworkServices.CRYPTO_ADDRESSES;
            case "IUS":      return NetworkServices.INTRA_USER;
            case "MONEY":    return NetworkServices.MONEY;
            case "WALLCOMM": return NetworkServices.WALLET_COMMUNITY;
            case "WALLRES":  return NetworkServices.WALLET_RESOURCES;
            case "WALLSTO":  return NetworkServices.WALLET_STORE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NetworkServices enum");
        }
    }
}
