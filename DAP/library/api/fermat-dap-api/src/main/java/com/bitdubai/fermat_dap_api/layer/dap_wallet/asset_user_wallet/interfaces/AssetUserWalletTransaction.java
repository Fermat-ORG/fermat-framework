package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletTransaction {
    String getAssetPublicKey();

    String getActualTransactionHash();

    String getTransactionHash();

    String getGenesisTransaction();

    DAPActor getActorFrom();

    DAPActor getActorTo();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();

    boolean isLocked();
}
