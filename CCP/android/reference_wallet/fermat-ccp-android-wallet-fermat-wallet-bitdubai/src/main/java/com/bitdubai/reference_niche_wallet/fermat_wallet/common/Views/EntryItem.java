package com.bitdubai.reference_niche_wallet.fermat_wallet.common.Views;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;

public class EntryItem implements Item {

    public final FermatWalletModuleTransaction fermatWalletTransaction;

    public EntryItem(FermatWalletModuleTransaction fermatWalletTransaction) {
        this.fermatWalletTransaction = fermatWalletTransaction;
    }

    @Override
    public boolean isSection() {
        return false;
    }

}