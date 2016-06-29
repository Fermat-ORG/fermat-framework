package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.io.Serializable;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetUserWalletTransactionWrapper implements AssetUserWalletTransaction, Serializable {
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
    private final boolean locked;

    public AssetUserWalletTransactionWrapper(final String transactionId,
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
                                             final String memo,
                                             final boolean locked) {
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
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetUserWalletTransactionWrapper that = (AssetUserWalletTransactionWrapper) o;

        if (getGenesisTransaction() != null ? !getGenesisTransaction().equals(that.getGenesisTransaction()) : that.getGenesisTransaction() != null)
            return false;
        return !(getAssetPublicKey() != null ? !getAssetPublicKey().equals(that.getAssetPublicKey()) : that.getAssetPublicKey() != null);

    }

    @Override
    public int hashCode() {
        int result = getActualTransactionHash() != null ? getActualTransactionHash().hashCode() : 0;
        result = 31 * result + (getGenesisTransaction() != null ? getGenesisTransaction().hashCode() : 0);
        result = 31 * result + (getAssetPublicKey() != null ? getAssetPublicKey().hashCode() : 0);
        return result;
    }

    @Override
    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    @Override
    public String getActualTransactionHash() {
        return transactionId;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public String getGenesisTransaction() {
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

    @Override
    public boolean isLocked() {
        return locked;
    }

}
