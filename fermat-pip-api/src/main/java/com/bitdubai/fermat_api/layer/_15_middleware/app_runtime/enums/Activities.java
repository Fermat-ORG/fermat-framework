package com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Activities {

    CWP_SHELL_LOGIN ("LoginActivity"),
    CWP_SHOP_MANAGER_MAIN ("ShopActivity"),
    CWP_WALLET_MANAGER_MAIN ("DesktopActivity"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN ("CPWWRWAKAV1M"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN ("CWRWBWBV1M"),
    CWP_WALLET_STORE_MAIN ("StoreActivity"),
    CWP_WALLET_ADULTS_ALL_MAIN ("AdultsActivity"),
    CWP_WALLET_BASIC_ALL_MAIN ("WalletBasicActivity"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_AVAILABLE_BALANCE("basicBalanceActivity"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_SEND( "BasicSendToContactActivity"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_NEW_SEND( "BasicSendToNewContactActivity"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_RECEIVE( "BasicReceiveFromContactActivity"),
    CWP_WALLET_RUNTIME_BITCOIN_ALL_CONTACTS_NEW_RECEIVE( "BasicReceiveFromNewContactActivity"),
    CWP_WALLET_RUNTIME_STORE_MAIN ( "StoreFrontActivity"),
    CWP_WALLET_RUNTIME_WALLET_AGE_ADULTS_ALL_BITDUBAI_VERSION_1_MAIN ("CPWRWAABV1_Main"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN ("AdultsRuntimeActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS ("AccountsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE ("AvailableBalanceActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL ("AccountDetailActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS ("BanksActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS ("CouponsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS ("DiscountsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS ("VouchersActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS ("GiftCardsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES ("ClonesActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS ("ChildsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS ( "ContactsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT ( "ContactsChatActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND ( "SendToContactActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND ( "SendToNewContactActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE ( "ReceiveFromContactActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE ( "ReceiveFromNewContactActivity"),
    CWP_WALLET_ADULTS_ALL_SHOPS ("ShopsActivity"),
    CWP_WALLET_ADULTS_ALL_REFFILS ("ReffillsActivity"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED ("RequestReceivedActivity"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_CHAT ("ChatOverReceiveTrxActivity"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY ("ReceiveAllHistoryActivity"),
    CWP_WALLET_ADULTS_ALL_REQUEST_SEND ( "RequestSendActivity"),
    CWP_WALLET_ADULTS_ALL_SEND_HISTORY ( "SentHistoryActivity"),
    CWP_WALLET_ADULTS_ALL_CHAT_TRX( "ChatOverTrxActivity"),
    CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT( "DailyDiscountsActivity"),
    CWP_WALLET_ADULTS_ALL_WEEKLY_DISCOUNT( "WeeklyDiscountsActivity"),
    CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT( "MonthlyDiscountsActivity"),
    CWP_WALLET_FACTORY_MAIN ("FactoryActivity");

    private String key;

    Activities(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }



    public String toString(){
        return key;
    }

    public static Activities getValueFromString(String name) {
        for (Activities activities : Activities.values()) {
            if (activities.key.equals(name)) {
                return activities;
            }
        }
        // throw an IllegalArgumentException or return null
        throw new IllegalArgumentException("the given number doesn't match any Status.");
    }
}
