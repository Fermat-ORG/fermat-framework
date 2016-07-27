package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by angel on 22/02/16.
 */

public enum ActorType implements FermatEnum {
    BROKER("BROK"),
    CUSTOMER("CUST");

    private final String code;

    ActorType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ActorType getByCode(String code) {

        switch (code) {
            case "BROK":
                return BROKER;
            case "CUST":
                return CUSTOMER;
            default:
                return BROKER;
        }
    }
}
