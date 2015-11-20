package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory;

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
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE("CWRWBTCABTA"),
    CCP_BITCOIN_WALLET_TRANSACTIONS_SENT("CCPBWTS"),
    CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED("CCPBWTR"),
    CCP_BITCOIN_WALLET_REQUEST_RECEIVED("CCPBWRR"),
    CCP_BITCOIN_WALLET_REQUEST_SEND("CCPBWRS"),
    CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY("CCPBWTSH"),
    CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED_HISTORY("CCPBWTRH"),
    CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY("CCPBWRSH"),
    CCP_BITCOIN_WALLET_REQUEST_RECEIVED_HISTORY("CCPBWRRH"),

    CCP_BITCOIN_WALLET_SEND_FORM_FRAGMENT("CPPBWSFF"),
    CCP_BITCOIN_WALLET_REQUEST_FORM_FRAGMENT("CCPBWRFF"),
    CCP_BITCOIN_WALLET_SETTINGS_FRAGMENT("CCPBWSF"),
    CCP_BITCOIN_WALLET_ADD_CONNECTION_FRAGMENT("CCPBWACF");


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
