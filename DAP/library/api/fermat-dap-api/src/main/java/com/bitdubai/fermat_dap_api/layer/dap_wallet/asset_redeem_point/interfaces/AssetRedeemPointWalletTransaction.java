package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletTransaction {

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
