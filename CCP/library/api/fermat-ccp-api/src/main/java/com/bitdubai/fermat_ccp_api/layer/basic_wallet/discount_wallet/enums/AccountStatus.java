package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/24/15.
 */
public enum AccountStatus  {

    CREATED ("CRE"),
    OPEN ("OPN"),
    CLOSED   ("CLO"),
    DELETED ("DEL");

    private final String code;

    AccountStatus(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static AccountStatus getByCode(String code)throws InvalidParameterException {

        switch (code) {
            case "CRE": return AccountStatus.CREATED;
            case "OPN": return AccountStatus.OPEN;
            case "CLO": return AccountStatus.CLOSED;
            case "DEL": return AccountStatus.DELETED;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AccountStatus enum");

        }

        /**
         * Return by default.
         */
        //return AccountStatus.CREATED;
    }
}