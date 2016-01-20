package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.util.UUID;

/**
 * Created by Nerio on 23/11/15.
 */
public class NetworkServiceMessageAccept extends NetworkServiceMessage {

    private final UUID requestId;
    private final DAPActor dapActor;

    public NetworkServiceMessageAccept(final UUID requestId, final DAPActor dapActor) {

        super(NetworkServiceMessageType.ACCEPT);

        this.requestId = requestId;
        this.dapActor = dapActor;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public DAPActor getDapActor() {
        return dapActor;
    }

    @Override
    public String toString() {
        return "AcceptMessage{" +
                "requestId=" + requestId +
                ", dapActor=" + dapActor +
                '}';
    }
}