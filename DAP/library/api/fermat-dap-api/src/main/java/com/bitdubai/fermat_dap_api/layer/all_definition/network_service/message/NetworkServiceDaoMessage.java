package com.bitdubai.fermat_dap_api.layer.all_definition.network_service.message;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service.message.enums.NetworkServiceDaoMessageType;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceDaoMessage  {
    NetworkServiceDaoMessageType messageType;
    Actors sourceActor;
    Actors destinationActor;
    Object messageContent;
}
