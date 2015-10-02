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
public class CryptoPaymentRequestNetworkServiceRecord implements CryptoPaymentRequest {

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

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    @Override
    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    @Override
    public CryptoPaymentType getType() {
        return type;
    }

    @Override
    public CryptoPaymentState getState() {
        return state;
    }

    @Override
    public RequestProtocolState getProtocolState() {
        return protocolState;
    }
}
