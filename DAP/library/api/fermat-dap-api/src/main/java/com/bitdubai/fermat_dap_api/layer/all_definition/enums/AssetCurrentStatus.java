package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 01/12/15.
 */
public enum AssetCurrentStatus implements FermatEnum {

    //ENUM DECLARATION
    ASSET_CREATED("ASCR"),
    ASSET_APPROPRIATED("ASAP"),
    ASSET_REDEEMED("ASRE"),
    ASSET_UNUSED("ASUN");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    AssetCurrentStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static AssetCurrentStatus getByCode(String code) throws InvalidParameterException {
        for (AssetCurrentStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AssetCurrentStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
