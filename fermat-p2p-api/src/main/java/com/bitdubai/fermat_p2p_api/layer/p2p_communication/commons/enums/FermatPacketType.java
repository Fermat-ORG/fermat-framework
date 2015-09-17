/*
 * @#FMPPacketType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType</code> define
 * all types of Fermat Packet cam be
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum FermatPacketType {

    // Definition types

    /**
     * This type is use when a server handshake respond
     */
    SERVER_HANDSHAKE_RESPOND       ("SRV_HS_RESP"),

    /**
     * This type is use when a component registration request
     */
    COMPONENT_REGISTRATION_REQUEST ("COMP_REG_REQ"),

    /**
     * This type is use when a component registration request is complete
     */
    COMPLETE_REGISTRATION_COMPONENT("COMPL_REG_COMP"),

    /**
     * This type is use when need a list the components registered in the server
     */
    REQUEST_LIST_COMPONENT_REGISTERED("REQ_LIST_COMP_REG"),

    /**
     * This type is use when a component disconnect request is made
     */
    COMPONENT_DISCONNECT_REQUEST   ("COMP_DISC_REQ"),

    /**
     * This type is use when a message delivery notification is made
     */
    MESSAGE_DELIVERY_NOTIFICATION  ("MSG_DEL_NOT"),

    /**
     * This type is use when a component connection request is made
     */
    COMPONENT_CONNECTION_REQUEST   ("COMP_CONNECT_REQ"),

    /**
     * This type is use whe an component send a message to another component
     */
    MESSAGE_TRANSMIT ("MSG_TRANS");

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    private FermatPacketType(String code){
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
    public static FermatPacketType getByCode(final String code){

        switch (code){
            case "SRV_HS_RESP"       : return FermatPacketType.SERVER_HANDSHAKE_RESPOND;
            case "COMP_REG_REQ"      : return FermatPacketType.COMPONENT_CONNECTION_REQUEST;
            case "COMPL_REG_COMP"    : return FermatPacketType.COMPLETE_REGISTRATION_COMPONENT;
            case "REQ_LIST_COMP_REG" : return FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED;
            case "COMP_DISC_REQ"     : return FermatPacketType.COMPONENT_DISCONNECT_REQUEST;
            case "MSG_DEL_NOT"       : return FermatPacketType.MESSAGE_DELIVERY_NOTIFICATION;
            case "COMP_CONNECT_REQ"  : return FermatPacketType.COMPONENT_CONNECTION_REQUEST;
            case "MSG_TRANS"         : return FermatPacketType.MESSAGE_TRANSMIT;

            default: throw new IllegalArgumentException();
        }
    }

}
