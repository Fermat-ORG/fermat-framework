package com.bitdubai.fermat_wrd_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_wrd_api.all_definitions.enums.WorldApiType</code>
 * represent the different type of apis found on world super layer.<p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/03/16.
 */
public enum WorldApiType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    TOKENLY("TOK");

    String code;
    WorldApiType(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static WorldApiType getByCode(String code) throws InvalidParameterException {
        for (WorldApiType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ContractTransactionStatus enum.");
    }

    @Override
    public String toString() {
        return "WorldApiType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}
