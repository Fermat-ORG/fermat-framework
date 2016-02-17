package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

/**
 * Created by franklin on 24/09/15.
 */
public interface AssetIssuerWalletTransaction {
    String getAssetPublicKey();

    String getTransactionId();

    String getTransactionHash();

    DAPActor getActorFrom();

    DAPActor getActorTo();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();
}
