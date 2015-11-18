package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/11/15.
 */
public enum TypeRequest  implements FermatEnum {
    CONNECTION("CONN"),
    NEGOTIATION ("NEGO"),
    CONTRACT ("CONT");

    private String code;

    TypeRequest(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static TypeRequest getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CONN": return TypeRequest.CONNECTION;
            case "NEGO": return TypeRequest.NEGOTIATION;
            case "CONT": return TypeRequest.CONTRACT;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TypeRequest enum");
        }
    }
}
