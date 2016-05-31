package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
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

    private final UUID                  requestId        ;
    private final String                walletPublicKey  ;
    private final String                identityPublicKey;
    private final Actors                identityType     ;
    private final String                actorPublicKey   ;
    private final Actors                actorType        ;
    private final String                description      ;
    private final CryptoAddress         cryptoAddress    ;
    private final long                  amount           ;
    private final long                  startTimeStamp   ;
    private final long                  endTimeStamp     ;
    private final CryptoPaymentType     type             ;
    private final CryptoPaymentState    state            ;
    private final BlockchainNetworkType networkType      ;
    private final ReferenceWallet       referenceWallet  ;
    private final CryptoCurrency        cryptoCurrency  ;

    public CryptoPaymentRequestRecord(final UUID requestId,
                                      final String walletPublicKey,
                                      final String identityPublicKey,
                                      final Actors identityType,
                                      final String actorPublicKey,
                                      final Actors actorType,
                                      final String description,
                                      final CryptoAddress cryptoAddress,
                                      final long amount,
                                      final long startTimeStamp,
                                      final long endTimeStamp,
                                      final CryptoPaymentType type,
                                      final CryptoPaymentState state,
                                      final BlockchainNetworkType networkType,
                                      final ReferenceWallet referenceWallet,  CryptoCurrency cryptoCurrency) {

        this.requestId         = requestId        ;
        this.walletPublicKey   = walletPublicKey  ;
        this.identityPublicKey = identityPublicKey;
        this.identityType      = identityType     ;
        this.actorPublicKey    = actorPublicKey   ;
        this.actorType         = actorType        ;
        this.description       = description      ;
        this.cryptoAddress     = cryptoAddress    ;
        this.amount            = amount           ;
        this.startTimeStamp    = startTimeStamp   ;
        this.endTimeStamp      = endTimeStamp     ;
        this.type              = type             ;
        this.state             = state            ;
        this.networkType       = networkType      ;
        this.referenceWallet   = referenceWallet  ;
        this.cryptoCurrency    = cryptoCurrency;
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
    public Actors getIdentityType() {
        return identityType;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    @Override
    public ReferenceWallet getReferenceWallet() {
        return referenceWallet;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }
}
