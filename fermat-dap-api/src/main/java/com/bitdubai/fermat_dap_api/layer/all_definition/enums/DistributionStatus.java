package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/09/15.
 */
public enum DistributionStatus {
    CHECKING_HASH("CHASH");

    private String code;

    DistributionStatus(String code){
        this.code=code;
    }
    public String getCode() { return this.code ; }

    public static DistributionStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "CHASH":
                return DistributionStatus.CHECKING_HASH;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DistributionStatus enum.");
        }
    }
}
