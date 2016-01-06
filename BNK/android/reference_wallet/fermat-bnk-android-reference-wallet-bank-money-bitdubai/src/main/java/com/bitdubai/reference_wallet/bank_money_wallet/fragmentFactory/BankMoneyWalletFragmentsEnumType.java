package com.bitdubai.reference_wallet.bank_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by memo on 04/12/15.
 */
public enum BankMoneyWalletFragmentsEnumType implements FermatFragmentsEnumType<BankMoneyWalletFragmentsEnumType> {
    BNK_BANK_MONEY_WALLET_ACCOUNTS_LIST("BNKBMWAL"),
    BNK_BANK_MONEY_WALLET_ADD_ACCOUNT("BNKBMWAA"),
    BNK_BANK_MONEY_WALLET_ACCOUNT_DETAIL("BNKBMWAD");


    private String key;

    BankMoneyWalletFragmentsEnumType(String key){
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

    public static BankMoneyWalletFragmentsEnumType getValue(String name) {
        for (BankMoneyWalletFragmentsEnumType fragments : BankMoneyWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
