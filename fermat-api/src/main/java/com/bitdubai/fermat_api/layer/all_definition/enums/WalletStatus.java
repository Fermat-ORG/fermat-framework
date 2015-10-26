package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 25.01.15.
 */
public enum WalletStatus implements FermatEnum {

    //Modified by Manuel Perez on 05/08/2015
    CLOSED("CLOSED"),
    ONLINE("ONLINE"),
    OPEN("OPEN");

    private String code;

    WalletStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static WalletStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CLOSED": return WalletStatus.CLOSED;
            case "ONLINE": return WalletStatus.ONLINE;
            case "OPEN":   return WalletStatus.OPEN;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the WalletStatus enum");
        }
    }
}
