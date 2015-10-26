package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by loui on 22/02/15.
 */
public enum Developers implements FermatEnum {

    //Modified by Manuel Perez on 03/08/2015
    BITDUBAI("BitDubai");

    private String code;

    Developers(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static Developers getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BitDubai": return Developers.BITDUBAI;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Developers enum");
        }
    }
}
