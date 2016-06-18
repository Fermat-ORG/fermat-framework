package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.message;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceDaoMessage {
    org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType messageType;
    Actors sourceActor;
    Actors destinationActor;
    Object messageContent;
}