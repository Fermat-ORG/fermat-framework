package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.05..
 */
public class NegotiationTransmissionConnectionRecord {

    private UUID id;
    private String actorPublicKey;
    private String ipkNetworkService;
    private String lastConnection;

    public NegotiationTransmissionConnectionRecord(UUID id, String actorPublicKey, String ipkNetworkService, String lastConnection) {
        this.id = id;
        this.actorPublicKey = actorPublicKey;
        this.ipkNetworkService = ipkNetworkService;
        this.lastConnection = lastConnection;
    }

    public String getActorPublicKey() {
        return actorPublicKey;
    }

    public String getIpkNetworkService() {
        return ipkNetworkService;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public UUID getId() {
        return id;
    }
}
