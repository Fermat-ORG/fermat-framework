package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public enum OpenContractStatus implements FermatEnum {

    OPEN_CONTRACT_ORDERED("OCO"),;

    String code;

    OpenContractStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static OpenContractStatus getByCode(String code) throws InvalidParameterException {
        for (OpenContractStatus value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the OpenContractStatus enum.");
    }

    @Override
    public String toString() {
        return new StringBuilder().append("OpenContractStatus{").append("code='").append(code).append('\'').append('}').toString();
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}
