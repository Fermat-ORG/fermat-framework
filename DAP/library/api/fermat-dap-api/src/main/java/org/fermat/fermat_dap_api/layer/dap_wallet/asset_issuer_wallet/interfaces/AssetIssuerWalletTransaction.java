package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;

/**
 * Created by franklin on 24/09/15.
 */
public interface AssetIssuerWalletTransaction extends Serializable {
    String getAssetPublicKey();

    String getTransactionId();

    String getTransactionHash();

    DAPActor getActorFrom();

    DAPActor getActorTo();

    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType getBalanceType();

    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();
}
