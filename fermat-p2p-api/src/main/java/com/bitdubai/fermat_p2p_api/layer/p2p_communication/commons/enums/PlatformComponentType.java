/*
 * @#PlatformComponentType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType</code> define
 * all types that a platform component cam be
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum PlatformComponentType {

    // Definition types

    COMMUNICATION_CLOUD_CLIENT_COMPONENT ("COM_CLD_CLI_COMP"),
    COMMUNICATION_CLOUD_SERVER_COMPONENT ("COM_CLD_SER_COMP"),
    NETWORK_SERVICE_COMPONENT            ("NS_COMP");

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    private PlatformComponentType(String code){
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
     * Return the PlatformComponentType represented by the code pass as parameter
     *
     * @param code
     * @return PlatformComponentType
     */
    public static PlatformComponentType getByCode(final String code){

        switch (code){

            case "COM_CLD_CLI_COMP" : return PlatformComponentType.COMMUNICATION_CLOUD_CLIENT_COMPONENT;
            case "COM_CLD_SER_COMP" : return PlatformComponentType.COMMUNICATION_CLOUD_SERVER_COMPONENT;
            case "NS_COMP"          : return PlatformComponentType.NETWORK_SERVICE_COMPONENT;
            default: throw new IllegalArgumentException();
        }
    }


}
