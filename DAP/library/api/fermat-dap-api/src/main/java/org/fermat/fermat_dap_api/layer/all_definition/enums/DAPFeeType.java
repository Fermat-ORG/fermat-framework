package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 13/5/15.
 */
public enum DAPFeeType {

    //ENUM DECLARATION

//    SLOW("Slow", 1000),
//    NORMAL("Normal", 2000),
//    FAST("Fast", 3000);

    SLOW("Slow", 20000),
    NORMAL("Normal", 25000),
    FAST("Fast", 30000);
    //VARIABLE DECLARATION

    private String description;
    private long feeValue;

    DAPFeeType(String description, long feeValue) {
        this.description = description;
        this.feeValue = feeValue;
    }

    public String getDescription() {
        return description;
    }

    public long getFeeValue() {
        return feeValue;
    }

    public static DAPFeeType findByFeeValue(long value) throws InvalidParameterException {
        for (DAPFeeType fenum : values()) {
            if (fenum.getFeeValue() == value) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Value Received: " + value, "This Fee value Is Not Valid for the DAPFeeType enum.");
    }
}
