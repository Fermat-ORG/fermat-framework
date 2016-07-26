package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 09/06/15.
 */
public enum Specialist {
    ASSET_ISSUER_SPECIALIST("AIS"),
    ASSET_REDEMPTION_SPECIALIST("ARS"),
    ASSET_USER_SPECIALIST("AUS"),
    CRYPTO_ROUTER_SPECIALIST("CPR"),
    EXTRA_USER_SPECIALIST("EXU"),
    DEVICE_USER_SPECIALIST("DVU"),
    INTRA_USER_SPECIALIST("INU"),
    REDEEM_POINT_SPECIALIST("REP"),
    UNKNOWN_SPECIALIST("UNK");

    private final String code;

    Specialist(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static Specialist getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AIS":
                return Specialist.ASSET_ISSUER_SPECIALIST;
            case "AUS":
                return Specialist.ASSET_USER_SPECIALIST;
            case "ARS":
                return Specialist.ASSET_REDEMPTION_SPECIALIST;
            case "CPR":
                return Specialist.CRYPTO_ROUTER_SPECIALIST;
            case "EXU":
                return Specialist.EXTRA_USER_SPECIALIST;
            case "DVU":
                return Specialist.DEVICE_USER_SPECIALIST;
            case "INU":
                return Specialist.INTRA_USER_SPECIALIST;
            case "REP":
                return Specialist.REDEEM_POINT_SPECIALIST;
            case "UNK":
                return Specialist.UNKNOWN_SPECIALIST;
            //Modified by Manuel Perez on 04/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the Specialist enum");

        }

        /**
         * If we try to cpmvert am invalid string.
         */
        // throw new InvalidParameterException(code);
    }
}
