package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesNetworkServiceConnectionRecord</code>
 * represents a crypto payment request network service connection.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public class CryptoAddressesNetworkServiceConnectionRecord {

    private final UUID   id               ;
    private final String actorPublicKey   ;
    private final String ipkNetworkService;
    private final String lastConnection   ;

    public CryptoAddressesNetworkServiceConnectionRecord(final UUID id,
                                                         final String actorPublicKey,
                                                         final String ipkNetworkService,
                                                         final String lastConnection) {

        this.id                = id               ;
        this.actorPublicKey    = actorPublicKey   ;
        this.ipkNetworkService = ipkNetworkService;
        this.lastConnection    = lastConnection   ;
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
