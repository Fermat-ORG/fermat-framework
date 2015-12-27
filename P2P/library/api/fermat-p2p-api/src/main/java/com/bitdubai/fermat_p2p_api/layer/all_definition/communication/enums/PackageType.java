/*
 * @#PackageType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType</code> represent
 * all type can be a <code>Package</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum PackageType {

    // Definition types

    /*
     * Request messages types
     */
    REQUEST_CHECK_IN_CLIENT,
    REQUEST_CHECK_IN_NETWORK_SERVICE,
    REQUEST_CHECK_IN_ACTOR,
    REQUEST_NETWORK_SERVICE_LIST,
    REQUEST_ACTOR_LIST,
    REQUEST_NEAR_NODE_LIST,

    /*
     * Respond messages types
     */
    RESPOND_CHECK_IN_CLIENT,
    RESPOND_CHECK_IN_NETWORK_SERVICE,
    RESPOND_CHECK_IN_ACTOR,
    RESPOND_NETWORK_SERVICE_LIST,
    RESPOND_ACTOR_LIST,
    RESPOND_NEAR_NODE_LIST

}
