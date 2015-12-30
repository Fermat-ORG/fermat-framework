package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 28/12/15.
 */
public enum ReceptionStatus implements FermatEnum {

    //ENUM DECLARATION
    ASSET_ACCEPTED("DAMA"),
    CHECKING_CONTRACT("CCONT"),
    CHECKING_HASH("CHASH"),
    CONTRACT_CHECKED("CONTC"),
    CRYPTO_RECEIVED("BTCRX"),
    HASH_CHECKED("HASHC"),
    RECEIVING("RXG"),
    REJECTED_BY_CONTRACT("DAMRBC"),
    REJECTED_BY_HASH("RBH"),
    CANCELLED("CNC"),
    UNDEFINED_REJECTION("URJ"),
    RECEPTION_FINISHED("REFI");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    ReceptionStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static ReceptionStatus getByCode(String code) throws InvalidParameterException {
        for (ReceptionStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ReceptionStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
