package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

/**
 * Created by ciencias on 2/14/15.
 */
public interface AppRuntime {
    
    public App getApp (Apps app);
    
    public App getLastApp ();

    public SubApp getLastSubApp ();

    public Wallet getLastWallet ();
    
    public Activity getLasActivity ();
    
    public Fragment getLastFragment ();
    
}
