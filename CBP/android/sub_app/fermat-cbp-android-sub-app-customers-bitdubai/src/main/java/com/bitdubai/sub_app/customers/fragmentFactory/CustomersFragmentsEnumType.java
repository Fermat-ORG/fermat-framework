package com.bitdubai.sub_app.customers.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;


public enum CustomersFragmentsEnumType implements FermatFragmentsEnumType<CustomersFragmentsEnumType> {

    MAIN_FRAGMET("MF");

    private String key;

    CustomersFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static CustomersFragmentsEnumType getValue(String name) {
        for (CustomersFragmentsEnumType fragments : CustomersFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
