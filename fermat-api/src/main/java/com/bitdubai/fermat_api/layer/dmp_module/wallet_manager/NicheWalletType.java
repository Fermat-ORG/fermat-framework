package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

/**
 * Created by ciencias on 26.01.15.
 */
public enum NicheWalletType {

    DEFAULT ("Default"),
    AGE_KIDS_ALL ("Kids"), AGE_TEEN_ALL ("Teens"),AGE_YOUNG_ALL ("Young"), AGE_ADULT_ALL ("Adult"),AGE_SENIOR_ALL ("Senior");

    private final String typeName;

    NicheWalletType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName()   { return typeName; }

    public static NicheWalletType getTypeByName (String typeName) {

        switch (typeName)
        {
            case "Kids": return NicheWalletType.AGE_KIDS_ALL;
            case "Teens": return NicheWalletType.AGE_TEEN_ALL;
            case "Young": return NicheWalletType.AGE_YOUNG_ALL;
            case "Adult": return NicheWalletType.AGE_ADULT_ALL;
            case "Senior": return NicheWalletType.AGE_SENIOR_ALL;
        }


        return DEFAULT;

    }

}
