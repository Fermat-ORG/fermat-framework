package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceDaoMessageType;
import com.google.gson.Gson;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceDaoMessage  {

    private NetworkServiceDaoMessageType messageType;

    public NetworkServiceDaoMessage() {
    }

    public NetworkServiceDaoMessage(final NetworkServiceDaoMessageType messageType) {
        this.messageType = messageType;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public NetworkServiceDaoMessageType getMessageType() {
        return messageType;
    }
}
