package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatFragments;

/**
 * Created by rodrigo on 2015.07.20..
 */
public enum Fragments implements FermatFragments {
    CWP_SHELL_LOGIN ("LoginFragment"),
    CWP_WALLET_MANAGER_MAIN ("DesktopFragment"),
    CWP_SUB_APP_DEVELOPER ("SubAppDesktopFragment"),
    CWP_WALLET_MANAGER_SHOP ("LoginFragment"),
    CWP_SHOP_MANAGER_MAIN ("ShopFragment"),
    CWP_SHOP_MANAGER_FREE("ShopFreeFragment") ,
    CWP_SHOP_MANAGER_PAID ("ShopPaidFragment"),
    CWP_SHOP_MANAGER_ACCEPTED_NEARBY("ShopNearbyFragment"),

    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND("BitcoinSendFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE("BitcoinBalanceFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE("BitcoinReceiveFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS("BitcoinTransactionFragment"),
    CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS("BitcoinContactFragment"),

    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE("KidsProfileFragment"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP("KidsDesktopFragment"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS("KidsContactFragment"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY("KidsCommunityFragment"),

    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME("AdultsHomeFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE("AdultsBalanceFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND("AdultsSendFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE("AdultsReceiveFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS("AdultsShopsFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL("AdultsReffilFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS("AdultsDiscountsFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP("AdultsShopShopFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS("AdultsShopProductFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS("AdultsShopReviewFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT("AdultsShopChatFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY("AdultsShopHistoryFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP("AdultsShopMapFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS("AdultsDebitsFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS("AdultsCreditsFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL("AdultsAccountAllFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT("AdultsContactsChatFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS("AdultsContactsFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND("AdultsContactsSendFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND("AdultsContactsSendNewFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE("AdultsContactReciveFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE("AdultsContactsNewReceiveFragment"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX("AdultsChatFragment"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE("AdultsBalanceFragment"),

    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED("AdultsRequestReceiveFragment"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY("AdultsRequestHistoryFragment"),
    CWP_WALLET_ADULTS_ALL_REQUEST_SEND("AdultsRequestSendFragment"),
    CWP_WALLET_ADULTS_ALL_SEND_HISTORY("AdultsSendHistoryFragment"),
    CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT("AdultsDailyDiscountFragment"),
    CWP_WALLET_ADULTS_ALL_WEEKLY_DISCOUNT("AdultsWeeklyDiscountFragment"),
    CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT("AdultsMonthlyDiscountFragment"),

    CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES("DeveloperDatabaseFragment"),
    CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES("DeveloperTablesFragment"),
    CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS("DeveloperRecordsFragment"),
    CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS("DeveloperDatabaseToolsFragment"),
    CWP_SUB_APP_DEVELOPER_LOG_TOOLS("DeveloperLogFragment"),
    CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS("DeveloperLogLevel1Fragment"),
    CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS("DeveloperLogLevel2Fragment"),
    CWP_SUB_APP_DEVELOPER_LOG_LEVEL_3_TOOLS("DeveloperLogLevel3Fragment"),

    CWP_WALLET_STORE_MAIN ("StoreFragment"),

    CWP_WALLET_PUBLISHER_MAIN("PublisherFragment"),


    /**
     * WAllet factory
     */
    CWP_WALLET_FACTORY_MAIN ("FactoryFragment"),
    CWP_WALLET_FACTORY_MANAGER ("ManagerFragment"),
    CWP_WALLET_FACTORY_ESTRUCTURE ("EstructureFragment");



    private String key;

    Fragments(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }



    public String toString(){
        return key;
    }

    public static Fragments getValueFromString(String name) {
        for (Fragments fragments : Fragments.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
