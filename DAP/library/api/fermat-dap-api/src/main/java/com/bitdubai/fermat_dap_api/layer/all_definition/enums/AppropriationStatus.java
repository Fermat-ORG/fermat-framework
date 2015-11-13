package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public enum AppropriationStatus implements FermatEnum {

    //ENUM DECLARATION

    APPROPRIATION_STARTED("APST"),
    SENDING_BITCOINS("SNDB"),
    DEBITING_ASSET("DBAS"),
    REVERTED_ON_BLOCKCHAIN("ROBC"),
    REVERTED_ON_CRYPTO_NETWORK("ROCN"),
    APPROPRIATION_SUCCESSFUL("APSU"),
    APPROPRIATION_FAILED("APFA");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    AppropriationStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static AppropriationStatus getByCode(String code) throws InvalidParameterException {
        for (AppropriationStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IssuingStatus enum.");
    }

    @Override
    public String toString() {
        return "AppropriationStatus{" +
                "code='" + code + '\'' +
                '}';
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}
