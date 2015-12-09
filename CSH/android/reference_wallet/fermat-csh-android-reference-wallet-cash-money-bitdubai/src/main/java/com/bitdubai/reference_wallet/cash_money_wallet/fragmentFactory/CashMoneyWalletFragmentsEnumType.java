package com.bitdubai.reference_wallet.cash_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public enum CashMoneyWalletFragmentsEnumType implements FermatFragmentsEnumType<CashMoneyWalletFragmentsEnumType> {
    CSH_CASH_MONEY_WALLET_MAIN_FRAGMENT("CSHCMWMF");


    private String key;

    CashMoneyWalletFragmentsEnumType(String key){
        this.key=key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }

    public static CashMoneyWalletFragmentsEnumType getValue(String name) {
        for (CashMoneyWalletFragmentsEnumType fragments : CashMoneyWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
