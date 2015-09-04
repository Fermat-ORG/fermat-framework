package com.bitdubai.fermat_api.all_definition.digital_asset.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 9/4/15.
 */
public enum State {
    DRAFT ("DFT"),
    PENDING_FINAL ("PFI"),
    FINAL ("FIN");

    private String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static State getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "DFT":
                return State.DRAFT;
            case "PFI":
                return State.PENDING_FINAL;
            case "FIN":
                return State.FINAL;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the State enum.");
        }
    }
}
