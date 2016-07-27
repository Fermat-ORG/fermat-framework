package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.io.Serializable;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletTransactionWrapper implements AssetRedeemPointWalletTransaction, Serializable {

    private final String transactionId;
    private final String transactionHash;
    private final String assetPublicKey;
    private final TransactionType transactionType;
    private final DAPActor actorFrom;
    private final DAPActor actorTo;
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
                                                    final DAPActor actorFrom,
                                                    final DAPActor actorTo,
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
        this.actorFrom = actorFrom;
        this.actorTo = actorTo;
        this.balanceType = balanceType;
        this.amount = amount;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetRedeemPointWalletTransactionWrapper that = (AssetRedeemPointWalletTransactionWrapper) o;

        if (!getTransactionHash().equals(that.getTransactionHash())) return false;
        return getAssetPublicKey().equals(that.getAssetPublicKey());

    }

    @Override
    public int hashCode() {
        int result = getTransactionHash().hashCode();
        result = 31 * result + getAssetPublicKey().hashCode();
        return result;
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
    public DAPActor getActorFrom() {
        return actorFrom;
    }

    @Override
    public DAPActor getActorTo() {
        return actorTo;
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
