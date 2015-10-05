package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord</code>
 * is the representation of a Crypto Payment Request Record in database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceRecord {

    private final UUID                 requestId        ;
    private final String               walletPublicKey  ;
    private final String               identityPublicKey;
    private final String               actorPublicKey   ;
    private final String               description      ;
    private final CryptoAddress        cryptoAddress    ;
    private final long                 amount           ;
    private final long                 startTimeStamp   ;
    private final long                 endTimeStamp     ;
    private final CryptoPaymentType    type             ;
    private final CryptoPaymentState   state            ;
    private final RequestProtocolState protocolState    ;

    public CryptoPaymentRequestNetworkServiceRecord(final UUID                 requestId        ,
                                                    final String               walletPublicKey  ,
                                                    final String               identityPublicKey,
                                                    final String               actorPublicKey   ,
                                                    final String               description      ,
                                                    final CryptoAddress        cryptoAddress    ,
                                                    final long                 amount           ,
                                                    final long                 startTimeStamp   ,
                                                    final long                 endTimeStamp     ,
                                                    final CryptoPaymentType    type             ,
                                                    final CryptoPaymentState   state            ,
                                                    final RequestProtocolState protocolState    ) {

        this.requestId         = requestId        ;
        this.walletPublicKey   = walletPublicKey  ;
        this.identityPublicKey = identityPublicKey;
        this.actorPublicKey    = actorPublicKey   ;
        this.description       = description      ;
        this.cryptoAddress     = cryptoAddress    ;
        this.amount            = amount           ;
        this.startTimeStamp    = startTimeStamp   ;
        this.endTimeStamp      = endTimeStamp     ;
        this.type              = type             ;
        this.state             = state            ;
        this.protocolState     = protocolState    ;
    }

   
    public UUID getRequestId() {
        return requestId;
    }

   
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

   
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

   
    public String getActorPublicKey() {
        return actorPublicKey;
    }

   
    public String getDescription() {
        return description;
    }

   
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

   
    public long getAmount() {
        return amount;
    }

   
    public long getStartTimeStamp() {
        return startTimeStamp;
    }

   
    public long getEndTimeStamp() {
        return endTimeStamp;
    }

   
    public CryptoPaymentType getType() {
        return type;
    }

   
    public CryptoPaymentState getState() {
        return state;
    }

   
    public RequestProtocolState getProtocolState() {
        return protocolState;
    }
}
