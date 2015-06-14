package com.bitdubai.fermat_api.layer._15_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;

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
