package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum ReferenceFragmentsEnumType implements FermatFragmentsEnumType<ReferenceFragmentsEnumType> {


    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND("CWRWBTCABS"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE("CWRWBTCABB"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE("CWRWBTCABR"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS("CWRWBTCABT"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS("CWRWBTCABC"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS("CWRWBABDC"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS("CWRWBABCC"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST("CWRWBTCABMR"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_BOOK("CWRWBTCABTB"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE("CWRWBTCABTA")
    ;


    private String key;

    ReferenceFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey()   { return this.key; }


    @Override
    public String toString(){
        return key;
    }

    public static ReferenceFragmentsEnumType getValue(String name) {
        for (ReferenceFragmentsEnumType fragments : ReferenceFragmentsEnumType.values()) {
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
