package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface SubApp {

    public SubApps getType();

    public Map<Activities, Activity> getActivities();

    public Map<Wallets, Wallet> getWallets();
    
    public Map<String,LanguagePackage> getLanguagePackages();
}
