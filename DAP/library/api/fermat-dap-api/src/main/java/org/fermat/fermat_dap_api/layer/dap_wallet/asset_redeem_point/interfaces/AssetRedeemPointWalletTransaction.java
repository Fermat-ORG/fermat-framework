package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import java.io.Serializable;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletTransaction extends Serializable {

    String getAssetPublicKey();

    String getTransactionId();

    String getTransactionHash();

    org.fermat.fermat_dap_api.layer.dap_actor.DAPActor getActorFrom();

    org.fermat.fermat_dap_api.layer.dap_actor.DAPActor getActorTo();

    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType getBalanceType();

    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();
}
