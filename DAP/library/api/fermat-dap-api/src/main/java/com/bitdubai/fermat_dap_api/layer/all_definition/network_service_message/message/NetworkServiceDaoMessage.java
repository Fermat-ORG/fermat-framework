package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.message;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceDaoMessage  {
    NetworkServiceMessageType messageType;
    Actors sourceActor;
    Actors destinationActor;
    Object messageContent;
}