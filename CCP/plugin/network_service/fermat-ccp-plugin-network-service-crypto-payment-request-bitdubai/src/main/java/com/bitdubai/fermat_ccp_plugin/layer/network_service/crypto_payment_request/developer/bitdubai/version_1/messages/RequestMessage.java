package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.enums.MessageTypes;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.InformationMessage</code>
 * contains the structure of a Information message for this plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class RequestMessage extends NetworkServiceMessage {

    private final UUID                  requestId        ;
    private final String                identityPublicKey;
    private final Actors                identityType     ;
    private final String                actorPublicKey   ;
    private final Actors                actorType        ;
    private final String                description      ;
    private final CryptoAddress         cryptoAddress    ;
    private final long                  amount           ;
    private final long                  startTimeStamp   ;
    private final RequestAction         action           ;
    private final BlockchainNetworkType networkType      ;
    private final ReferenceWallet      referenceWallet;
    private final String        walletPublicKey;

    public RequestMessage(final UUID                  requestId        ,
                          final String                identityPublicKey,
                          final Actors                identityType     ,
                          final String                actorPublicKey   ,
                          final Actors                actorType        ,
                          final String                description      ,
                          final CryptoAddress         cryptoAddress    ,
                          final long                  amount           ,
                          final long                  startTimeStamp   ,
                          final RequestAction         action           ,
                          final BlockchainNetworkType networkType     ,
                          final ReferenceWallet      referenceWallet,
                          String identitySender,
                          String actorDestination,
                          final String        walletPublicKey) {

        super(MessageTypes.REQUEST,identitySender,actorDestination);

        this.requestId         = requestId        ;
        this.identityPublicKey = identityPublicKey;
        this.identityType      = identityType     ;
        this.actorPublicKey    = actorPublicKey   ;
        this.actorType         = actorType        ;
        this.description       = description      ;
        this.cryptoAddress     = cryptoAddress    ;
        this.amount            = amount           ;
        this.startTimeStamp    = startTimeStamp   ;
        this.action            = action           ;
        this.networkType       = networkType      ;
        this.referenceWallet   = referenceWallet    ;
        this.walletPublicKey = walletPublicKey;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public Actors getIdentityType() {
        return identityType;
    }

    public String getActorPublicKey() {
        return actorPublicKey;
    }

    public Actors getActorType() {
        return actorType;
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

    public RequestAction getAction() {
        return action;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public ReferenceWallet getReferenceWallet() {
        return referenceWallet;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestId=" + requestId +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", identityType=" + identityType +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                ", actorType=" + actorType +
                ", description='" + description + '\'' +
                ", cryptoAddress=" + cryptoAddress +
                ", amount=" + amount +
                ", startTimeStamp=" + startTimeStamp +
                ", action=" + action +
                ", networkType=" + networkType +
                ",referenceWallet=" + referenceWallet +
                '}';
    }

}
