package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.google.gson.Gson;

/**
 * Created by rodrigo on 10/8/15.
 */
public class NetworkServiceMessage {

    private org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType messageType;

    public NetworkServiceMessage() {
    }

    public NetworkServiceMessage(final org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType messageType) {
        this.messageType = messageType;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType getMessageType() {
        return messageType;
    }
}
