package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceConnectionRecord</code>
 * represents a crypto payment request network service connection.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceConnectionRecord {

    private final UUID   id               ;
    private final String actorPublicKey   ;
    private final String ipkNetworkService;
    private final String lastConnection   ;

    public CryptoPaymentRequestNetworkServiceConnectionRecord(final UUID   id               ,
                                                              final String actorPublicKey   ,
                                                              final String ipkNetworkService,
                                                              final String lastConnection   ) {

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
