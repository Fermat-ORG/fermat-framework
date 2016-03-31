package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public enum TokenlyCurrency implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    BITCOIN     ("BTC"),
    DEVPARTY    ("DEVPARTY"),
    LTBCOIN     ("LTBCOIN"),
    TATIANACOIN ("TATIANACOIN");

    String code;
    TokenlyCurrency(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static TokenlyCurrency getByCode(String code) throws InvalidParameterException {
        for (TokenlyCurrency value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the TokenlyCurrency enum.");
    }

    @Override
    public String toString() {
        return "TokenlyCurrency{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}
