package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * Created by rodrigo on 2015.07.20..
 */
public enum Activities {

    CWP_SHELL_LOGIN ("CSL"),
    CWP_SHOP_MANAGER_MAIN ("CSMM"),
    CWP_WALLET_MANAGER_MAIN ("CWMM"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN ("CWRWAKAB1M"),

    /**
     * Reference wallet
     */
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN ("CWRWBWBV1M"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS("CWRWBWBV1T"),

    /**
     * Intra user
     */
    CWP_INTRA_USER_ACTIVITY("CIUA"),
    CWP_INTRA_USER_CREATE_ACTIVITY("CIUCA"),

    //Wallet store
    CWP_WALLET_STORE_MAIN_ACTIVITY("CWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWSMDA"),


    CWP_WALLET_ADULTS_ALL_MAIN ("CWAAM"),
    CWP_WALLET_BASIC_ALL_MAIN ("CWBAM"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_AVAILABLE_BALANCE("CWRBTCAAB"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_TRANSACTIONS ("CWRBTCAT"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS ("CWRBTCAC"),


    CWP_WALLET_RUNTIME_STORE_MAIN ( "CWRSM"),
    CWP_WALLET_RUNTIME_WALLET_AGE_ADULTS_ALL_BITDUBAI_VERSION_1_MAIN ("CWRWAAAB1M"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN ("CWRAAM"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS ("CWRAAA"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE ("CWRAAAB"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL ("CWRAAAD"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS ("CWRAABK"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS ("CWRAAC"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS ("CWRAAD"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS ("CWRAAV"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS ("CWRAAGC"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES ("CWRAACS"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS ("CWRAACH"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS ( "CWRAACT"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT ( "CWRAACC"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND ( "CWRAACSN"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND ( "CWRAACNS"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE ( "CWRAACR"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE ( "CWRAACNR"),
    CWP_WALLET_ADULTS_ALL_SHOPS ("CWRAAS"),
    CWP_WALLET_ADULTS_ALL_REFFILS ("CWRAAR"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED ("CWRAARR"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_CHAT ("CWRAARRC"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY ("CWRAARRH"),
    CWP_WALLET_ADULTS_ALL_REQUEST_SEND ( "CWRAARS"),
    CWP_WALLET_ADULTS_ALL_SEND_HISTORY ( "CWRAASH"),
    CWP_WALLET_ADULTS_ALL_CHAT_TRX( "CWRAACTX"),
    CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT( "CWRAADD"),
    CWP_WALLET_ADULTS_ALL_WEEKLY_DISCOUNT( "CWRAAWD"),
    CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT( "CWRAAMD"),
    CWP_SUB_APP_ALL_DEVELOPER("CSAAD"),
    CWP_WALLET_FACTORY_MAIN ("CWFM"),
    CWP_WALLET_FACTORY_EDIT_WALLET ("CWFEW"),
    CWP_WALLET_PUBLISHER_MAIN ("CWPM");

    private String code;

    Activities(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code; }

    public String toString(){
        return code;
    }

    public static Activities getValueFromString(String code) {
        //for (Activities activities : Activities.values()) {
        //    if (activities.key.equals(name)) {
        //        return activities;
        //    }
        //}
        switch (code){

            case "CSL":
                return Activities.CWP_SHELL_LOGIN;
            case "CSMM":
                return Activities.CWP_SHOP_MANAGER_MAIN;
            case "CWMM":
                return Activities.CWP_WALLET_MANAGER_MAIN;
            case "CWRWAKAB1M":
                return Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN;
            case "CWRWBWBV1T":
                return Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS;
            case "CWSMA":
                return Activities.CWP_WALLET_STORE_MAIN_ACTIVITY;
            case "CWSDA":
                return CWP_WALLET_STORE_DETAIL_ACTIVITY;
            case "CWSMDA":
                return CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY;
            case "CWAAM":
                return Activities.CWP_WALLET_ADULTS_ALL_MAIN;
            case "CWBAM":
                return Activities.CWP_WALLET_BASIC_ALL_MAIN;
            case "CWRBTCAAB":
                return Activities.CWP_WALLET_RUNTIME_BITCOIN_ALL_AVAILABLE_BALANCE;
            case "CWRBTCAT":
                return Activities.CWP_WALLET_RUNTIME_BITCOIN_ALL_TRANSACTIONS;
            case "CWRBTCAC":
                return Activities.CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS;
            case "CWRSM":
                return Activities.CWP_WALLET_RUNTIME_STORE_MAIN;
            case "CWRWAAAB1M":
                return Activities.CWP_WALLET_RUNTIME_WALLET_AGE_ADULTS_ALL_BITDUBAI_VERSION_1_MAIN;
            case "CWRAAM":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN;
            case "CWRAAA":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS;
            case "CWRAAAB":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE;
            case "CWRAAAD":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL;
            case "CWRAABK":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS;
            case "CWRAAC":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS;
            case "CWRAAD":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS;
            case "CWRAAV":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS;
            case "CWRAAGC":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS;
            case "CWRAACS":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES;
            case "CWRAACH":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS;
            case "CWRAACT":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS;
            case "CWRAACC":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT;
            case "CWRAACSN":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND;
            case "CWRAACNS":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND;
            case "CWRAACR":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE;
            case "CWRAACNR":
                return Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE;
            case "CWRAAS":
                return Activities.CWP_WALLET_ADULTS_ALL_SHOPS;
            case "CWRAAR":
                return Activities.CWP_WALLET_ADULTS_ALL_REFFILS;
            case "CWRAARR":
                return Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED;
            case "CWRAARRC":
                return Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_CHAT;
            case "CWRAARRH":
                return Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY;
            case "CWRAARS":
                return Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND;
            case "CWRAASH":
                return Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY;
            case "CWRAACTX":
                return Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX;
            case "CWRAADD":
                return Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT;
            case "CWRAAWD":
                return Activities.CWP_WALLET_ADULTS_ALL_WEEKLY_DISCOUNT;
            case "CWRAAMD":
                return Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT;
            case "CSAAD":
                return Activities.CWP_SUB_APP_ALL_DEVELOPER;
            case "CWFM":
                return Activities.CWP_WALLET_FACTORY_MAIN;
            case "CWFEW":
                return Activities.CWP_WALLET_FACTORY_EDIT_WALLET;
            case "CWPM":
                return Activities.CWP_WALLET_PUBLISHER_MAIN;
            case "CIUA":
                return CWP_INTRA_USER_ACTIVITY;
            case "CIUCA":
                return CWP_INTRA_USER_CREATE_ACTIVITY;

        }
        // throw an IllegalArgumentException or return null
        //throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
