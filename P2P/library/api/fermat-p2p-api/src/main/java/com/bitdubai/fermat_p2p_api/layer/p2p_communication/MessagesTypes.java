/*
 * @#MessagesStatus.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * The enum <code>com.bitdubai.fermat_ccp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceMessage</code>
 * represent the status various for the messages<p/>
 *
 * Created by ciencias on 2/23/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum MessagesTypes {

    /**
     * The enum values
     */
    TEXT  ("TXT"),
    BYTE  ("BYTE"),
    IMAGE ("IMG"),
    VIDEO ("VIDEO");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    MessagesTypes(String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
    public String getCode()   { return this.code ; }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesTypes enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static MessagesTypes getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "TXT":
                return MessagesTypes.TEXT;
            case "BYTE":
                return MessagesTypes.BYTE;
            case "IMG":
                return MessagesTypes.IMAGE;
            case "VIDEO":
                return MessagesTypes.VIDEO;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    };

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
    
}
