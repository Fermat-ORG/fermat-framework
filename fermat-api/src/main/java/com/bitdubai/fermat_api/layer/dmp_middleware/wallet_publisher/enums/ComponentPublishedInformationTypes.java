/*
 * @#ComponentPublishedInformationTypes.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationTypes</code> define
 * all the types have a Wallet Published Information
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum ComponentPublishedInformationTypes {

    /**
     *  Definitions types
     */
    WALLET   ("W"),
    SKIN     ("S"),
    LANGUAGE ("L");

    /**
     * Represent the key
     */
    private String code;

    /**
     * Constructor
     *
     * @param code
     */
    ComponentPublishedInformationTypes(String code) {
        this.code = code;
    }

    /**
     * Get the code representation
     *
     * @return String
     */
    public String getCode()   { return this.code; }

    /**
     * Get the ComponentPublishedInformationTypes representation from code
     *
     * @param code
     * @return ComponentPublishedInformationTypes
     * @throws InvalidParameterException
     */
    public static ComponentPublishedInformationTypes getByCode(String code) throws InvalidParameterException {

        switch(code) {
            case"W":
                return WALLET;
            case"S":
                return SKIN;
            case"L":
                return LANGUAGE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ComponentPublishedInformationTypes enum");

        }
    }

}
