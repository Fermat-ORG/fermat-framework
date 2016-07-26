package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 23/11/2015
 */

public enum ContractStatus implements FermatEnum {
    CANCELLED("CAN", "Cancelled"),
    COMPLETED("COM", "Completed"),
    MERCHANDISE_SUBMIT("MES", "Merchandise submitted"),
    PAUSED("PSD", "Paused"),
    PENDING_MERCHANDISE("PEM", "Pending merchandise"),
    PENDING_PAYMENT("PEN", "Pending payment"),
    PAYMENT_SUBMIT("PYS", "Payment submitted"),
    READY_TO_CLOSE("RTC", "Ready to close"),;

    private final String code;
    private final String friendlyName;

    ContractStatus(String code, String friendlyName) {

        this.code = code;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * This method returns the enum friendly name.
     *
     * @return
     */
    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static ContractStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CAN":
                return ContractStatus.CANCELLED;
            case "COM":
                return ContractStatus.COMPLETED;
            case "MES":
                return ContractStatus.MERCHANDISE_SUBMIT;
            case "PSD":
                return ContractStatus.PAUSED;
            case "PEM":
                return ContractStatus.PENDING_MERCHANDISE;
            case "PEN":
                return ContractStatus.PENDING_PAYMENT;
            case "PYS":
                return ContractStatus.PAYMENT_SUBMIT;
            case "RTC":
                return ContractStatus.READY_TO_CLOSE;

            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ContactState enum");
        }
    }
}