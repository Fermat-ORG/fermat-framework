package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.util.UUID;

/**
 * Created by franklin on 24/09/15.
 */
public interface AssetIssuerWalletTransaction {
    public String getAssetPublicKey();

    public String getTransactionId();

    public String getTransactionHash();

    public CryptoAddress getAddressFrom();

    public Actors getActorFromType();

    public CryptoAddress getAddressTo();

    public Actors getActorToType();

    public String getActorToPublicKey();

    public String getActorFromPublicKey();

    public BalanceType getBalanceType();

    public TransactionType getTransactionType();

    public long getTimestamp();

    public long getAmount();

    public long getRunningBookBalance();

    public long getRunningAvailableBalance();

    public String getMemo();
}
