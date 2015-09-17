package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.WalletInfo;

/**
 * Created by mati on 2015.09.17..
 */
public class CryptoWalletInfo implements WalletInfo {

    private long totalAmountTransactions;
    private int totalTransactions;


    @Override
    public int getTotalTransactions() {
        return 0;
    }

    @Override
    public long getTotalAmountOfTransactions() {
        return 0;
    }
}
