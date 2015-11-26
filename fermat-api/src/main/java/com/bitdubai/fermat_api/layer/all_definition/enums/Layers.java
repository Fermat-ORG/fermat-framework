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
     * In order to do make code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ACTOR                      ("ACT"),
    ACTOR_CONNECTION           ("ACC"),
    ACTOR_NETWORK_SERVICE      ("ANS"),
    BASIC_WALLET               ("BSW"),
    COMMUNICATION              ("COM"),
    CRYPTO_MODULE              ("CRM"),
    CRYPTO_NETWORK             ("CRN"),
    CRYPTO_ROUTER              ("CRR"),
    CRYPTO_VAULT               ("CRV"),
    DEFINITION                 ("DEF"),
    DESKTOP_MODULE             ("DKM"),
    DIGITAL_ASSET_TRANSACTION  ("DAT"),
    ENGINE                     ("ENG"),
    HARDWARE                   ("HAR"),
    IDENTITY                   ("IDT"),
    MIDDLEWARE                 ("MID"),
    NEGOTIATION                ("NEG"),
    NETWORK_SERVICE            ("NTS"),
    PLATFORM_SERVICE           ("PMS"),
    REQUEST                    ("REQ"),
    SUB_APP_MODULE             ("SAM"),
    SYSTEM                     ("SYS"),
    TRANSACTION                ("TRA"),
    USER                       ("USR"),
    WALLET                     ("WAL"),
    WALLET_MODULE              ("WAM"),
    WORLD                      ("WRL"),

    ;

    private final String code;

    Layers(final String code) {
        this.code = code;
    }

    public static Layers getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ACT":  return ACTOR;
            case "ACC":  return ACTOR_CONNECTION;
            case "ANS":  return ACTOR_NETWORK_SERVICE;
            case "BSW":  return BASIC_WALLET;
            case "COM":  return COMMUNICATION;
            case "CRM":  return CRYPTO_MODULE;
            case "CRN":  return CRYPTO_NETWORK;
            case "CRR":  return CRYPTO_ROUTER;
            case "CRV":  return CRYPTO_VAULT;
            case "DAT":  return DIGITAL_ASSET_TRANSACTION;
            case "DEF":  return DEFINITION;
            case "DKM":  return DESKTOP_MODULE;
            case "ENG":  return ENGINE;
            case "HAR":  return HARDWARE;
            case "IDT":  return IDENTITY;
            case "MID":  return MIDDLEWARE;
            case "NEG":  return NEGOTIATION;
            case "NTS":  return NETWORK_SERVICE;
            case "PMS":  return PLATFORM_SERVICE;
            case "REQ":  return REQUEST;
            case "SAM":  return SUB_APP_MODULE;
            case "SYS":  return SYSTEM;
            case "TRA":  return TRANSACTION;
            case "USR":  return USER;
            case "WAL":  return WALLET;
            case "WAM":  return WALLET_MODULE;
            case "WRL":  return WORLD;

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