package com.bitdubai.fermat_api.layer._16_module.wallet_manager;

/**
 * Created by ciencias on 26.01.15.
 */
public enum WalletType {

    DEFAULT ("Default"),
    AGE_KIDS_ALL ("Kids"), AGE_TEEN_ALL ("Teens"),AGE_YOUNG_ALL ("Young"), AGE_ADULT_ALL ("Adult"),AGE_SENIOR_ALL ("Senior");

    private final String typeName;

    WalletType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName()   { return typeName; }

    public static WalletType getTypeByName (String typeName) {

        switch (typeName)
        {
            case "Kids": return WalletType.AGE_KIDS_ALL;
            case "Teens": return WalletType.AGE_TEEN_ALL;
            case "Young": return WalletType.AGE_YOUNG_ALL;
            case "Adult": return WalletType.AGE_ADULT_ALL;
            case "Senior": return WalletType.AGE_SENIOR_ALL;
        }


        return DEFAULT;

    }

}
