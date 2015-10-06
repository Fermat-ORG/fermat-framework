/*
 * @#ComponentPublishedInformationStatus.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus</code> define
 * all the status have a Wallet Published Information
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum ComponentPublishedInformationStatus {

    /**
     *  Definitions of status
     */
    NO_PUBLISHED   ("NP"),
    REQUESTED      ("RQ"),
    UNDER_REVISION ("UR"),
    PUBLISHED      ("PB"),
    REJECTED       ("RJ");

    /**
     * Represent the key
     */
    private String code;

    /**
     * Constructor
     *
     * @param code
     */
    ComponentPublishedInformationStatus(String code) {
        this.code = code;
    }

    /**
     * Get the code representation
     *
     * @return String
     */
    public String getCode()   { return this.code; }

    /**
     * Get the ComponentPublishedInformationStatus representation from code
     *
     * @param code
     * @return ComponentPublishedInformationStatus
     * @throws InvalidParameterException
     */
    public static ComponentPublishedInformationStatus getByCode(String code) throws InvalidParameterException {

        switch(code) {
            case"NP":
                return NO_PUBLISHED;
            case"RQ":
                return REQUESTED;
            case"UR":
                return UNDER_REVISION;
            case"PB":
                return PUBLISHED;
            case"RJ":
                return REJECTED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ComponentPublishedInformationStatus enum");

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
