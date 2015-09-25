package com.bitdubai.fermat_api.layer.ccp_module;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Modules {

    //Modified by Manuel Perez on 05/08/2015
    WALLET_MANAGER ("WALLMAN"),
    WALLET_RUNTIME ("WALLRUN"),
    WALLET_STORE ("WALLSTO"),
    WALLET_FACTORY ("WALLFAC"),
    WALLET_PUBLISHER ("WALLPUB");

    private String code;

    Modules(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code; }

    public static Modules getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "WALLMAN":
                return Modules.WALLET_MANAGER;
            case "WALLRUN":
                return Modules.WALLET_RUNTIME;
            case "WALLSTO":
                return Modules.WALLET_STORE;
            case "WALLFAC":
                return Modules.WALLET_FACTORY;
            case "WALLPUB":
                return Modules.WALLET_PUBLISHER;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Modules enum");

        }

    }
}
