package com.bitdubai.sub_app.wallet_publisher.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum WalletPublisherFragmentsEnumType implements FermatFragmentsEnumType<WalletPublisherFragmentsEnumType> {


    CWP_WALLET_PUBLISHER_MAIN_FRAGMENT("CWPMF"),;


    private String key;

    WalletPublisherFragmentsEnumType(String key) {
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


    public static WalletPublisherFragmentsEnumType getValue(String name) {
        for (WalletPublisherFragmentsEnumType fragments : WalletPublisherFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }
}
