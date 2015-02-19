package com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Activities {
    
    CWP_SHELL_LOGIN ("LoginActivity"),
    CWP_SHOP_MANAGER_MAIN ("ShopActivity"),
    CWP_WALLET_MANAGER_MAIN ("DesktopActivity"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_MAIN ("FrameworkActivity"),
    CWP_WALLET_STORE_MAIN ("StoreActivity"),
    CWP_WALLET_ADULTS_ALL_MAIN ("AdultsActivity"),
    CWP_WALLET_RUNTIME_STORE_MAIN ( "StoreFrontActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_MAIN ("AdultsRuntimeActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS ("AccountsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS ("BanksActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS ("CouponsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS ("DiscountsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS ("VouchersActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS ("GiftCardsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES ("ClonesActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS ("ChildsActivity"),
    CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS ( "ContactsActivity"),
    CWP_WALLET_ADULTS_ALL_SHOPS ("ShopsActivity"),
    CWP_WALLET_ADULTS_ALL_REFFILS ("ReffillsActivity"),
    CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED ("RequestReceivedActivity"),
    CWP_WALLET_ADULTS_ALL_REQUEST_SEND ( "RequestSendActivity"),
    CWP_WALLET_FACTORY_MAIN ("FactoryActivity");

    private final String key;

    Activities(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }



    public String toString(){
        return key;
    }
}
