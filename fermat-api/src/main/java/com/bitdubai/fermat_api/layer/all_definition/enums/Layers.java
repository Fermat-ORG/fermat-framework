package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Layers</code>
 * enumerates all the layers type of fermat.
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
    ENGINE          ("ENG"),
    HARDWARE        ("HAR"),
    IDENTITY        ("IDT"),
    MIDDLEWARE      ("MID"),
    NETWORK_SERVICE ("NTS"),
    PLATFORM_SERVICE("PMS"),
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
                case "ACT":  return Layers.ACTOR;
                case "BSW":  return Layers.BASIC_WALLET;
                case "COM":  return Layers.COMMUNICATION;
                case "DEF":  return Layers.DEFINITION;
                case "ENG":  return Layers.ENGINE;
                case "HAR":  return Layers.HARDWARE;
                case "IDT":  return Layers.IDENTITY;
                case "MID":  return Layers.MIDDLEWARE;
                case "NTS":  return Layers.NETWORK_SERVICE;
                case "PMS":  return Layers.PLATFORM_SERVICE;
                case "REQ":  return Layers.REQUEST;
                case "SAM":  return Layers.SUB_APP_MODULE;
                case "TRA":  return Layers.TRANSACTION;
                case "WAM":  return Layers.WALLET_MODULE;
                case "WRL":  return Layers.WORLD;
                default:
                throw new InvalidParameterException("Code Received: " + code,
                        "The received code is not valid for the Layers enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
