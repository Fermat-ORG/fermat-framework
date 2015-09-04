/*
 * @#NetworkServiceType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType</code> represent
 * all types that a network service cam be.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum NetworkServiceType {

    // Definition types

    UNDEFINED                       ("UNDEF"),
    NETWORK_SERVICE_TEMPLATE_TYPE   ("NS_TEMP_TYP"),
    NETWORK_SERVICE_INTRA_USER_TYPE ("NS_INT_USR_TYP");

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    private NetworkServiceType(String code){
        this.code = code;
    }

    /**
     * Return the code representation
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * (no-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return this.code;
    }

    /**
     * Return the NetworkServiceType represented by the code pass as parameter
     *
     * @param code
     * @return PlatformComponentType
     */
    public static NetworkServiceType getByCode(final String code){

        switch (code){

            case "UNDEF"          : return NetworkServiceType.UNDEFINED;
            case "NS_TEMP_TYP"    : return NetworkServiceType.NETWORK_SERVICE_TEMPLATE_TYPE;
            case "NS_INT_USR_TYP" : return NetworkServiceType.NETWORK_SERVICE_INTRA_USER_TYPE;
            default: throw new IllegalArgumentException();
        }
    }
}
