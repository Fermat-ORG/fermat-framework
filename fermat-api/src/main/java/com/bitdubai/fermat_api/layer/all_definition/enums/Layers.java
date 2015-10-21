package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Layers</code>
 * contains all the layers of fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public enum Layers implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ACTOR           ("ACT"),
    BASIC_WALLET    ("BSW"),
    COMMUNICATION   ("COM"),
    DEFINITION      ("DEF"),
    ENGINE          ("WRL"),
    HARDWARE        ("HAR"),
    IDENTITY        ("IDT"),
    MIDDLEWARE      ("MID"),
    NETWORK_SERVICE ("NTS"),
    REQUEST         ("REQ"),
    SUB_APP_MODULE  ("SAM"),
    TRANSACTION     ("TRA"),
    WALLET_MODULE   ("WAM"),
    WORLD           ("WRL"),

    ;

    private String code;

    Layers(final String code) {

        this.code = code;
    }

    public static Layers getByCode(String code) throws InvalidParameterException {

        switch (code) {


            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the Layers enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
