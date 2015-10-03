/*
 * @#DescriptorFactoryProjectType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>FactoryProjectType</code> define
 * all the types have a Descriptor Factory Project.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum FactoryProjectType {

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
    FactoryProjectType(String code) {
        this.code = code;
    }

    /**
     * Get the code representation
     *
     * @return String
     */
    public String getCode()   { return this.code; }

    /**
     * Get the FactoryProjectType representation from code
     *
     * @param code
     * @return FactoryProjectType
     * @throws InvalidParameterException
     */
    public static FactoryProjectType getByCode(String code) throws InvalidParameterException {

        switch(code) {
            case"W":
                return WALLET;
            case"S":
                return SKIN;
            case"L":
                return LANGUAGE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the FactoryProjectType enum");

        }
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
}