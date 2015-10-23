package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.util.UUID;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletTransactionWrapper implements AssetRedeemPointWalletTransaction {
    private final String transactionId;
    private final String transactionHash;
    private final String assetPublicKey;
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

    public AssetRedeemPointWalletTransactionWrapper(final String transactionId,
                                               final String transactionHash,
                                               final String assetPublicKey,
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
                                               final String memo) {
        this.transactionId = transactionId;
        this.assetPublicKey = assetPublicKey;
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
    }
    @Override
    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    @Override
    public String getTransactionId() {
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
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
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
    public long getAmount() {
        return amount;
    }

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
}
