package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;

import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 29/09/2015.
 */
public class TransactionWrapper implements BitcoinWalletTransactionRecord {

    private final UUID             transactionId     ;
    private final String           actorFromPublicKey;
    private final String           actorToPublicKey  ;
    private final Actors           actorFromType     ;
    private final Actors           actorToType       ;
    private final String           transactionHash   ;
    private final CryptoAddress    addressFrom       ;
    private final CryptoAddress    addressTo         ;
    private final long             amount            ;
    private final long             timestamp         ;
    private final String           memo              ;
    private final String           walletPublicKey   ;
    private final TransactionState state             ;
    private final CryptoStatus     cryptoStatus      ;

    public TransactionWrapper(final UUID             transactionId     ,
                              final String           actorFromPublicKey,
                              final String           actorToPublicKey  ,
                              final Actors           actorFromType     ,
                              final Actors           actorToType       ,
                              final String           transactionHash   ,
                              final CryptoAddress    addressFrom       ,
                              final CryptoAddress    addressTo         ,
                              final long             amount            ,
                              final long             timestamp         ,
                              final String           memo              ,
                              final String           walletPublicKey   ,
                              final TransactionState state             ,
                              final CryptoStatus     cryptoStatus      ) {

        this.transactionId      = transactionId     ;
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
        this.walletPublicKey    = walletPublicKey   ;
        this.state              = state             ;
        this.cryptoStatus       = cryptoStatus      ;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
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
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public TransactionState getState() {
        return state;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    @Override
    public String toString() {
        return "TransactionWrapper{" +
                "transactionId=" + transactionId +
                ", transactionHash='" + transactionHash + '\'' +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", timestamp=" + timestamp +
                ", state=" + state +
                ", cryptoStatus=" + cryptoStatus +
                '}';
    }
}
