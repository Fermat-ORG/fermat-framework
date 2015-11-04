package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.Views;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;


public class EntryItem implements Item {

    public final CryptoWalletTransaction cryptoWalletTransaction;

    public EntryItem(CryptoWalletTransaction cryptoWalletTransaction) {
        this.cryptoWalletTransaction = cryptoWalletTransaction;
    }

    @Override
    public boolean isSection() {
        return false;
    }

}