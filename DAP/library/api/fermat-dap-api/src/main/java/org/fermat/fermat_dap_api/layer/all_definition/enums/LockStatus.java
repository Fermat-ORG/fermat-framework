package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/02/16.
 */
public enum LockStatus implements FermatEnum {

    //ENUM DECLARATION
    LOCKED("LOCKED"),
    UNLOCKED("UNLOCKED");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    LockStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static LockStatus getByCode(String code) throws InvalidParameterException {
        for (LockStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the LockStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
