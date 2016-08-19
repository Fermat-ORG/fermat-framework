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

    // Definition types Client channel

    /*
     * Request packet types
     */

    CHECK_IN_CLIENT_REQUEST, // when a client requests to the node its own registration in the fermat-network
    CHECK_IN_NETWORK_SERVICE_REQUEST, // when a network service requests through the client to the node its own registration in the fermat-network
    CHECK_IN_ACTOR_REQUEST, // when an actor requests through the client to the node its own registration in the fermat-network

    CHECK_OUT_CLIENT_REQUEST, // when a client requests to the node to remove its registration in the fermat-network
    CHECK_OUT_NETWORK_SERVICE_REQUEST, // when a network service requests through the client to the node to remove its registration in the fermat-network
    CHECK_OUT_ACTOR_REQUEST, // when an actor requests through the client to the node to remove its registration in the fermat-network

    NETWORK_SERVICE_LIST_REQUEST, // not implemented yet - > ask for the list of network service registered in the client
    ACTOR_LIST_REQUEST, // is used by the client to ask asynchronously a list of actors to the node with a given query id
    NEAR_NODE_LIST_REQUEST, // is used by the client to ask asynchronously a list of nearly nodes passing a point of reference.

    CHECK_IN_PROFILE_DISCOVERY_QUERY_REQUEST,
    ACTOR_TRACE_DISCOVERY_QUERY_REQUEST,

    MESSAGE_TRANSMIT, // its used by the actors in the network services to send a message through the client to another actor

    ACTOR_CALL_REQUEST, // when an actor is not in the same node, we use this to ask the node the information of the other node

    UPDATE_ACTOR_PROFILE_REQUEST, // is used to update the actor information

    UPDATE_PROFILE_GEOLOCATION_REQUEST, // is used to update only the actor location

    /*
     * Respond packet types
     */
    CHECK_IN_CLIENT_RESPONSE, // response from the node to the client indicating if the registration was successful or not
    CHECK_IN_NETWORK_SERVICE_RESPONSE, // response from the node to the client indicating if the registration was successful or not
    CHECK_IN_ACTOR_RESPONSE, // response from the node to the client indicating if the registration was successful or not

    CHECK_OUT_CLIENT_RESPONSE, // response from the node to the client indicating if the check out (or unregister process) was successful or not
    CHECK_OUT_NETWORK_SERVICE_RESPONSE, // response from the node to the client indicating if the check out (or unregister process) was successful or not
    CHECK_OUT_ACTOR_RESPONSE, // response from the node to the client indicating if the check out (or unregister process) was successful or not

    NETWORK_SERVICE_LIST_RESPONSE, // response with the list of network services (not implemented yet(
    ACTOR_LIST_RESPONSE, // response with the list of actors with the given query id
    NEAR_NODE_LIST_RESPONSE, // response with the list of nearly nodes

    CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPONSE,
    ACTOR_TRACE_DISCOVERY_QUERY_RESPONSE,

    UPDATE_ACTOR_PROFILE_RESPONSE, // response from the node to the client indicating if the update was successful or not

    MESSAGE_TRANSMIT_RESPONSE, // response indicating that the message was sent to the other client or not
    MESSAGE_TRANSMIT_SYNC_ACK_RESPONSE,

    ACTOR_CALL_RESPONSE, // response with the information of the actor with its node data (to contact him).

    // Definition types NODE channel NODE-TO-NODE Processes

    /*
     * Request packet types
     */
    ADD_NODE_TO_CATALOG_REQUEST, // request to the seed node to add the requesting node to the catalog list
    GET_NODE_CATALOG_REQUEST, // when a node starts for the first time ask for the catalog information to fill it
    UPDATE_NODE_IN_CATALOG_REQUEST, // request to the seed node to update the requesting node to the catalog list
    NODES_CATALOG_TO_PROPAGATE_REQUEST, // a node sends to the other the available information to propagate
    NODES_CATALOG_TO_ADD_OR_UPDATE_REQUEST, // a node sends to the other the information requested by it to update its catalog

    GET_ACTOR_CATALOG_REQUEST, // when a node starts for the first time asks for the catalog information to fill it
    ACTOR_CATALOG_TO_PROPAGATE_REQUEST, // a node sends to the other the available information to propagate
    ACTOR_CATALOG_TO_ADD_OR_UPDATE_REQUEST,  // a node sends to the other the information requested by it to update its catalog

    /*
     * Respond packet types
     */
    ADD_NODE_TO_CATALOG_RESPONSE, // the seed node responds with success or not to the request of adding the node
    GET_NODE_CATALOG_RESPONSE, // the node returns the information requested that it contains in its catalog
    UPDATE_NODE_IN_CATALOG_RESPONSE, // the node indicates to its counterpart that the update was successful or not
    NODES_CATALOG_TO_PROPAGATE_RESPONSE, // the node responds to the counterpart if it needs or not the information sent by it

    GET_ACTOR_CATALOG_RESPONSE, // the node returns the information requested that it contains in its catalog
    ACTOR_CATALOG_TO_PROPAGATE_RESPONSE, // the node responds to the counterpart if it needs or not the information sent by it

    SERVER_HANDSHAKE_RESPONSE, // the servers returns a message to the client when the client check in and the connection was successful

    ;


}
