package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestRecord</code>
 * is the representation of a Crypto Payment Request Record in database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestRecord implements CryptoPayment {

    private final UUID               requestId        ;
    private final String             walletPublicKey  ;
    private final String             identityPublicKey;
    private final String             actorPublicKey   ;
    private final String             description      ;
    private final CryptoAddress      cryptoAddress    ;
    private final long               amount           ;
    private final long               startTimeStamp   ;
    private final long               endTimeStamp     ;
    private final CryptoPaymentType  type             ;
    private final CryptoPaymentState state            ;

    public CryptoPaymentRequestRecord(UUID               requestId        ,
                                      String             walletPublicKey  ,
                                      String             identityPublicKey,
                                      String             actorPublicKey   ,
                                      String             description      ,
                                      CryptoAddress      cryptoAddress    ,
                                      long               amount           ,
                                      long               startTimeStamp   ,
                                      long               endTimeStamp     ,
                                      CryptoPaymentType  type             ,
                                      CryptoPaymentState state            ) {

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
}
