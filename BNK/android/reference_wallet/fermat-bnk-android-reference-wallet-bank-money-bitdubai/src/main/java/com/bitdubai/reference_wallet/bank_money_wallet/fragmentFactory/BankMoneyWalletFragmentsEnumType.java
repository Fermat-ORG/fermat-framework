package com.bitdubai.reference_wallet.bank_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by memo on 04/12/15.
 */
public enum BankMoneyWalletFragmentsEnumType implements FermatFragmentsEnumType<BankMoneyWalletFragmentsEnumType> {
    ;


    private String key;

    BankMoneyWalletFragmentsEnumType(String key){
        this.key=key;
    }
    @Override
    public String getKey() {
        return null;
    }
    @Override
    public String toString() {
        return key;
    }

    public static BankMoneyWalletFragmentsEnumType getValue(String name) {
        for (BankMoneyWalletFragmentsEnumType fragments : BankMoneyWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
