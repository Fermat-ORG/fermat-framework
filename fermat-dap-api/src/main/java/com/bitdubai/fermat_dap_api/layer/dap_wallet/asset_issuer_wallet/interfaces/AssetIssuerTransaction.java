package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by franklin on 05/09/15.
 */
public interface AssetIssuerTransaction {

    UUID getTransactionId();

    long getTimestamp();

    long getAmount();

    CryptoAddress getAddressFrom();

    //Validemos si podriamos usar BitcoinWalletTransaction
}
