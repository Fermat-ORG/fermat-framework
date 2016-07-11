package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 17/03/16.
 */
public class CryptoWalletTransactionWalletRecord implements CryptoWalletTransactionRecord {



    CryptoAddress AddressFrom;

    UUID TransactionId;

    UUID RequestId;

    CryptoAddress AddressTo;

    long Amount;
    long Total;

    long Timestamp;

    String Memo;

    String TransactionHash;

    String ActorToPublicKey;

    String ActorFromPublicKey;

    Actors ActorToType;

    Actors ActorFromType;

    com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType BlockchainNetworkType;

    CryptoCurrency cryptoCurrency;

    private long fee ;
    private FeeOrigin feeOrigin;


    public CryptoWalletTransactionWalletRecord(UUID transactionId,
                                               CryptoAddress addressFrom,
                                               UUID requestId,
                                               long amount,
                                               CryptoAddress addressTo,
                                               String memo,
                                               long timestamp,
                                               String transactionHash,
                                               String actorFromPublicKey,
                                               String actorToPublicKey,
                                               Actors actorToType,
                                               Actors actorFromType,
                                               com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType,
                                               CryptoCurrency cryptoCurrency,
                                               long fee,
                                               FeeOrigin feeOrigin,
                                               long Total) {
        TransactionId = transactionId;
        AddressFrom = addressFrom;
        RequestId = requestId;
        Amount = amount;
        AddressTo = addressTo;
        Memo = memo;
        Timestamp = timestamp;
        TransactionHash = transactionHash;
        ActorFromPublicKey = actorFromPublicKey;
        ActorToPublicKey = actorToPublicKey;
        ActorToType = actorToType;
        ActorFromType = actorFromType;
        BlockchainNetworkType = blockchainNetworkType;
        this.cryptoCurrency = cryptoCurrency;
        this.fee = fee;
        this.feeOrigin = feeOrigin;
        this.Total = Total;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return AddressFrom;
    }

    @Override
    public UUID getTransactionId() {
        return TransactionId;
    }

    @Override
    public UUID getRequestId() {
        return RequestId;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return AddressTo;
    }

    @Override
    public long getAmount() {
        return Amount;
    }

    @Override
    public long getTotal() {
        return this.Total;
    }

    @Override
    public long getTimestamp() {
        return Timestamp;
    }

    @Override
    public String getMemo() {
        return Memo;
    }

    @Override
    public String getTransactionHash() {
        return TransactionHash;
    }

    @Override
    public String getActorToPublicKey() {
        return ActorToPublicKey;
    }

    @Override
    public String getActorFromPublicKey() {
        return ActorFromPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return ActorToType;
    }

    @Override
    public Actors getActorFromType() {
        return ActorFromType;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return BlockchainNetworkType;
    }

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
