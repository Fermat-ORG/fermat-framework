/*
 * @#MessageType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageType</code> represent
 * all type can be a <code>Message</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum MessageType {

    // Definition types
    REQUEST_CHECK_IN_CLIENT("RCC"),
    REQUEST_CHECK_IN_NETWORK_SERVICE("RCNS"),
    REQUEST_CHECK_IN_ACTOR("RCA"),
    REQUEST_NETWORK_SERVICE_LIST("RNSL"),
    REQUEST_ACTOR_LIST("RAL")
    ;

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    MessageType(String code){
        this.code = code;
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
     * Return the FermatPacketType represented by the code pass as parameter
     *
     * @param code
     * @return FermatPacketType
     */
    public static MessageType getByCode(final String code){

        switch (code){
           case "RCC"  : return REQUEST_CHECK_IN_CLIENT;
           case "RCNS" : return REQUEST_CHECK_IN_NETWORK_SERVICE;
           case "RCA"  : return REQUEST_CHECK_IN_ACTOR;
           case "RNSL" : return REQUEST_NETWORK_SERVICE_LIST;
           case "RAL"  : return REQUEST_ACTOR_LIST;

           default: throw new IllegalArgumentException();
        }
    }
}
