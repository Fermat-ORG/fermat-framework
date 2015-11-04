package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.05..
 */
public class CryptoTransmissionConnectionRecord {

    private UUID id;
    private String actorPublicKey;
    private String ipkNetworkService;
    private String lastConnection;

    public CryptoTransmissionConnectionRecord(UUID id,String actorPublicKey, String ipkNetworkService,String lastConnection) {
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
