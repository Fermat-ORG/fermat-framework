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
    CHECK_IN_CLIENT_REQUEST,
    CHECK_IN_NETWORK_SERVICE_REQUEST,
    CHECK_IN_ACTOR_REQUEST,
    NETWORK_SERVICE_LIST_REQUEST,
    ACTOR_LIST_REQUEST,
    NEAR_NODE_LIST_REQUEST,
    CHECK_IN_PROFILE_DISCOVERY_QUERY_REQUEST,
    ACTOR_TRACE_DISCOVERY_QUERY_REQUEST,

    /*
     * Respond messages types
     */
    CHECK_IN_CLIENT_RESPOND,
    CHECK_IN_NETWORK_SERVICE_RESPOND,
    CHECK_IN_ACTOR_RESPOND,
    NETWORK_SERVICE_LIST_RESPOND,
    ACTOR_LIST_RESPOND,
    NEAR_NODE_LIST_RESPOND,
    CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPOND,
    ACTOR_TRACE_DISCOVERY_QUERY_RESPOND,

}
