package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus</code>
 * represent the status various for the messages<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum MessageStatus implements FermatEnum {

    /**
     * The enum values
     */
    FAILED          ("F"),
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
    MessageStatus(String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
    @Override
    public String getCode()   { return this.code ; }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static MessageStatus getByCode(String code) {

        switch (code) {
            case "PTS": return PENDING_TO_SEND;
            case "S":   return SENT;
            case "D":   return DELIVERED;
            case "NR":  return NEW_RECEIVED;
            case "R":   return READ;
            case "F":   return FAILED;
            default:    return FAILED;
        }
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
