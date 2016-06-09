package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/11/15.
 */
public enum AssetMovementType implements FermatEnum, Serializable {

    //ENUM DECLARATION

    ASSET_REDEEMED("ARED"),
    ASSET_APPROPIATED("AAPP"),
    ASSET_SOLD("ASOL"),
    ASSET_TRANSFERRED("ATRA"),;
    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    AssetMovementType(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static AssetMovementType getByCode(String code) throws InvalidParameterException {
        for (AssetMovementType fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DAPMessageType enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
