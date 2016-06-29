package org.fermat.fermat_dap_api.layer.dap_wallet.common.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 19/01/16.
 */
public enum TransactionType implements FermatEnum, Serializable {

    //ENUM DECLARATION

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    TransactionType(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static TransactionType getByCode(String code) throws InvalidParameterException {
        for (TransactionType fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TransactionType enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
