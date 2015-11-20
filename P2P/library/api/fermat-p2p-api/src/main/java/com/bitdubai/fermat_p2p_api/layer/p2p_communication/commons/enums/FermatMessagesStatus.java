/*
 * @#FermatMessagesStatus.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus</code>
 * represent the status various for the messages<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum FermatMessagesStatus {

    /**
     * The enum values
     */
    PENDING_TO_SEND ("PTS"),
    SENT            ("S"),
    DELIVERED       ("D"),
    NEW_RECEIVED    ("NR"),
    READ            ("R");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    FermatMessagesStatus(String code) {
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
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static FermatMessagesStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "PTS":
                return FermatMessagesStatus.PENDING_TO_SEND;
            case "S":
                return FermatMessagesStatus.SENT;
            case "D":
                return FermatMessagesStatus.DELIVERED;
            case "NR":
                return FermatMessagesStatus.NEW_RECEIVED;
            case "R":
                return FermatMessagesStatus.READ;
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
