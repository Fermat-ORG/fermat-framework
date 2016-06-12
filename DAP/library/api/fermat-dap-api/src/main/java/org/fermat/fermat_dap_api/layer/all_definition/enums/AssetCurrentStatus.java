package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 01/12/15.
 */
public enum AssetCurrentStatus implements FermatEnum, Serializable {

    //ENUM DECLARATION
    ASSET_CREATED("ASCR", "Created"),
    ASSET_APPROPRIATED("ASAP", "Appropriated"),
    ASSET_REDEEMED("ASRE", "Redeemed"),
    ASSET_UNUSED("ASUN", "Unused");

    //VARIABLE DECLARATION

    private String code;
    private String description;

    //CONSTRUCTORS

    AssetCurrentStatus(String code, String description) {
        this.code = code;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
