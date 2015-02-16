package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Languages;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeSubApp implements SubApp {

    SubApps type;
    Map<Activities, Activity> activities = new  HashMap<Activities, Activity>();
    Map<Wallets, Wallet> wallets= new  HashMap<Wallets, Wallet>();
    Map<String,LanguagePackage> languagePackages = new HashMap<String,LanguagePackage>();


    /**
     * RuntimeSubApp interface implementation.
     */
    public void setType(SubApps type) {
        this.type = type;
    }
    
    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }

    public void addWallet (Wallet wallet){
        wallets.put(wallet.getType(), wallet);
    }

    public void addLanguagePackage (LanguagePackage languagePackage){
        languagePackages.put(languagePackage.getName(),languagePackage);
    }

    /**
     * SubApp interface implementation.
     */

    @Override
    public SubApps getType() {
        return type;
    }

    @Override
    public Map<Activities, Activity> getActivities(){
        return activities;
    }

    @Override
    public Map<Wallets, Wallet> getWallets(){
        return wallets;
    }

    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }

}
