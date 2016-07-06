package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by eze on 2015.09.15..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 29/09/2015.
 */
public class IncomingIntraUserTransactionWrapper implements CryptoWalletTransactionRecord {

    private final UUID          transactionId     ;
    private final UUID          requestId     ;
    private final String        actorFromPublicKey;
    private final String        actorToPublicKey  ;
    private final Actors        actorFromType     ;
    private final Actors        actorToType       ;
    private final String        transactionHash   ;
    private final CryptoAddress addressFrom       ;
    private final CryptoAddress addressTo         ;
    private final long          amount            ;
    private final long          timestamp         ;
    private final String        memo              ;
    private final BlockchainNetworkType blockchainNetworkType;
    private final CryptoCurrency cryptoCurrency;
    private final FeeOrigin feeOrigin;
    private final long        fee;
    private final long total;

    public IncomingIntraUserTransactionWrapper(final UUID transactionId,
                                               final UUID requestId,
                                               final String actorFromPublicKey,
                                               final String actorToPublicKey,
                                               final Actors actorFromType,
                                               final Actors actorToType,
                                               final String transactionHash,
                                               final CryptoAddress addressFrom,
                                               final CryptoAddress addressTo,
                                               final long amount,
                                               final long timestamp,
                                               final String memo,
                                               final BlockchainNetworkType blockchainNetworkType, CryptoCurrency cryptoCurrency,
                                               FeeOrigin feeOrigin,
                                               long fee, long total) {

        this.transactionId      = transactionId     ;
        this.requestId          = requestId         ;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey   = actorToPublicKey  ;
        this.actorFromType      = actorFromType     ;
        this.actorToType        = actorToType       ;
        this.transactionHash    = transactionHash   ;
        this.addressFrom        = addressFrom       ;
        this.addressTo          = addressTo         ;
        this.amount             = amount            ;
        this.timestamp          = timestamp         ;
        this.memo               = memo              ;
        this.blockchainNetworkType = blockchainNetworkType;
        this.cryptoCurrency = cryptoCurrency;
        this.feeOrigin = feeOrigin;
        this.fee = fee;
        this.total = total;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return this.feeOrigin;
    }

    @Override
    public long getFee() {
        return this.fee;
    }


}