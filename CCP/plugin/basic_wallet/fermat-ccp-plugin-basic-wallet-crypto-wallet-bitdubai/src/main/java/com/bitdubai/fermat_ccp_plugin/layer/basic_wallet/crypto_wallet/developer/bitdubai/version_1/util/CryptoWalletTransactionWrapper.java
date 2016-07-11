package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.10..
 */
public class CryptoWalletTransactionWrapper implements CryptoWalletTransaction {

    private final UUID transactionId;
    private final String transactionHash;
    private final TransactionType transactionType;
    private final CryptoAddress addressFrom;
    private final CryptoAddress addressTo;
    private final String actorFromPublicKey;
    private final String actorToPublicKey;
    private final Actors actorFromType;
    private final Actors actorToType;
    private final BalanceType balanceType;
    private final long amount;
    private final long runningBookBalance;
    private final long runningAvailableBalance;
    private final long timeStamp;
    private final String memo;
    private final BlockchainNetworkType blockchainNetworkType;
    private final TransactionState transactionState;
    private final CryptoCurrency cryptoCurrency;
    private final FeeOrigin feeOrigin;
    private final long fee;
    private final long total;
    
    public CryptoWalletTransactionWrapper(final UUID transactionId,
                                          final String transactionHash,
                                          final TransactionType transactionType,
                                          final CryptoAddress addressFrom,
                                          final CryptoAddress addressTo,
                                          final String actorFromPublicKey,
                                          final String actorToPublicKey,
                                          final Actors actorFromType,
                                          final Actors actorToType,
                                          final BalanceType balanceType,
                                          final long amount,
                                          final long runningBookBalance,
                                          final long runningAvailableBalance,
                                          final long timeStamp,
                                          final String memo,
                                          BlockchainNetworkType blockchainNetworkType,
                                          final TransactionState transactionState,
                                          CryptoCurrency cryptoCurrency,
                                          FeeOrigin feeOrigin,long fee, long total) {
        this.transactionId = transactionId;
        this.transactionHash = transactionHash;
        this.transactionType = transactionType;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.balanceType = balanceType;
        this.amount = amount;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.blockchainNetworkType = blockchainNetworkType;
        this.transactionState = transactionState;
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
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public BalanceType getBalanceType() {
        return balanceType;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public long getAmount() { return amount; }

    @Override
    public long getRunningBookBalance() {
        return runningBookBalance;
    }

    @Override
    public long getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public TransactionState getTransactionState() {
        return transactionState;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType; }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    @Override
    public long getFee() {
        return this.fee;
    }
}
