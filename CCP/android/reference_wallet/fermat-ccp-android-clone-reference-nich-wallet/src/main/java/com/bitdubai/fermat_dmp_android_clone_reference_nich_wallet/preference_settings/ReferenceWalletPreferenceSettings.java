package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.preference_settings;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.PreferenceWalletSettings;

/**
 * Created by Matias Furszyfer on 2015.08.24..
 */
public class ReferenceWalletPreferenceSettings extends PreferenceWalletSettings{

    private int transactionToShow;

    BalanceType balanceType;

    public ReferenceWalletPreferenceSettings(){
        transactionToShow=10;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public int getTransactionsToShow() {
        return transactionToShow;
    }
}
