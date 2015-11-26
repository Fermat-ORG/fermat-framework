package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType;
import com.google.gson.Gson;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceMessage {

    private NetworkServiceMessageType messageType;

    public NetworkServiceMessage() {
    }

    public NetworkServiceMessage(final NetworkServiceMessageType messageType) {
        this.messageType = messageType;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public NetworkServiceMessageType getMessageType() {
        return messageType;
    }
}
