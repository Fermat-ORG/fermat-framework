package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceDaoMessageType;

import java.util.UUID;

/**
 * Created by Nerio on 23/11/15.
 */
public class NetworkServiceMessageDeny extends NetworkServiceDaoMessage {

    private final UUID requestId;
    private final String reason   ;

    public NetworkServiceMessageDeny(final UUID requestId, final String reason) {

        super(NetworkServiceDaoMessageType.DENY);

        this.requestId = requestId;
        this.reason = reason;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "DenyMessage{" +
                "requestId=" + requestId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
