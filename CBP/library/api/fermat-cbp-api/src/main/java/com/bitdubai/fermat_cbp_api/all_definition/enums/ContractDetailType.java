package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 */
public enum ContractDetailType implements FermatEnum {

    BROKER_DETAIL("BDET"),
    CUSTOMER_DETAIL("CDET");

    String code;

    ContractDetailType(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static ContractDetailType getByCode(String code) throws InvalidParameterException {
        for (ContractDetailType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null,
                new StringBuilder().append("Code Received: ").append(code).toString(),
                "This Code Is Not Valid for the ContractDetailType enum.");
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ContractDetailType{").append("code='").append(code).append('\'').append('}').toString();
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}
