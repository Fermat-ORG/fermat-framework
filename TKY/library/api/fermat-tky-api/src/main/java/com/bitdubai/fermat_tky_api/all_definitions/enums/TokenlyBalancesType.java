package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyBalancesType</code>
 * represent the different type of balances found on api tokenly.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public enum TokenlyBalancesType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    CONFIRMED       ("confirmed"),
    SENDING         ("sending"),
    UNCONFIRMED     ("unconfirmed");

    String code;
    TokenlyBalancesType(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static TokenlyBalancesType getByCode(String code) throws InvalidParameterException {
        for (TokenlyBalancesType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the TokenlyBalancesType enum.");
    }

    @Override
    public String toString() {
        return "TokenlyBalancesType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}

