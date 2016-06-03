package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.message;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.UUID;

/**
 * Created by Nerio on 23/11/15.
 */
public class NetworkServiceMessageRequest extends NetworkServiceMessage {

    private final UUID requestId;
    private final Actors identityTypeRequesting;
    private final Actors identityTypeResponding;
    private final String identityPublicKeyRequesting;
    private final String identityPublicKeyResponding;

    public NetworkServiceMessageRequest(final UUID requestId,
                                        final Actors identityTypeRequesting,
                                        final Actors identityTypeResponding,
                                        final String identityPublicKeyRequesting,
                                        final String identityPublicKeyResponding) {

        super(org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums.NetworkServiceMessageType.REQUEST);

        this.requestId = requestId;
        this.identityTypeRequesting = identityTypeRequesting;
        this.identityTypeResponding = identityTypeResponding;
        this.identityPublicKeyRequesting = identityPublicKeyRequesting;
        this.identityPublicKeyResponding = identityPublicKeyResponding;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public Actors getIdentityTypeRequesting() {
        return identityTypeRequesting;
    }

    public Actors getIdentityTypeResponding() {
        return identityTypeResponding;
    }

    public String getIdentityPublicKeyRequesting() {
        return identityPublicKeyRequesting;
    }

    public String getIdentityPublicKeyResponding() {
        return identityPublicKeyResponding;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestId=" + requestId +
                ", identityTypeRequesting=" + identityTypeRequesting +
                ", identityTypeResponding=" + identityTypeResponding +
                ", identityPublicKeyRequesting='" + identityPublicKeyRequesting + '\'' +
                ", identityPublicKeyResponding='" + identityPublicKeyResponding + '\'' +
                '}';
    }
}
