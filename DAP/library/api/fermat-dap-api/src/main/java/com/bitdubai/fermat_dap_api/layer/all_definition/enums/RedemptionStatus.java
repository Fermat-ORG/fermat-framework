package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.Objects;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 26/10/15.
 */
public enum RedemptionStatus implements FermatEnum {
    STARTING_REDEMPTION("SRP"),
    SENT_TO_REDEEMPOINT("STRP"),

    REJECTED_BY_CONTRACT("RBC"),
    REJECTED_BY_HASH("RBH"),
    UNDEFINED_REJECTION("URJ");

    //VARIABLE DECLARATION
    private String code;
    //CONSTRUCTORS

    RedemptionStatus(String code) {
        this.code = code;
    }


    //PUBLIC METHODS

    public static RedemptionStatus getByCode(String code) throws InvalidParameterException {
        if (code == null) throw new InvalidParameterException("The given code is null.");
        for (RedemptionStatus status : RedemptionStatus.values()) {
            if (Objects.equals(status.code, code)) return status;
        }
        throw new InvalidParameterException("The given code: " + code + " is not registered.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    /**
     * Throw the method <code>getCode</code> you can get the code of the specific element of the enum.
     *
     * @return the code of the enum.
     */
    @Override
    public String getCode() {
        return code;
    }
    //INNER CLASSES
}
