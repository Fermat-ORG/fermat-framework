package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.22..
 */
public enum WalletType implements FermatEnum {

    NICHE("NICHE"),
    REFERENCE("REFER"),;

    private String code;

    WalletType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static WalletType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "NICHE":
                return WalletType.NICHE;
            case "REFER":
                return WalletType.REFERENCE;
            //Modified by Manuel Perez on 03/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the WalletType enum");
        }
    }
}
