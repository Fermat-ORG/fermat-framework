package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletTransaction {
    String getAssetPublicKey();

    String getActualTransactionHash();

    String getGenesisTransaction();

    CryptoAddress getAddressFrom();

    Actors getActorFromType();

    CryptoAddress getAddressTo();

    Actors getActorToType();

    String getActorToPublicKey();

    String getActorFromPublicKey();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();
}
