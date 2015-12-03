/*
 * @#MessageContentType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.MessageContentType</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum MessageContentType {

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
    MessageContentType(String code) {
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
    public static MessageContentType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "TXT":
                return MessageContentType.TEXT;
            case "BYTE":
                return MessageContentType.BYTE;
            case "IMG":
                return MessageContentType.IMAGE;
            case "VIDEO":
                return MessageContentType.VIDEO;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
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
