package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord</code>
 * is the representation of a Crypto Payment Request Record in database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceRecord implements CryptoPaymentRequest {

    private final UUID requestId;
    private final String identityPublicKey;
    private final Actors identityType;
    private final String actorPublicKey;
    private final Actors actorType;
    private final String description;
    private final CryptoAddress cryptoAddress;
    private final long amount;
    private final long startTimeStamp;
    private final RequestType direction;
    private final RequestAction action;
    private final RequestProtocolState protocolState;
    private final BlockchainNetworkType networkType;
    private final ReferenceWallet referenceWallet;
    private final int sentNumber;
    private final String messageType;
    private final String walletPublicKey;
    private final CryptoCurrency cryptoCurrency;

    public CryptoPaymentRequestNetworkServiceRecord(final UUID requestId,
                                                    final String identityPublicKey,
                                                    final Actors identityType,
                                                    final String actorPublicKey,
                                                    final Actors actorType,
                                                    final String description,
                                                    final CryptoAddress cryptoAddress,
                                                    final long amount,
                                                    final long startTimeStamp,
                                                    final RequestType direction,
                                                    final RequestAction action,
                                                    final RequestProtocolState protocolState,
                                                    final BlockchainNetworkType networkType,
                                                    final ReferenceWallet referenceWallet,
                                                    final int sentNumber,
                                                    final String messageType,
                                                    final String walletPublicKey, CryptoCurrency cryptoCurrency) {

        this.requestId = requestId;
        this.identityPublicKey = identityPublicKey;
        this.identityType = identityType;
        this.actorPublicKey = actorPublicKey;
        this.actorType = actorType;
        this.description = description;
        this.cryptoAddress = cryptoAddress;
        this.amount = amount;
        this.startTimeStamp = startTimeStamp;
        this.direction = direction;
        this.action = action;
        this.protocolState = protocolState;
        this.networkType = networkType;
        this.referenceWallet = referenceWallet;
        this.sentNumber = sentNumber;
        this.messageType = messageType;
        this.walletPublicKey = walletPublicKey;
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    @Override
    public Actors getIdentityType() {
        return identityType;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public Actors getActorType() {
        return actorType;
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
    public RequestType getDirection() {
        return direction;
    }

    @Override
    public RequestAction getAction() {
        return action;
    }

    @Override
    public RequestProtocolState getProtocolState() {
        return protocolState;
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
    public int getSentNumber() {
        return sentNumber;
    }

    @Override
    public String getMessageType() {
        return this.messageType;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }


    @Override
    public String toString() {
        return "CryptoPaymentRequestNetworkServiceRecord{" +
                "requestId=" + requestId +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", identityType=" + identityType +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                ", actorType=" + actorType +
                ", description='" + description + '\'' +
                ", cryptoAddress=" + cryptoAddress +
                ", amount=" + amount +
                ", startTimeStamp=" + startTimeStamp +
                ", direction=" + direction +
                ", action=" + action +
                ", protocolState=" + protocolState +
                ", networkType=" + networkType +
                ", walletPublicKey=" + walletPublicKey +
                '}';
    }
}
